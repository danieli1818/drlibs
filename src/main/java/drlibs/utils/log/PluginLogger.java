package drlibs.utils.log;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import drlibs.utils.messages.MessagesStorage;
import drlibs.utils.reloader.Reloadable;
import drlibs.utils.reloader.reloadparams.ReloadParams;

// TODO Statically save default log transforms for the case of error in loading the logger
public class PluginLogger implements Reloadable {

	private String logFormat;

	private Logger logger;

	private MessagesStorage messagesStorage;

	public PluginLogger(String filesDirPath) {
		this("<message>", filesDirPath, "log_messages.yml");
	}

	public PluginLogger(String logFormat, String filesDirPath) {
		this(logFormat, filesDirPath, "log_messages.yml");
	}

	public PluginLogger(String logFormat, String filesDirPath, String messagesConfigRelativeFilePath) {
		this.logFormat = logFormat;
		this.logger = Bukkit.getLogger();
		this.messagesStorage = new MessagesStorage(filesDirPath, messagesConfigRelativeFilePath);
		this.messagesStorage.setDefaultMessagesConfigMap(getDefaultMessagesConfig());
	}
	
	private Map<String, String> getDefaultMessagesConfig() {
		return Map.of(
						// Parse
						"success_parse_message", "Successfully parsed <source>",
						"warning_parse_message", "Warning parsing <source> since: <error_message>",
						"error_parse_load_message", "Error loading <source> since: <error_message>",
						"error_parse_error_message", "Error parsing <source> since: <error_message>",
						"error_invalid_parse_result_type_message", "Unknown (<error_type>) parse result type of <source> with the errormessage: <error_message>",

						// Post Processing
						"success_post_processing_message", "Successfully post processed <source>",
						"warning_post_processing_message", "Warning post processing <source> since: <error_message>",
						"error_post_processing_message", "Error post processing <source> since: <error_message>",
						"error_invalid_post_processing_result_type_message", "Unknown (<error_type>) post processing result type of <source> with the errormessage: <error_message>");
	}

	public void log(Level level, String message) {
		if (logFormat.contains("<message>")) {
			logger.log(level, logFormat.replaceAll("<message>", message));
		} else {
			logger.log(Level.WARNING,
					"Using the message to log and ignoring log format since it doesn't contain {message} format specifier!");
			logger.log(level, message);
		}
	}

	public void log(Level level, String message, Exception e) {
		log(level, message);
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
		String message = messagesStorage.getMessage(messageID);
		if (message == null) {
			log(Level.WARNING, "Can't find message ID: " + messageID + "!");
			return null;
		}
		return message;
	}

	@Override
	public Collection<ReloadParams> getReloadParams() {
		return messagesStorage.getReloadParams();
	}

}
