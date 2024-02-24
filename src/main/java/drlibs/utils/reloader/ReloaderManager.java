package drlibs.utils.reloader;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import drlibs.utils.log.PluginLogger;
import drlibs.utils.reloader.reloadparams.ReloadParams;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.ParseResult.ResultType;
import drlibs.utils.reloader.results.PostProcessingResult;
import drlibs.utils.reloader.results.ReloadResult;
import drlibs.utils.reloader.results.base.BaseReloadResult;

public class ReloaderManager {

	private PluginLogger logger;
	private Map<String, ReloadablesSet> reloadablesSetsMap;

	public ReloaderManager() {
		this(null);
	}

	public ReloaderManager(PluginLogger logger) {
		this.logger = logger;
		this.reloadablesSetsMap = new HashMap<>();
	}

	/**
	 * Registers a reloadablesSet as the default set.
	 * 
	 * @param reloadableSet The ReloadableSet to register.
	 * @return The previous reloadablesSet of the default set.
	 */
	public ReloadablesSet registerReloadablesSet(ReloadablesSet reloadablesSet) {
		return registerReloadablesSet(null, reloadablesSet);
	}

	/**
	 * Unregisters the default reloadablesSet.
	 * 
	 * @return The previous reloadablesSet of the default set.
	 */
	public ReloadablesSet unregisterReloadablesSet() {
		return unregisterReloadablesSet(null);
	}

	/**
	 * Registers a reloadablesSet.
	 * 
	 * @param setID          The set ID to register the reloadableSet to.
	 * @param reloadablesSet The ReloadablesSet to register.
	 * @return The previous reloadablesSet of that set ID.
	 */
	public ReloadablesSet registerReloadablesSet(String setID, ReloadablesSet reloadablesSet) {
		if (reloadablesSet == null) {
			return unregisterReloadablesSet(setID);
		}
		return reloadablesSetsMap.put(setID, reloadablesSet);
	}

	/**
	 * Unregisters a reloadablesSet.
	 * 
	 * @param setID          The set ID to unregister the reloadableSet from.
	 * @param reloadablesSet The ReloadableSet to unregister.
	 * @return The previous reloadablesSet of that set ID.
	 */
	public ReloadablesSet unregisterReloadablesSet(String setID) {
		return reloadablesSetsMap.remove(setID);
	}

	/**
	 * Reloads the default set.
	 * 
	 * @return Whether the reload was completed successfully.
	 */
	public ReloadResult reloadDefaultSet() {
		return reloadSet(null);
	}

	/**
	 * Reloads the set.
	 * 
	 * @param setID The set ID to reload.
	 * @return ReloadResult with data on the reload.
	 */
	public ReloadResult reloadSetWithID(String setID) {
		return reloadSet(setID);
	}

	/**
	 * Reloads all sets.
	 */
	public void reloadAllSet() {
		for (String setID : reloadablesSetsMap.keySet()) {
			reloadSet(setID);
		}
	}

	/**
	 * Reloads the set.
	 * 
	 * @param setID The set ID to reload.
	 * @return ReloadResult with data on the reload.
	 */
	private ReloadResult reloadSet(String setID) {
		ReloadablesSet reloadablesSet = reloadablesSetsMap.get(setID);
		if (reloadablesSet == null) {
			return new BaseReloadResult(BaseReloadResult.ResultType.RELOADABLE_ERROR,
					"There is not such a reloadable set with the set ID: " + setID); // TODO Change message to be read
																						// from configuration files.
		}
		reloadablesSet.onPreReload(); // TODO Add result status?

		// First iteration on reload params of each reloadable for load data reloadables
		// map creation for caching later
		Map<LoadData, Set<Reloadable>> loadDataReloadablesMap = new HashMap<>();
		Collection<Reloadable> reloadables = reloadablesSet.getReloadables();
		for (Reloadable reloadable : reloadables) {
			Collection<ReloadParams> reloadParamsCollection = reloadable.getReloadParams();
			for (ReloadParams reloadParams : reloadParamsCollection) {
				LoadData loadData = reloadParams.getLoadData();
				if (!loadDataReloadablesMap.containsKey(loadData)) {
					loadDataReloadablesMap.put(loadData, new HashSet<>());
				}
				loadDataReloadablesMap.get(loadData).add(reloadable);
			}
		}

		BaseReloadResult reloadResult = new BaseReloadResult(); // TODO Change to builder pattern
		// Second iteration on reload params of each reloadable to reload using caching
		Map<LoadData, ParseResult> loadDataCachedParsedObject = new HashMap<>();
		for (Reloadable reloadable : reloadables) {
			Collection<ReloadParams> reloadParamsCollection = reloadable.getReloadParams();
			for (ReloadParams reloadParams : reloadParamsCollection) {
				LoadData loadData = reloadParams.getLoadData();
				ParseResult parseResult = null;
				if (!loadDataCachedParsedObject.containsKey(loadData)) { // Cached
					parseResult = reloadParams.getParser().parse(reloadParams.getSource());
					loadDataCachedParsedObject.put(loadData, parseResult);
					if (parseResult.getResultType() != ResultType.SUCCESS) {
						reloadResult.addParseResult(parseResult);
					}
				}
				parseResult = loadDataCachedParsedObject.get(loadData);
				logParseResult(parseResult);
				if (!ParseResult.SUCCESS_PARSE_RESULT_TYPES.contains(parseResult.getResultType())) {
					continue;
				}
				PostProcessingResult postProcessingResult = reloadParams.getPostParsingFunction().apply(parseResult); // Post
																														// processed
																														// the
																														// parsed
																														// data
				logPostProcessingResult(postProcessingResult, reloadParams.getSource());
				reloadResult.addPostProcessingResult(postProcessingResult);

				Set<Reloadable> loadDataReloadables = loadDataReloadablesMap.get(loadData);
				loadDataReloadables.remove(reloadable); // Remove reloadable from load data reloadables map of the load
														// data
				if (loadDataReloadables.isEmpty()) {
					loadDataCachedParsedObject.remove(loadData); // Remove cache
					loadDataReloadablesMap.remove(loadData); // Remove from load data reloadables map
				}
			}
		}

		reloadablesSet.onPostReload(); // TODO Add result status?
		return reloadResult;
	}

	private void logParseResult(ParseResult parseResult) {
		ResultType resultType = parseResult.getResultType();
		Map<String, String> variables = Map.of("source", parseResult.getSource(), "error_message",
				parseResult.getErrorMessage(), "result_type", resultType.toString());
		switch (resultType) {
			case SUCCESS:
				logger.logTranslated(Level.INFO, "success_parse_message", variables);
				break;
			case PARSING_WARNING:
				logger.logTranslated(Level.WARNING, "warning_parse_message", variables);
				break;
			case LOAD_ERROR:
				logger.logTranslated(Level.SEVERE, "error_parse_load_message", variables);
				break;
			case PARSING_ERROR:
				logger.logTranslated(Level.SEVERE, "error_parse_error_message", variables);
				break;
			default:
				logger.logTranslated(Level.SEVERE, "error_invalid_parse_result_type_message", variables);
				break;
		}
	}
	
	private void logPostProcessingResult(PostProcessingResult postProcessingResult, String source) {
		PostProcessingResult.ResultType resultType = postProcessingResult.getResultType();
		Map<String, String> variables = Map.of("source", source, "error_message",
				postProcessingResult.getErrorMessage(), "error_type", resultType.toString());
		switch (resultType) {
			case SUCCESS:
				logger.logTranslated(Level.INFO, "success_post_processing_message", variables);
				break;
			case WARNING:
				logger.logTranslated(Level.WARNING, "warning_post_processing_message", variables);
				break;
			case ERROR:
				logger.logTranslated(Level.SEVERE, "error_post_processing_message", variables);
				break;
			default:
				logger.logTranslated(Level.SEVERE, "error_invalid_post_processing_result_type_message", variables);
				break;
		}
	}

}
