import java.util.List;
import java.util.ArrayList;


public class ChangeSet {
	
	private List<String> removedFiles;
	
	private List<String> addedFiles;
	
	private List<String> modifiedFiles;
	
	public ChangeSet(){
		this.removedFiles = new ArrayList<String>();
		this.addedFiles = new ArrayList<String>();
		this.modifiedFiles = new ArrayList<String>();
	}

	public List<String> getRemovedFiles() {
		return removedFiles;
	}

	public void setRemovedFiles(List<String> removedFiles) {
		this.removedFiles = removedFiles;
	}

	public List<String> getAddedFiles() {
		return addedFiles;
	}

	public void setAddedFiles(List<String> addedFiles) {
		this.addedFiles = addedFiles;
	}

	public List<String> getModifiedFiles() {
		return modifiedFiles;
	}

	public void setModifiedFiles(List<String> modifiedFiles) {
		this.modifiedFiles = modifiedFiles;
	}
	

	
	
	
/*	public String toString(){
		String result = "";
	
		return result;
	}*/
}
