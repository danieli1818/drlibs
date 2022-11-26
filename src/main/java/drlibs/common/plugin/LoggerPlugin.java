package drlibs.common.plugin;

import java.io.File;

import drlibs.utils.log.PluginLogger;

public interface LoggerPlugin extends AdvancedPlugin {

	public PluginLogger getPluginLogger();
	
	public File getDataFolder();
	
	public void saveResource(String resourcePath, boolean replace);
	
}
