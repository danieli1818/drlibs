package drlibs.common.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import drlibs.common.plugin.MessagesPlugin;

public abstract class BaseCommand implements AdvancedCommand {

	private static final String NULL_SUB_COMMAND_ID = "null_sub_command_id";

	private MessagesPlugin plugin;

	private Map<String, AdvancedCommand> commands;
	private Map<String, String> aliases;

	private String description;
	private String permission;

	public BaseCommand(MessagesPlugin plugin, String description, String permission) {
		this.plugin = plugin;
		this.commands = new HashMap<>();
		this.commands.put("help", new HelpCommand(plugin, this, 5));
		this.description = description;
		this.permission = permission;
	}

	public BaseCommand(MessagesPlugin plugin, String description, String permission, int numOfSubCommandsPerHelpPage)
			throws IllegalArgumentException {
		if (numOfSubCommandsPerHelpPage <= 0) {
			throw new IllegalArgumentException("The number of sub commands per help page must be positive!");
		}
		this.plugin = plugin;
		this.commands.put("help", new HelpCommand(plugin, this, numOfSubCommandsPerHelpPage));
		this.description = description;
		this.permission = permission;
	}

	public BaseCommand addSubCommand(String id, BaseCommand command) {
		if (id == null) {
			Bukkit.getLogger().log(Level.WARNING, NULL_SUB_COMMAND_ID);
			return this;
		}
		if (command == null) {
			return removeSubCommand(id);
		}
		this.commands.put(id, command);
		return this;
	}

	public BaseCommand addSubCommand(String id, BaseCommand command, String[] aliases) {
		addSubCommand(id, command);
		for (String alias : aliases) {
			this.aliases.put(alias, id);
		}
		return this;
	}

	public BaseCommand addSubCommand(String id, BaseCommand command, Collection<String> aliases) {
		return addSubCommand(id, command, (String[]) aliases.toArray());
	}

	public BaseCommand removeSubCommand(String id) {
		this.commands.remove(id);
		List<String> aliases = new ArrayList<>();
		for (Map.Entry<String, String> alias : this.aliases.entrySet()) {
			if (alias.getValue().equals(id)) {
				aliases.add(alias.getKey());
			}
		}
		for (String alias : aliases) {
			this.commands.remove(alias);
		}
		return this;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length >= 1) {
			String subCommandStr = args[0];
			AdvancedCommand subCommand = commands.get(subCommandStr);
			if (subCommand != null) {
				subCommand.onCommand(sender, new CommandTransformer(subCommandStr, subCommand), subCommandStr,
						Arrays.copyOfRange(args, 1, args.length));
				return true;
			}
		} else {
			if (!sender.hasPermission(getPermission())) {
				plugin.getMessagesSender().sendTranslatedMessage(getNoPermissionMessageID(), sender);
			}
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> autocompleteOptions = new ArrayList<>();
		switch (args.length) {
		case 1:
			String startCommand = args[0];
			for (String subCommandID : getSubCommands().keySet()) {
				if (subCommandID.startsWith(startCommand)) {
					autocompleteOptions.add(subCommandID);
				}
			}
		}
		return autocompleteOptions;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Map<String, AdvancedCommand> getSubCommands() {
		return commands;
	}

	@Override
	public String getPermission() {
		return permission;
	}
	
	public MessagesPlugin getPlugin() {
		return plugin;
	}

}
