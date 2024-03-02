package drlibs.utils.reloader.results.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drlibs.utils.reloader.results.ParseResult;

public class ListParseResult implements ParseResult {

	private List<ParseResult> parseResults;
	private Map<String, String> errorMessagesTransformer;
	
	public static final String NO_PARSERS_TRANSFORM_KEY = "no_parsers";

	public ListParseResult() {
		this.parseResults = new ArrayList<>();
		this.errorMessagesTransformer = new HashMap<>();
		this.errorMessagesTransformer.put(NO_PARSERS_TRANSFORM_KEY, "No parsers in the list parse result!");
	}

	@Override
	public String getSource() {
		if (parseResults.isEmpty()) {
			return null;
		}
		return parseResults.get(0).getSource();
	}

	@Override
	public Object getResult() {
		if (parseResults.isEmpty()) {
			return null;
		}
		return parseResults.get(parseResults.size() - 1).getResult();
	}

	@Override
	public ResultType getResultType() {
		if (parseResults.isEmpty()) {
			return ResultType.PARSING_WARNING;
		}
		ParseResult parseResult = parseResults.stream()
				.max((ParseResult parseResult1, ParseResult parseResult2) -> parseResult1.getResultType().ordinal()
						- parseResult2.getResultType().ordinal())
				.get();
		return parseResult.getResultType();
	}

	@Override
	public String getErrorMessage() {
		List<String> errorMessages = new ArrayList<>();
		for (ParseResult parseResult : parseResults) {
			String errorMessage = parseResult.getErrorMessage();
			if (errorMessage.length() > 0) {
				errorMessages.add(errorMessage);
			}
		}
		if (errorMessages.isEmpty()) {
			return null;
		}
		return String.join("\n", errorMessages);
	}
	
	public void addParseResult(ParseResult parseResult) {
		if (parseResult != null) {
			parseResults.add(parseResult);
		}
	}
	
	public boolean removeParseResult(ParseResult parseResult) {
		return parseResults.remove(parseResult);
	}

}
