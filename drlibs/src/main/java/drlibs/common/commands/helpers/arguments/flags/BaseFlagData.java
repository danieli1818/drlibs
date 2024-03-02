package drlibs.common.commands.helpers.arguments.flags;

import java.util.List;

public abstract class BaseFlagData implements FlagData {

	private String description;
	private Object defaultValue;
	
	public BaseFlagData(String description) {
		this(description, null);
	}
	
	public BaseFlagData(String description, Object defaultValue) {
		this.description = description;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<String> getFlagValues(int offset) {
		return null;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}

}
