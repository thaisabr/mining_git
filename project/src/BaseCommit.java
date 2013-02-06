
public class BaseCommit {
	
	private String name;
	
	private String SHAKey;
	

	private Directory directory;
	
	
	
	public BaseCommit(String name, String sha){
	
		this.name = name;
		this.SHAKey = sha;
		this.directory = new Directory(this.name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSHAKey() {
		return SHAKey;
	}

	public void setSHAKey(String sHAKey) {
		SHAKey = sHAKey;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory dir) {
		
		this.directory = dir;
		
	}


	public void loadDirectory(){
		this.directory.listFilesAndFilesSubDirectories(this.name);
	}
	



}