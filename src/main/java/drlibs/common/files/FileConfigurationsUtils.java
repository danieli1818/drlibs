package drlibs.common.files;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class FileConfigurationsUtils {

	private FileConfigurationsLoader loader;
	
	public FileConfigurationsUtils(FileConfigurationsLoader loader) {
		this.loader = loader;
	}
	
	public boolean loadFileConfiguration(String filePath) {
		return loader.loadFileConfiguration(filePath);
	}
	
	public void loadFileConfigurations(Collection<String> filePaths) {
		for (String filePath : filePaths) {
			loader.loadFileConfiguration(filePath);
		}
	}
	
	public void loadFileConfigurations(Collection<String> filePaths, boolean loadResource) {
		for (String filePath : filePaths) {
			loader.loadFileConfiguration(filePath, true, loadResource, false);
		}
	}
	
	public void loadFileConfigurations(Collection<String> filePaths, boolean loadResource, boolean replaceFile) {
		for (String filePath : filePaths) {
			loader.loadFileConfiguration(filePath, true, loadResource, replaceFile);
		}
	}
	
	public String getString(String filePath, String valuePath) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		return fileConfiguration.getString(valuePath);
	}
	
	public Integer getInteger(String filePath, String valuePath) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		return fileConfiguration.getInt(valuePath);
	}
	
	public Double getDouble(String filePath, String valuePath) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		return fileConfiguration.getDouble(valuePath);
	}
	
	public Boolean getBoolean(String filePath, String valuePath) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		return fileConfiguration.getBoolean(valuePath);
	}
	
	public ItemStack getItemStack(String filePath, String valuePath) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		return fileConfiguration.getItemStack(valuePath);
	}
	
	public boolean removeObject(String filePath, String valuePath) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return false;
		}
		fileConfiguration.set(filePath, null);
		return true;
	}
	
	public <T> Map<String, T> parseAllKeys(String filePath, Function<String, T> parser) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		Map<String, T> keysValuesMap = new HashMap<>();
		for (String key : fileConfiguration.getKeys(false)) {
			keysValuesMap.put(key, parser.apply(key));
		}
		return keysValuesMap;
	}
	
	public <T> BiMap<String, T> parseAllKeysBi(String filePath, Function<String, T> parser) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		BiMap<String, T> keysValuesMap = HashBiMap.create();
		for (String key : fileConfiguration.getKeys(false)) {
			keysValuesMap.put(key, parser.apply(key));
		}
		return keysValuesMap;
	}
	
	public void setObject(String filePath, String valuePath, Object value) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		fileConfiguration.set(valuePath, value);
	}
	
	public boolean save(String filePath) {
		return loader.saveFileConfiguration(filePath);
	}
	
	public boolean hasKey(String filePath, String key) {
		FileConfiguration fileConfiguration = loader.getFileConfiguration(filePath);
		return fileConfiguration.getKeys(false).contains(key);
	}
	
	private FileConfiguration getFileConfiguration(String filePath) {
		return loader.getFileConfiguration(filePath);
	}
	
}
