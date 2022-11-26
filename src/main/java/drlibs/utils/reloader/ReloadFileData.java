package drlibs.utils.reloader;

public class ReloadFileData {

	private String filePath;
	private boolean shouldLoadResource;
	private boolean shouldReplaceFile;
	
	public ReloadFileData(String filePath) {
		this(filePath, false, false);
	}
	
	public ReloadFileData(String filePath, boolean shouldLoadResource) {
		this(filePath, shouldLoadResource, false);
	}
	
	public ReloadFileData(String filePath, boolean shouldLoadResource, boolean shouldReplaceFile) {
		this.filePath = filePath;
		this.shouldLoadResource = shouldLoadResource;
		this.shouldReplaceFile = shouldReplaceFile;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public boolean shouldLoadResource() {
		return shouldLoadResource;
	}
	
	public boolean shouldReplaceFile() {
		return shouldReplaceFile;
	}
	
}
