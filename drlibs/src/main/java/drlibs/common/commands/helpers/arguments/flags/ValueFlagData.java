package drlibs.common.commands.helpers.arguments.flags;

import java.util.ArrayList;
import java.util.List;

import drlibs.utils.datastructures.tuples.Tuple;

public class ValueFlagData extends BaseFlagData {
	
	private List<String> values;

	public ValueFlagData(String description) {
		this(description, null);
	}
	
	public ValueFlagData(String description, String defaultValue) {
		super(description, defaultValue);
		this.values = new ArrayList<>();
	}
	
	@Override
	public List<String> getFlagValues(int offset) {
		if (offset == 1) {
			return values;
		}
		return super.getFlagValues(offset);
	}
	
	public ValueFlagData addFlagValue(String value) {
		if (value != null) {
			values.add(value);
		}
		return this;
	}
	
	public ValueFlagData addFlagValues(String... values) {
		for (String value : values) {
			addFlagValue(value);
		}
		return this;
	}

	@Override
	public Tuple<Object, Integer> parseValue(final String[] args, final int flagIndex) {
		if (flagIndex + 1 >= args.length) {
			return null;
		}
		return new Tuple<Object, Integer>(args[flagIndex + 1], 1);
	}

	@Override
	public String getFlagSuffix() {
		return "<Value>";
	}

}
