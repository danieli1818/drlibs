package drlibs.common.commands.helpers.arguments.flags;

import java.util.List;

import drlibs.utils.datastructures.tuples.Tuple;

public interface FlagData {

	/**
	 * Returns the description of the flag.
	 * 
	 * @return The description of the flag
	 */
	public String getDescription();

	/**
	 * Returns all possible flag values if there are infinite possibilities or the
	 * flag takes no value it returns null.
	 * 
	 * @param offset The offset of the value from the flag, for example if it's
	 *               right after the flag it's 1
	 * @return All possible flag values
	 */
	public List<String> getFlagValues(int offset);

	/**
	 * It parses the flag values of the flag and returns a tuple of the value and
	 * how many times the flagIndex needs to increment.
	 * 
	 * @param args      The arguments of the command
	 * @param flagIndex The index where the flag is in the args
	 * @return The value of the flag and how many times the flagIndex needs to
	 *         increment as a tuple
	 */
	public Tuple<Object, Integer> parseValue(final String[] args, final int flagIndex);

	/**
	 * Returns the syntax of the flag's parameters.
	 * 
	 * @return The syntax of the flag's parameters
	 */
	public String getFlagSuffix();

	/**
	 * Returns the default value if exists, else it returns null.
	 * 
	 * @return The default value if exists
	 */
	public Object getDefaultValue();

}
