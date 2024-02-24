package drlibs.utils.reloader.results.base;

import drlibs.utils.reloader.results.PostProcessingResult;

public class BasePostProcessingResult implements PostProcessingResult {

	private ResultType resultType;
	private String errorMessage;
	
	public BasePostProcessingResult() {
		this(ResultType.SUCCESS);
	}
	
	public BasePostProcessingResult(ResultType resultType) {
		this(resultType, null);
	}
	
	public BasePostProcessingResult(ResultType resultType, String errorMessage) {
		this.resultType = resultType;
		this.errorMessage = errorMessage;
	}
	
	public ResultType getResultType() {
		return resultType;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
