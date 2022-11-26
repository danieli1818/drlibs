package drlibs.common.plugin;

import drlibs.common.files.FileConfigurationsUtils;
import drlibs.utils.reloader.ReloaderManager;

public interface FilesPlugin extends LoggerPlugin {

	public FileConfigurationsUtils getFileConfigurationsUtils();
	
	public ReloaderManager getReloaderManager();
	
}
