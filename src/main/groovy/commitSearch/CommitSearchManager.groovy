package commitSearch

import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.gremlin.groovy.Gremlin

import java.util.regex.Matcher


/***
 * Provides searching mechanism based on Gremlin language for GitHub repositories.
 */
class CommitSearchManager {

    static final FILE_SEPARATOR_REGEX = /(\\|\/)/
    static final NEW_LINE_REGEX = /\r\n|\n/
    Graph graph
    String repository

    /***
     * Creates a object to repositorySearch for commits of a git repository.
     * @param gitRepository name of the git repository
     */
    public CommitSearchManager(String gitRepository){
        def path = "tmp${File.separator}graph.db"
        Gremlin.load()
        graph = new Neo4jGraph(path)
        this.repository = gitRepository
    }

    private static List getFilesFromCommit(def node, def project){
        project = project+"--"
        def files = []
        node.out('CHANGED').token.fill(files)
        files = files.collect { (it-project).replaceAll(FILE_SEPARATOR_REGEX, Matcher.quoteReplacement(File.separator)) }
        return files
    }

    private static String getAuthorsFromCommit(def node){
        def authors = []
        node.out('AUTHOR').out('NAME').name.fill(authors)
        return authors.get(0)
    }

    /***
     * Retrieves all commits from a git repository.
     * @return all commits
     */
    public List<Commit> searchAllCommits(){
        def result = graph.V.filter{it._type == "COMMIT"}
        def commits = []
        result?.each{ r ->
            def files = getFilesFromCommit(r, repository)
            def author = getAuthorsFromCommit(r)
            commits += new Commit(hash:r.hash, message:r.message.replaceAll(NEW_LINE_REGEX," "), files:files, author:author, date:r.date)
        }
        return commits.sort{ it.date }
    }

    /***
     * Retrieves all commits from a git repository filtered by keywords founded in the commit message.
     * @param words keywords for the searching
     * @return commits that satisfies the searching criteria
     */
    public List<Commit> searchByComment(List<String> words){
        def commits = searchAllCommits()
        println "Total commits: ${commits.size()}"

        def result = commits.findAll{ commit ->
            words?.any{commit.message.toLowerCase().contains(it)} && !commit.files.empty
        }
        def finalResult = result.unique{ a,b -> a.hash <=> b.hash }
        println "Total commits by comment: ${finalResult.size()}"

        return finalResult.sort{ it.date }
    }

    /***
     * Retrieves all commits from a git repository which the commit message satisfies a regular expression.
     * @param regex regular expression that defines the expected format of the commit message
     * @return commits that satisfies the searching criteria
     */
    public List<Commit> searchByComment(String regex){
        def commits = searchAllCommits()
        println "Total commits: ${commits.size()}"

        def result = commits.findAll{ commit ->
            (commit.message.toLowerCase() ==~ regex) && !commit.files.empty
        }
        def finalResult = result.unique{ a,b -> a.hash <=> b.hash }
        println "Total commits by comment: ${finalResult.size()}"

        return finalResult.sort{ it.date }
    }

}