package drlibs.utils.reloader.results.base;

import drlibs.utils.reloader.results.ParseResult;

public class BaseParseResult implements ParseResult {
	
	private String source;
	private Object result;
	private ResultType resultType;
	private String errorMessage;
	
	public BaseParseResult(String source, Object result) {
		this(source, ResultType.SUCCESS, result);
	}
	
	public BaseParseResult(String source, ResultType resultType, Object result) {
		this(source, resultType, result, null);
	}
	
	public BaseParseResult(String source, ResultType resultType, String errorMessage) {
		this(source, resultType, null, errorMessage);
	}
	
	public BaseParseResult(String source, ResultType resultType, Object result, String errorMessage) {
		this.source = source;
		this.resultType = resultType;
		this.result = result;
		this.errorMessage = errorMessage;
	}
	
	public String getSource() {
		return source;
	}
	
	public Object getResult() {
		return result;
	}
	
	public ResultType getResultType() {
		return resultType;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
