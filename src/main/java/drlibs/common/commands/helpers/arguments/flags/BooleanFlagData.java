package drlibs.common.commands.helpers.arguments.flags;

import drlibs.utils.datastructures.tuples.Tuple;

public class BooleanFlagData extends BaseFlagData {

	public BooleanFlagData(String description) {
		super(description, false);
	}
	
	@Override
	public Tuple<Object, Integer> parseValue(String[] args, int flagIndex) {
		return new Tuple<Object, Integer>(true, 0);
	}

	@Override
	public String getFlagSuffix() {
		return null;
	}
	
}
