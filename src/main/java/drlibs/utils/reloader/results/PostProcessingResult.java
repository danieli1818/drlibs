package drlibs.utils.reloader.results;

public interface PostProcessingResult {

	public ResultType getResultType();
	
	public String getErrorMessage();
	
	public enum ResultType {
		SUCCESS,
		WARNING,
		ERROR
	}
	
}
