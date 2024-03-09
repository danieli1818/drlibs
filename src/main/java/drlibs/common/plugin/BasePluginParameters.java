package drlibs.common.plugin;

import drlibs.utils.log.PluginLogger;
import drlibs.utils.messages.MessagesSender;

public class BasePluginParameters implements PluginParameters {

	private String pluginID;
	private PluginLogger pluginLogger;
	private MessagesSender messagesSender;
	
	public BasePluginParameters(String pluginID, PluginLogger pluginLogger, MessagesSender messagesSender) {
		this.pluginID = pluginID;
		this.pluginLogger = pluginLogger;
		this.messagesSender = messagesSender;
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

}
