package drlibs.common.commands;

import java.util.function.Consumer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import drlibs.common.plugin.MessagesPlugin;

public class ConsumerRootCommand extends RootCommand implements AdvancedCommand {

	Consumer<CommandSender> consumer;

	public ConsumerRootCommand(MessagesPlugin plugin, Consumer<CommandSender> consumer, String description,
			String permission, String invalidCommandMessageID, String noPermissionMessageID,
			String playerCommandMessageID) {
		super(plugin, description, permission, invalidCommandMessageID, noPermissionMessageID, playerCommandMessageID);
		this.consumer = consumer;
	}

	public ConsumerRootCommand(MessagesPlugin plugin, Consumer<CommandSender> consumer, String description,
			String permission, int numOfSubCommandsPerHelpPage, String invalidCommandMessageID,
			String noPermissionMessageID, String playerCommandMessageID) throws IllegalArgumentException {
		super(plugin, description, permission, numOfSubCommandsPerHelpPage, invalidCommandMessageID,
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
