package drlibs.utils.reloader.loadparsers;

import java.util.ArrayList;
import java.util.List;

import drlibs.utils.reloader.LoadParser;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.list.ListParseResult;

public class ParsersListLoadParser implements LoadParser {

	private List<LoadParser> loadParsers;
	
	public ParsersListLoadParser(List<LoadParser> loadParsers) {
		this.loadParsers = new ArrayList<>(loadParsers);
	}
	
	@Override
	public ParseResult parse(String source) {
		String currentSource = source;
		ListParseResult listParseResult = new ListParseResult();
		for (LoadParser loadParser : loadParsers) {
			ParseResult parseResult = loadParser.parse(currentSource);
			listParseResult.addParseResult(parseResult);
			if (!ParseResult.SUCCESS_PARSE_RESULT_TYPES.contains(parseResult.getResultType())) {
				return listParseResult;
			}
		}
		return listParseResult;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (!(otherObject instanceof ParsersListLoadParser)) {
			return false;
		}
		return loadParsers.equals(((ParsersListLoadParser)otherObject).loadParsers);
	}
	
	@Override
	public int hashCode() {
		return loadParsers.hashCode();
	}

}
