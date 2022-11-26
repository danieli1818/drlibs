package drlibs.utils.messages;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import drlibs.common.plugin.FilesPlugin;
import drlibs.utils.reloader.ReloadFileData;
import drlibs.utils.reloader.Reloadable;

public class MessagesStorage implements Reloadable {

	private FilesPlugin plugin;
	
	private String configPath;
	
	private boolean loadResource;
	
	private Map<String, String> messagesCache;
	
	public MessagesStorage(FilesPlugin plugin) {
		this(plugin, "messages.yml", true);
	}
	
	public MessagesStorage(FilesPlugin plugin, String configFilePath) {
		this(plugin, configFilePath, false);
	}
	
	public MessagesStorage(FilesPlugin plugin, String configFilePath, boolean loadResource) {
		this.plugin = plugin;
		this.configPath = configFilePath;
		this.loadResource = loadResource;
		this.messagesCache = new HashMap<>();
	}
	
	public MessagesStorage setConfigFilename(String filename) {
		if (configPath != null && configPath.equals(filename)) {
			return this;
		}
		configPath = filename;
		clearCache();
		return this;
	}
	
	public String getMessage(String messageID) {
		if (messagesCache.containsKey(messageID)) {
			return messagesCache.get(messageID);
		}
		return loadMessage(messageID);
	}
	
	private String loadMessage(String messageID) {
		String message = plugin.getFileConfigurationsUtils().getString(configPath, messageID);
		if (message != null) {
			this.messagesCache.put(messageID, message);
		}
		return message;
	}
	
	private void clearCache() {
		this.messagesCache.clear();
	}

	@Override
	public void reload() {
		clearCache();
	}

	@Override
	public Collection<ReloadFileData> getReloadFilenames() {
		Set<ReloadFileData> filenames = new HashSet<>();
		filenames.add(new ReloadFileData(configPath, loadResource));
		return filenames;
	}
	
}
