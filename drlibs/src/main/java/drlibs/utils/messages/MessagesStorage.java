package drlibs.utils.messages;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import drlibs.utils.reloader.Reloadable;
import drlibs.utils.reloader.loadparsers.FileLoadParser;
import drlibs.utils.reloader.loadparsers.ParsersListLoadParser;
import drlibs.utils.reloader.loadparsers.YamlLoadParser;
import drlibs.utils.reloader.reloadparams.BaseReloadParams;
import drlibs.utils.reloader.reloadparams.ReloadParams;
import drlibs.utils.reloader.results.ParseResult;
import drlibs.utils.reloader.results.PostProcessingResult;
import drlibs.utils.reloader.results.base.BasePostProcessingResult;

public class MessagesStorage implements Reloadable {
	
	private String filesDirPath;
	
	private String configPath;
	
	private Map<String, String> messagesConfigMap;
	private Map<String, String> defaultMessagesConfigMap;
	
	public MessagesStorage(String filesDirPath) {
		this(filesDirPath, "messages.yml");
	}
	
	public MessagesStorage(String filesDirPath, String configFilePath) {
		this.filesDirPath = filesDirPath;
		this.configPath = configFilePath;
		this.messagesConfigMap = new HashMap<>();
		this.defaultMessagesConfigMap = new HashMap<>();
	}
	
	public MessagesStorage setConfigFilename(String filename) {
		if (configPath != null && configPath.equals(filename)) {
			return this;
		}
		configPath = filename;
		clearMessagesConfig();
		return this;
	}
	
	public void clearDefaultMessagesConfigMap() {
		this.defaultMessagesConfigMap.clear();
	}
	
	public void setDefaultMessagesConfigMap(Map<String, String> defaultMessagesConfigMap) {
		this.defaultMessagesConfigMap.putAll(defaultMessagesConfigMap);
	}
	
	public String getMessage(String messageID) {
		if (messagesConfigMap.containsKey(messageID)) {
			return messagesConfigMap.get(messageID);
		}
		return null;
	}
	
	private void clearMessagesConfig() {
		messagesConfigMap.clear();
	}

	@Override
	public Collection<ReloadParams> getReloadParams() {
		List<ReloadParams> reloadParams = new ArrayList<>();
		ParsersListLoadParser parsersListLoadParser = new ParsersListLoadParser(Arrays.asList(new FileLoadParser(), new YamlLoadParser()));
		reloadParams.add(new BaseReloadParams(Paths.get(filesDirPath, configPath).toString(), parsersListLoadParser, (ParseResult parseResult) -> loadMessagesConfig(parseResult.getResult())));
		return reloadParams;
	}
	
	private PostProcessingResult loadMessagesConfig(Object parsedObject) {
		if (!(parsedObject instanceof Map<?, ?>)) {
			return new BasePostProcessingResult(PostProcessingResult.ResultType.ERROR, "Invalid parsed object, not of type Map!");
		}
		messagesConfigMap.clear();
		Map<?, ?> parsedMap = (Map<?, ?>)parsedObject;
		for (Map.Entry<?, ?> parsedMapEntry : parsedMap.entrySet()) {
			if (!(parsedMapEntry.getKey() instanceof String)) {
				// Weird
				return new BasePostProcessingResult(PostProcessingResult.ResultType.ERROR, "Invalid parsed object, map's key isn't a string!");
			}
			if (!(parsedMapEntry.getValue() instanceof String)) {
				// Invalid configuration
				return new BasePostProcessingResult(PostProcessingResult.ResultType.ERROR, "Invalid parsed object, map's value isn't a string!");
			}
			messagesConfigMap.put((String)parsedMapEntry.getKey(), (String)parsedMapEntry.getValue());
		}
		return new BasePostProcessingResult();
	}
	
}
