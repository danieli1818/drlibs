package drlibs.utils.reloader.reloadparams;

import java.util.function.Function;

import drlibs.utils.reloader.LoadData;
import drlibs.utils.reloader.LoadParser;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.PostProcessingResult;

public interface ReloadParams {

	/**
	 * Returns the source from where to load (Usually file path, can be DB
	 * connection strings)
	 * 
	 * @return The source from where to load
	 */
	public String getSource();

	/**
	 * Returns the parser
	 * 
	 * @return The parser
	 */
	public LoadParser getParser();

	/**
	 * Returns the post parsing function that will run after the parsing
	 * 
	 * @return The post parsing function
	 */
	public Function<ParseResult, PostProcessingResult> getPostParsingFunction();

	/**
	 * Returns whether this reload params has the same load data after parsing as
	 * the other reload params.
	 * 
	 * @param otherReloadParams ReloadParams to compare with
	 * @return Whether this reload params and the other has the same load data after
	 *         parsing
	 */
	public boolean hasSameLoadData(ReloadParams otherReloadParams);

	/**
	 * Returns the load data which contains the identification data in order to
	 * enable caching the parsed object in cases it is used by another reload
	 * params.
	 * 
	 * @return The load data
	 */
	public LoadData getLoadData();

}
