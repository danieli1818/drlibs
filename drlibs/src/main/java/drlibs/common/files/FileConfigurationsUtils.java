package drlibs.common.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

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
	
	public boolean loadFileConfiguration(String filePath, boolean loadResource) {
		return loader.loadFileConfiguration(filePath, true, true, false);
	}
	
	public boolean loadFileConfiguration(String filePath, boolean loadResource, boolean replaceFile) {
		return loader.loadFileConfiguration(filePath, true, true, true);
	}
	
	public boolean loadFileConfigurationOrCreate(String filePath) {
		return loader.loadFileConfiguration(filePath, true, false, false);
	}
	
	public boolean loadFileConfigurationOrDefault(String filePath) {
		return loader.loadFileConfiguration(filePath, true, true, false);
	}
	
	public boolean loadDefaultFileConfiguration(String filePath) {
		return loader.loadFileConfiguration(filePath, true, true, true);
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
	
	public Serializable getSerializable(String filePath, String valuePath) throws ClassNotFoundException {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		String data = fileConfiguration.getString(valuePath);
		if (data == null) {
			return null;
		}
		try {
			Object object = new ObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data))).readObject();
			if (object == null || !(object instanceof Serializable)) {
				return null;
			}
			return (Serializable) object;
		} catch (IOException e) {
			return null;
		}
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
	
	public boolean setObject(String filePath, String valuePath, Object value) {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return false;
		}
		fileConfiguration.set(valuePath, value);
		return true;
	}
	
	public boolean setSerializable(String filePath, String valuePath, Serializable value) throws IOException {
		FileConfiguration fileConfiguration = getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return false;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(value);
		objectOutputStream.close();
		fileConfiguration.set(valuePath, Base64Coder.encodeLines(outputStream.toByteArray()));
		return true;
	}
	
	public boolean save(String filePath) {
		return loader.saveFileConfiguration(filePath);
	}
	
	public boolean hasKey(String filePath, String key) {
		FileConfiguration fileConfiguration = loader.getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return false;
		}
		return fileConfiguration.getKeys(false).contains(key);
	}
	
	public Set<String> getKeys(String filePath) {
		FileConfiguration fileConfiguration = loader.getFileConfiguration(filePath);
		if (fileConfiguration == null) {
			return null;
		}
		return fileConfiguration.getKeys(false);
	}
	
	public String getRelativeFilePath(String absoluteFilePath) {
		return loader.getRelativePath(absoluteFilePath);
	}
	
	public String getAbsoluteFilePath(String relativeFilePath) {
		return loader.getAbsolutePath(relativeFilePath);
	}
	
	private FileConfiguration getFileConfiguration(String filePath) {
		return loader.getFileConfiguration(filePath);
	}
	
}
