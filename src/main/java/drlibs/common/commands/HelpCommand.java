package drlibs.common.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.command.CommandSender;

import drlibs.common.plugin.PluginParameters;
import drlibs.utils.log.PluginLogger;
import drlibs.utils.messages.MessagesSender;

public class HelpCommand implements AdvancedCommand {

	private static final String ERROR_PAGE_INDEX_OUT_OF_BOUNDS = "error_page_index_out_of_bounds";
	private MessagesSender messagesSender;
	private PluginLogger pluginLogger;
	private AdvancedCommand command;
	private int numOfSubCommandsPerHelpPage;

	public HelpCommand(PluginParameters pluginParameters, AdvancedCommand command, int numOfSubCommandsPerHelpPage) throws NullArgumentException {
		if (command == null) {
			throw new NullArgumentException("command");
		}
		if (pluginParameters == null) {
			throw new NullArgumentException("pluginParameters");
		}
		pluginLogger = pluginParameters.getPluginLogger();
		if (pluginLogger == null) {
			throw new NullArgumentException("pluginLogger");
		}
		messagesSender = pluginParameters.getMessagesSender();
		if (messagesSender == null) {
			throw new NullArgumentException("messagesSender");
		}
		this.command = command;
		this.numOfSubCommandsPerHelpPage = numOfSubCommandsPerHelpPage;
	}

	public void sendHelp(CommandSender sender) {
		sendHelp(sender, 1);
	}

	public void sendHelp(CommandSender sender, int page) {
		page -= 1;
		if (page < 0) {
			messagesSender.sendTranslatedMessage(ERROR_PAGE_INDEX_OUT_OF_BOUNDS, sender);
			pluginLogger.logTranslated(Level.INFO, ERROR_PAGE_INDEX_OUT_OF_BOUNDS);
			return;
		}
		List<String> subCommands = getOrderedSubCommandsIDs().subList(getNumOfSubCommandsPerHelpPage() * page,
				Math.min(getNumOfSubCommands(), getNumOfSubCommandsPerHelpPage() * (page + 1)));
		Map<String, AdvancedCommand> subCommandsMap = command.getSubCommands();
		for (String subCommandID : subCommands) {
			messagesSender.sendMessage(subCommandID + " - " + subCommandsMap.get(subCommandID).getDescription(), sender);
		}
	}

	public List<String> getOrderedSubCommandsIDs() {
		List<String> subCommands = new ArrayList<>(command.getSubCommands().keySet());
		subCommands.sort(String::compareTo);
		System.out.println("SubCommands in help command: " + subCommands);
		return subCommands;
	}

	public int getNumOfSubCommandsPerHelpPage() {
		return numOfSubCommandsPerHelpPage;
	}

	public int getNumOfHelpPages() {
		return (int) Math.ceil(((float) getNumOfSubCommands()) / getNumOfSubCommandsPerHelpPage());
	}

	private int getNumOfSubCommands() {
		return command.getSubCommands().size();
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			messagesSender
					.sendErrorMessage(getNoPermissionMessageID().replace("<PERMISSION>", getPermission()), sender);
		}
		switch (args.length) {
		case 0:
			sendHelp(sender);
			break;
		case 1:
			try {
				int pageNum = Integer.parseInt(args[0]) - 1;
				if (pageNum < 0 || pageNum >= getNumOfHelpPages()) {
					messagesSender.sendErrorMessage(getInvalidCommandMessageID(), sender);
					return false;
				}
				sendHelp(sender, pageNum);
			} catch (NumberFormatException e) {
				if (getInvalidCommandMessageID() != null) {
					messagesSender.sendErrorMessage(getInvalidCommandMessageID(), sender);
					return false;
				}
			}
			break;
		default:
			messagesSender.sendErrorMessage(getInvalidCommandMessageID(), sender);
			return false;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias,
			String[] args) {
		List<String> autocompleteOptions = new ArrayList<>();
		switch (args.length) {
		case 0:
			int numOfHelpPages = getNumOfHelpPages();
			for (int i = 1; i <= numOfHelpPages; i++) {
				autocompleteOptions.add(String.valueOf(i));
			}
			break;
		case 1:
			String startPageStr = args[0];
			try {
				int startPage = Integer.parseInt(startPageStr);
				for (String pageStr : getPagesNumbersAsStringsThatStartsWithNum(startPage)) {
					autocompleteOptions.add(pageStr);
				}
			} catch (NumberFormatException e) {

			}
			break;
		}
		return autocompleteOptions;
	}

	private List<String> getPagesNumbersAsStringsThatStartsWithNum(int num) {
		List<String> pagesNumbers = new ArrayList<>();
		int numOfHelpPages = getNumOfHelpPages();
		if (num > numOfHelpPages) {
			return pagesNumbers;
		}
		pagesNumbers.add(String.valueOf(num));
		int currentNumber = num * 10;
		while (currentNumber <= numOfHelpPages) {
			for (int i = 0; i < 10; i++) {
				if (currentNumber + i <= numOfHelpPages) {
					pagesNumbers.add(String.valueOf(currentNumber + i));
				} else {
					break;
				}
			}
			currentNumber *= 10;
		}
		return pagesNumbers;
	}

	@Override
	public String getDescription() {
		return "The help command!";
	}

	@Override
	public Map<String, AdvancedCommand> getSubCommands() {
		return null;
	}

	@Override
	public String getInvalidCommandMessageID() {
		return command.getInvalidCommandMessageID();
	}

	@Override
	public String getPermission() {
		return command.getPermission() + ".help";
	}

	@Override
	public String getNoPermissionMessageID() {
		return command.getNoPermissionMessageID();
	}
	
	@Override
	public String getPlayerCommandMessageID() {
		return command.getPlayerCommandMessageID();
	}

	@Override
	public String getCommandPrefix() {
		return command.getCommandPrefix() + " help";
	}

}
