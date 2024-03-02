package drlibs.utils.reloader.reloadparams;

import java.util.function.Function;

import drlibs.utils.reloader.LoadData;
import drlibs.utils.reloader.LoadParser;
import drlibs.utils.reloader.loaddata.BaseLoadData;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.PostProcessingResult;

public class BaseReloadParams implements ReloadParams {

	private String source;
	private LoadParser parser;
	private Function<ParseResult, PostProcessingResult> postParsingFunction;
	
	public BaseReloadParams(String source, LoadParser parser, Function<ParseResult, PostProcessingResult> postParsingFunction) {
		this.source = source;
		this.parser = parser;
		this.postParsingFunction = postParsingFunction;
	}
	
	@Override
	public String getSource() {
		return source;
	}
	
	@Override
	public LoadParser getParser() {
		return parser;
	}
	
	@Override
	public Function<ParseResult, PostProcessingResult> getPostParsingFunction() {
		return postParsingFunction;
	}

	@Override
	public boolean hasSameLoadData(ReloadParams otherReloadParams) {
		return getLoadData().equals(otherReloadParams.getLoadData());
	}

	@Override
	public LoadData getLoadData() {
		return new BaseLoadData(source, parser);
	}
	
}
