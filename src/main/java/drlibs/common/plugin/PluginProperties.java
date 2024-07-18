package drlibs.common.plugin;

import java.io.File;

import drlibs.utils.log.PluginLogger;
import drlibs.utils.messages.MessagesSender;

public interface PluginProperties {

	public String getPluginID();
	
	public PluginLogger getPluginLogger();
	
	public MessagesSender getMessagesSender();
	
	public File getDataFolder();
	
}
