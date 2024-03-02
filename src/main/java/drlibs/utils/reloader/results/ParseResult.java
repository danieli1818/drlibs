package drlibs.utils.reloader.results;

import java.util.Arrays;
import java.util.List;

public interface ParseResult {

	public String getSource();
	
	public Object getResult();
	
	public ResultType getResultType();
	
	public String getErrorMessage();

	public enum ResultType {
		SUCCESS,
		PARSING_WARNING,
		PARSING_ERROR,
		LOAD_ERROR
	}
	
	public static final List<ResultType> SUCCESS_PARSE_RESULT_TYPES = Arrays.asList(ResultType.SUCCESS,
			ResultType.PARSING_WARNING);
	
}
