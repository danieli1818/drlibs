package drlibs.utils.reloader.results.base;

import java.util.ArrayList;
import java.util.List;

import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.PostProcessingResult;
import drlibs.utils.reloader.results.ReloadResult;

public class BaseReloadResult implements ReloadResult {

	private ResultType resultType;
	private List<ParseResult> parseResults;
	private List<PostProcessingResult> postProcessingResults;
	private String reloadableErrorMessage;

	public BaseReloadResult() {
		this(ResultType.SUCCESS);
	}

	public BaseReloadResult(ResultType resultType) {
		this(resultType, null);
	}

	public BaseReloadResult(ResultType resultType, String reloadableErrorMessage) {
		this.resultType = resultType;
		this.parseResults = new ArrayList<>();
		this.reloadableErrorMessage = reloadableErrorMessage;
	}

	public void addParseResult(ParseResult parseResult) {
		parseResults.add(parseResult);
		switch (parseResult.getResultType()) {
			case LOAD_ERROR:
			case PARSING_ERROR:
				if (getResultType().ordinal() <= ReloadResult.ResultType.WARNING.ordinal()) {
					resultType = ResultType.PARSER_ERROR;
				}
				break;
			case PARSING_WARNING:
				if (getResultType().ordinal() < ReloadResult.ResultType.WARNING.ordinal()) {
					resultType = ResultType.WARNING;
				}
				break;
			default:
				break;
		}
	}

	public void addPostProcessingResult(PostProcessingResult postProcessingResult) {
		postProcessingResults.add(postProcessingResult);
		switch (postProcessingResult.getResultType()) {
		case ERROR:
			if (getResultType().ordinal() <= ReloadResult.ResultType.WARNING.ordinal()) {
				resultType = ResultType.PARSER_ERROR;
			}
			break;
		case WARNING:
			if (getResultType().ordinal() < ReloadResult.ResultType.WARNING.ordinal()) {
				resultType = ResultType.WARNING;
			}
			break;
		default:
			break;
		}
	}

	public ResultType getResultType() {
		return resultType;
	}

	public List<ParseResult> getParseResults() {
		return parseResults;
	}
	
	public List<PostProcessingResult> getPostProcessingResults() {
		return postProcessingResults;
	}

	public String getReloadableErrorMessage() {
		return reloadableErrorMessage;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public void setReloadableErrorMessage(String reloadableErrorMessage) {
		this.reloadableErrorMessage = reloadableErrorMessage;
	}

}
