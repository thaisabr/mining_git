
public class FeatureCommit extends BaseCommit {
	
	private ChangeSet changeset;
	
public FeatureCommit(String name, String sha){
		
		super(name, sha);
		
	}


public void  computeChangeSet(BaseCommit base){
	
	this.changeset = this.getDirectory().compareDirectories(base.getDirectory());
		
}



}
