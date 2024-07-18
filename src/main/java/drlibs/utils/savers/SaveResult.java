package drlibs.utils.savers;

public interface SaveResult {
	
	public enum Status {
		SUCCESS,
		ERROR
	}
	
	public Status getStatus();
	
	public String getError();
	
}
