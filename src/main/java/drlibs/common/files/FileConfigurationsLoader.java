package drlibs.common.files;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import drlibs.common.plugin.LoggerPlugin;
import drlibs.utils.log.PluginLogger;

public class FileConfigurationsLoader {

	private LoggerPlugin plugin;
	private BiMap<String, FileConfiguration> fileConfigurations;
	
	public FileConfigurationsLoader(LoggerPlugin plugin) {
		this.plugin = plugin;
		fileConfigurations = HashBiMap.create();
	}
	
	public boolean createPluginFolder() {
		if (!plugin.getDataFolder().exists()) {
			return plugin.getDataFolder().mkdirs();
		}
		return plugin.getDataFolder().isDirectory();
	}
	
	public FileConfiguration getFileConfiguration(String filePath) {
		return getFileConfiguration(filePath, true);
	}
	
	public FileConfiguration getFileConfiguration(String filePath, boolean shouldLoadFileConfigurationIfNotLoaded) {
		if (!fileConfigurations.containsKey(filePath)) {
			if (shouldLoadFileConfigurationIfNotLoaded) {
				loadFileConfiguration(filePath);
			}
		}
		return fileConfigurations.get(filePath);
	}
	
	public FileConfiguration getFileConfiguration(String filePath, boolean createPath, boolean loadResource, boolean shouldReplaceFile) {
		if (!fileConfigurations.containsKey(filePath)) {
			loadFileConfiguration(filePath, createPath, loadResource, shouldReplaceFile);
		}
		return fileConfigurations.get(filePath);
	}
	
	public boolean loadFileConfiguration(String filePath) {
		return loadFileConfiguration(filePath, true);
	}
	
	public boolean loadFileConfiguration(String filePath, boolean createPath) {
		return loadFileConfiguration(filePath, createPath, false, false);
	}
	
	public boolean loadFileConfiguration(String filePath, boolean createPath, boolean loadResource, boolean shouldReplaceFile) {
		File file = new File(getAbsolutePath(filePath));
		if (!file.exists() && createPath) {
			if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
				getPluginLogger().log(Level.SEVERE, "Couldn't create file path: " + filePath);
				return false;
			}
			if (loadResource) {
				try {
					plugin.saveResource(filePath, shouldReplaceFile);
				} catch (IllegalArgumentException e) {
					getPluginLogger().log(Level.SEVERE, "Couldn't save file at: " + filePath + " with its resource", e);
					return false;
				}
			}
		}
		if (!file.exists() || !file.isFile()) {
			getPluginLogger().log(Level.SEVERE, "File at: " + filePath + " doesn't exist!");
			return false;
		}
		FileConfiguration fileConfiguration = new YamlConfiguration();
		try {
			fileConfiguration.load(file);
		} catch (Exception e) {
			getPluginLogger().log(Level.SEVERE, "Couldn't load file configuration of file: " + filePath, e);
			return false;
		}
		fileConfigurations.put(filePath, fileConfiguration);
		return true;
	}
	
	public boolean saveFileConfiguration(String filePath) {
		FileConfiguration config = fileConfigurations.get(filePath);
		if (config == null) {
			return false;
		}
		try {
			config.save(new File(getAbsolutePath(filePath)));
		} catch (IOException e) {
			getPluginLogger().log(Level.SEVERE, "Couldn't save file configuration at: " + filePath, e);
			return false;
		}
		return true;
	}
	
	private String getAbsolutePath(String relativePath) {
		return plugin.getDataFolder().getAbsolutePath() + File.separator + relativePath;
	}
	
	private LoggerPlugin getPlugin() {
		return plugin;
	}
	
	private PluginLogger getPluginLogger() {
		return getPlugin().getPluginLogger();
	}
	
}
