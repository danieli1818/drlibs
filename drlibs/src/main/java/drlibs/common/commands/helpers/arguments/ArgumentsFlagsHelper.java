package drlibs.common.commands.helpers.arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import drlibs.common.commands.helpers.arguments.flags.FlagData;
import drlibs.utils.datastructures.tuples.Tuple;

/**
 * The ArgumentsFlagsHelper class is meant to be a command helper to parse and
 * show help for the command when it uses arguments flags
 * 
 * @author Daniel Rottner
 *
 */
public class ArgumentsFlagsHelper {

	private String flagsPrefix;
	private String commandPrefix;
	private Map<String, FlagData> flagsMap;

	public ArgumentsFlagsHelper(String commandPrefix) {
		this.flagsPrefix = "-";
		this.commandPrefix = commandPrefix;
		this.flagsMap = new HashMap<>();
	}

	public ArgumentsFlagsHelper(String commandPrefix, String flagsPrefix) {
		this(commandPrefix);
		if (flagsPrefix != null) {
			this.flagsPrefix = flagsPrefix;
		}
	}

	/**
	 * Registers the flag with the flagData, if the flag is already registered it
	 * overrides it. It returns the instance to support fluent programming. If the
	 * flag or the flagData are null it doesn't do anything.
	 * 
	 * @param flag     The flag to register
	 * @param flagData The flag data
	 * @return This instance
	 */
	public ArgumentsFlagsHelper registerFlag(String flagID, FlagData flagData) {
		if (flagID != null && flagData != null) {
			flagsMap.put(flagID, flagData);
		}
		return this;
	}

	/**
	 * Unregisters the flag and returns this instance for fluent programming
	 * 
	 * @param flag The flag to unregister
	 * @return This instance
	 */
	public ArgumentsFlagsHelper unregisterFlag(String flag) {
		flagsMap.remove(flag);
		return this;
	}

	/**
	 * Returns the full command syntax with the saved prefix
	 * 
	 * @return
	 */
	public String getCommandSyntax() {
		String helpMessage = commandPrefix;
		for (Map.Entry<String, FlagData> flagDataEntry : flagsMap.entrySet()) {
			String flagSuffix = flagDataEntry.getValue().getFlagSuffix().trim();
			helpMessage += " -" + flagDataEntry.getKey();
			if (flagSuffix != null && !flagSuffix.isEmpty()) {
				helpMessage += " " + flagSuffix;
			}
		}
		return helpMessage;
	}

	/**
	 * Returns the flag help message with its description. If the flag doesn't exist
	 * it returns null.
	 * 
	 * @param flag The flag to get the help message of
	 * @return The flag help message
	 */
	public String getFlagHelp(String flag) {
		FlagData data = flagsMap.get(flag);
		if (data == null) {
			return null;
		}
		return flagsPrefix + flag + " - " + data.getDescription();
	}

	/**
	 * Parses the command's arguments and returns a map of the flags in the
	 * arguments and their values
	 * 
	 * @param args The command's arguments
	 * @return The map of the flags in the arguments and their values
	 */
	public Map<String, Object> parseArgs(String[] args) {
		Map<String, Object> flagsValues = new HashMap<>();
		for (int i = 0; i < args.length; i++) {
			String flag = parseFlagFromArgument(args[i]);
			if (flag == null) {
				continue;
			}
			FlagData flagData = flagsMap.get(flag);
			Tuple<Object, Integer> flagParseResult = flagData.parseValue(args, i);
			if (flagParseResult == null) {
				continue;
			}
			flagsValues.put(flag, flagParseResult.getFirst());
			i += flagParseResult.getSecond() != null ? flagParseResult.getSecond() : 0;
		}
		return flagsValues;
	}

	/**
	 * Parses the command's arguments and returns a map of all the registered flags
	 * with their value from the arguments or if there isn't, their default value.
	 * 
	 * @param args The command's arguments
	 * @return The map of all the registered flags in the arguments and their values
	 */
	public Map<String, Object> parseArgsOrDefault(String[] args) {
		Map<String, Object> flagsValues = parseArgs(args);
		for (Map.Entry<String, FlagData> flagEntry : flagsMap.entrySet()) {
			if (!flagsValues.containsKey(flagEntry.getKey())) {
				flagsValues.put(flagEntry.getKey(), flagEntry.getValue().getDefaultValue());
			}
		}
		return flagsValues;
	}

	/**
	 * Returns the autocomplete options of the last flag in the args if exists, else
	 * returns null.
	 * 
	 * @param args The arguments
	 * @return The autocomplete options of the last flag in the args if exists, else
	 *         null
	 */
	private List<String> getAutocompleteOptionsOfLastFlag(String[] args) {
		for (int i = args.length - 1; i >= 0; i--) {
			String flag = parseFlagFromArgument(args[i]);
			if (flag != null) {
				return flagsMap.get(flag).getFlagValues(args.length - i - 1);
			}
		}
		return null;
	}

	/**
	 * Returns the autocomplete options of the last flag in the args if exists, else
	 * returns the registered flags not in the args if there are any, else returns
	 * null.
	 * 
	 * @param args The arguments
	 * @return The autocomplete options of the last flag in the args if exists, else
	 *         returns the registered flags not in the args if there are any, else
	 *         null
	 */
	public List<String> getAutocompleteOptions(String[] args) {
		List<String> autocompleteOptions = getAutocompleteOptionsOfLastFlag(args);
		System.out.println("Auto Complete Options Last Flag: " + autocompleteOptions);
		if (autocompleteOptions == null) {
			Set<String> missingFlags = getMissingFlagsInArgs(args);
			System.out.println("Missing Flags: " + missingFlags);
			if (!missingFlags.isEmpty()) {
				autocompleteOptions = missingFlags.stream().map((String flag) -> flagsPrefix + flag)
						.collect(Collectors.toList());
			}
		}
		if (args.length > 0) {
			String prefix = args[args.length - 1];
			autocompleteOptions = autocompleteOptions.stream()
					.filter((String autocompleteOption) -> autocompleteOption.startsWith(prefix))
					.collect(Collectors.toList());
			autocompleteOptions.sort(String::compareTo);
		}
		System.out.println("Auto Complete Options: " + autocompleteOptions);
		return autocompleteOptions;
	}

	public List<String> getFlagsInArgs(String[] args) {
		List<String> flags = new ArrayList<>();
		for (String arg : args) {
			String flag = parseFlagFromArgument(arg);
			if (flag != null) {
				flags.add(flag);
			}
		}
		return flags;
	}

	public Set<String> getMissingFlagsInArgs(String[] args) {
		Set<String> flags = new HashSet<>(flagsMap.keySet());
		for (String arg : args) {
			String flag = parseFlagFromArgument(arg);
			if (flag != null) {
				flags.remove(flag);
			}
		}
		return flags;
	}

	/**
	 * Try parsing the argument as a flag and if it succeeds it returns the flag's
	 * ID else it returns null
	 * 
	 * @param argument The flag argument
	 * @return The flag's ID if the argument is a flag else null
	 */
	private String parseFlagFromArgument(String argument) {
		if (!argument.startsWith(flagsPrefix)) {
			return null;
		}
		String flag = argument.substring(flagsPrefix.length());
		if (flagsMap.containsKey(flag)) {
			return flag;
		}
		return null;
	}

}
