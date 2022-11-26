package drlibs.utils.log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import drlibs.common.plugin.FilesPlugin;
import drlibs.utils.reloader.ReloadFileData;
import drlibs.utils.reloader.Reloadable;

public class PluginLogger implements Reloadable {
	
	private FilesPlugin plugin;

	private Logger logger;
	
	private String messagesConfigFilePath;
	
	public PluginLogger(FilesPlugin plugin) {
		this(plugin, "log_messages.yml");
	}
	
	public PluginLogger(FilesPlugin plugin, String messagesConfigFilePath) {
		this.plugin = plugin;
		this.logger = Bukkit.getLogger();
		this.messagesConfigFilePath = messagesConfigFilePath;
	}
	
	public void log(Level level, String message) {
		logger.log(level, message);
	}
	
	public void log(Level level, String message, Exception e) {
		logger.log(level, message);
		e.printStackTrace();
	}
	
	public boolean logTranslated(Level level, String messageID) {
		String message = getMessageByID(messageID);
		if (message == null) {
			return false;
		}
		if (!message.equals("")) {
			log(level, message);
		}
		return true;
	}
	
	public boolean logTranslated(Level level, String messageID, Exception e) {
		String message = getMessageByID(messageID);
		if (message == null) {
			return false;
		}
		if (!message.equals("")) {
			log(level, message, e);
		}
		return true;
	}
	
	public boolean logTranslated(Level level, String messageID, Map<String, String> variables) {
		String message = getMessageByID(messageID);
		if (message == null) {
			return false;
		}
		if (!message.equals("")) {
			return true;
		}
		for (Map.Entry<String, String> variableEntry : variables.entrySet()) {
			String variableID = variableEntry.getKey();
			String value = variableEntry.getValue();
			if (variableID == null) {
				log(Level.WARNING, "Variable ID is null!");
				continue;
			}
			if (value == null) {
				value = "";
			}
			message.replaceAll("<" + variableID + ">", value);
		}
		log(level, message);
		return true;
	}
	
	public boolean logTranslated(Level level, String messageID, Map<String, String> variables, Exception e) {
		String message = getMessageByID(messageID);
		if (message == null) {
			return false;
		}
		if (!message.equals("")) {
			return true;
		}
		for (Map.Entry<String, String> variableEntry : variables.entrySet()) {
			String variableID = variableEntry.getKey();
			String value = variableEntry.getValue();
			if (variableID == null) {
				log(Level.WARNING, "Variable ID is null!");
				continue;
			}
			if (value == null) {
				value = "";
			}
			message.replaceAll("<" + variableID + ">", value);
		}
		log(level, message, e);
		return true;
	}
	
	private String getMessageByID(String messageID) {
		String message = plugin.getFileConfigurationsUtils().getString(messagesConfigFilePath, messageID);
		if (message == null) {
			log(Level.WARNING, "Can't find message ID: " + messageID + "!");
			return null;
		}
		return message;
	}

	@Override
	public void reload() {
		
	}

	@Override
	public Collection<ReloadFileData> getReloadFilenames() {
		Set<ReloadFileData> filenames = new HashSet<>();
		filenames.add(new ReloadFileData(messagesConfigFilePath, true));
		return filenames;
	}
	
}
