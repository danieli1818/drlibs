package drlibs.utils.reloader.results;

import java.util.List;

public interface ReloadResult {

	public ResultType getResultType();
	
	public List<ParseResult> getParseResults();
	
	public String getReloadableErrorMessage();
	
	// Must be in descending error order since classes base on the indexes
	public enum ResultType {
		SUCCESS,
		WARNING,
		RELOADABLE_ERROR,
		PARSER_ERROR,
		POST_PROCESSING_ERROR
	}
	
}
