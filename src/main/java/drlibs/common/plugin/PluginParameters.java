package drlibs.common.plugin;

import drlibs.utils.log.PluginLogger;
import drlibs.utils.messages.MessagesSender;

public interface PluginParameters {

	public String getPluginID();
	
	public PluginLogger getPluginLogger();
	
	public MessagesSender getMessagesSender();
	
}
