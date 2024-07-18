package drlibs.utils.savers.types.yaml;

import java.nio.file.Path;

public class YamlSaverParams {

	public enum SaveMode {
		OVERWRITE_FILE,
		OVERWRITE_FILE_WITH_KEY,
		OVERWRITE_KEY,
		APPEND
	}
	
	private SaveMode saveMode;
	private Path relativeFilePath;
	private String key; // The key to save the yaml serializable in.
	
	public YamlSaverParams(SaveMode saveMode, Path relativeFilePath, String key) {
		this.saveMode = saveMode;
		this.relativeFilePath = relativeFilePath;
		this.key = key;
	}
	
	public SaveMode getSaveMode() {
		return saveMode;
	}
	
	public Path getRelativeFilePath() {
		return relativeFilePath;
	}
	
	public String getKey() {
		return key;
	}
	
}
