package drlibs.utils.savers.types.yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.Maps;

import drlibs.utils.files.JacksonUtils;
import drlibs.utils.savers.SaveResult;
import drlibs.utils.savers.SaveResult.Status;
import drlibs.utils.savers.Saver;
import drlibs.utils.savers.results.BaseSaveResult;
import drlibs.utils.savers.types.yaml.YamlSaverParams.SaveMode;

public class YamlSaver implements Saver<YamlSerializable, YamlSaverParams> {

	private Path yamlDirPath;
	
	public YamlSaver(Path yamlDirPath) {
		this.yamlDirPath = yamlDirPath;
	}
	
	// TODO Create the same for multiple yaml serializables.
	@Override
	public SaveResult save(YamlSerializable serializable, YamlSaverParams params) {
		Object dataObject = serializable.getDataObject();
		String xPath = params.getKey();
		Path yamlFilePath = yamlDirPath.resolve(params.getRelativeFilePath());
		SaveMode saveMode = params.getSaveMode();
		
		Object data = null;
		
		Yaml yaml = new Yaml();
		
		if (Files.exists(yamlFilePath)) {
			try {
				data = yaml.load(Files.newInputStream(yamlFilePath));
			} catch (IOException e) {
				e.printStackTrace();
				return new BaseSaveResult(Status.ERROR, e.getMessage());
			}
		} else {
			data = Maps.newHashMap();
		}
		
		try (Writer output = new FileWriter(yamlFilePath.toFile())) {
			yaml.dump(data, output);
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseSaveResult(Status.ERROR, e.getMessage());
		}
		
		
		// TODO Update to fit the above
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			Map.Entry<String, String> pathAndKey = JacksonUtils.splitPathAndKey(xPath);
			switch (saveMode) {
				case OVERWRITE_FILE:  // Run as it is when key is null, else remove all other keys and create the object in the path.
					if (xPath != null && Files.isRegularFile(yamlFilePath)) {
						JacksonUtils.clearAllFields(JacksonUtils.getNode(mapper, Files.newInputStream(yamlFilePath), ""));
					}
					mapper.writeValue(yamlFilePath.toFile(), dataObject);
					break;
				case OVERWRITE_FILE_WITH_KEY:
					if (xPath == null) {
						return new BaseSaveResult(Status.ERROR, "No key was given!");
					}
					if (Files.isRegularFile(yamlFilePath)) {
						JacksonUtils.clearAllFields(JacksonUtils.getNode(mapper, Files.newInputStream(yamlFilePath), ""));
					}
					JacksonUtils.createPathWithValue(mapper, Files.newInputStream(yamlFilePath), pathAndKey.getKey(), pathAndKey.getValue(), dataObject);
				case OVERWRITE_KEY:
					JacksonUtils.createPathWithValue(mapper, Files.newInputStream(yamlFilePath), pathAndKey.getKey(), pathAndKey.getValue(), dataObject);
					break;
				case APPEND:
					if (JacksonUtils.hasKey(mapper, Files.newInputStream(yamlFilePath), xPath)) {
						return new BaseSaveResult(Status.ERROR, "Key already exists!");
					}
					JacksonUtils.createPathWithValue(mapper, Files.newInputStream(yamlFilePath), pathAndKey.getKey(), pathAndKey.getValue(), dataObject);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new BaseSaveResult(Status.ERROR, e.getMessage());
		}
		return new BaseSaveResult(Status.SUCCESS, null);
	}
	
	private enum XPathSettingResultStatus {
		SUCCESS,
		ERROR_XPATH_ALREADY_EXISTS,
		ERROR_XPATH_PATH_CONTAINS_NON_MAP_VALUE
	}
	
	private XPathSettingResultStatus setXPathValue(Map<String, Object> data, String xPath, Object value) {
		String[] parts = xPath.split(".");
		Map<String, Object> currentMap = data;
		
		for (int i = 0; i < parts.length - 1; i++) {
			String part = parts[i];
			if (!currentMap.containsKey(part)) {
				currentMap.put(part, Maps.newHashMap());
				currentMap = (Map<String, Object>) currentMap.get(part);
			} else {
				Object partValue = currentMap.get(part);
				if (partValue instanceof Map) {
					currentMap = (Map<String, Object>) partValue;
				} else {
					return XPathSettingResultStatus.ERROR_XPATH_PATH_CONTAINS_NON_MAP_VALUE;  // TODO Add the path and the type
				}
			}
		}
		
		if (currentMap.containsKey(parts[parts.length - 1])) {
			return XPathSettingResultStatus.ERROR_XPATH_ALREADY_EXISTS;  // TODO Add the path and the type
		}
		currentMap.put(parts[parts.length - 1], value);
		return XPathSettingResultStatus.SUCCESS;
	}

}
