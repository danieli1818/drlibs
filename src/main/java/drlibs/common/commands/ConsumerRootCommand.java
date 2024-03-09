package drlibs.common.commands;

import java.util.function.Consumer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import drlibs.common.plugin.PluginParameters;

public class ConsumerRootCommand extends RootCommand implements AdvancedCommand {

	Consumer<CommandSender> consumer;

	public ConsumerRootCommand(PluginParameters pluginParameters, Consumer<CommandSender> consumer, String command,
			String description, String permission, String invalidCommandMessageID, String noPermissionMessageID,
			String playerCommandMessageID) {
		super(pluginParameters, command, description, permission, invalidCommandMessageID, noPermissionMessageID,
				playerCommandMessageID);
		this.consumer = consumer;
	}

	public ConsumerRootCommand(PluginParameters pluginParameters, Consumer<CommandSender> consumer, String command,
			String description, String permission, int numOfSubCommandsPerHelpPage, String invalidCommandMessageID,
			String noPermissionMessageID, String playerCommandMessageID) throws IllegalArgumentException {
		super(pluginParameters, command, description, permission, numOfSubCommandsPerHelpPage, invalidCommandMessageID,
				noPermissionMessageID, playerCommandMessageID);
		this.consumer = consumer;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean superOnCommandReturnValue = super.onCommand(sender, command, label, args);
		consumer.accept(sender);
		return superOnCommandReturnValue;
	}

}
