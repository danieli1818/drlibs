package drlibs.common.plugin;

import java.io.File;

import drlibs.utils.log.PluginLogger;
import drlibs.utils.messages.MessagesSender;

public class BasePluginParameters implements PluginProperties {

	private String pluginID;
	private PluginLogger pluginLogger;
	private MessagesSender messagesSender;
	private File dataFolder;
	
	public BasePluginParameters(String pluginID, PluginLogger pluginLogger, MessagesSender messagesSender, File dataFolder) {
		this.pluginID = pluginID;
		this.pluginLogger = pluginLogger;
		this.messagesSender = messagesSender;
		this.dataFolder = dataFolder;
	}
	
	@Override
	public String getPluginID() {
		return pluginID;
	}

	@Override
	public PluginLogger getPluginLogger() {
		return pluginLogger;
	}

	@Override
	public MessagesSender getMessagesSender() {
		return messagesSender;
	}
	
	public File getDataFolder() {
		return dataFolder;
	}

}
