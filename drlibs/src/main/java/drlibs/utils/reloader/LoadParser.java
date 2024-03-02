package drlibs.utils.reloader;

import drlibs.utils.reloader.results.ParseResult;

public interface LoadParser {

	/**
	 * Parse function that parses data from a source to an object.
	 * @param source The source to load the data to parse from.
	 * @return Object of the parsed data from the source.
	 */
	public ParseResult parse(String source);
	
}
