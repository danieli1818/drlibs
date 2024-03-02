package drlibs.common.commands;

import java.util.function.Consumer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import drlibs.common.plugin.MessagesPlugin;

public class ConsumerSubCommand extends SubCommand implements AdvancedCommand {

	Consumer<CommandSender> consumer;

	public ConsumerSubCommand(MessagesPlugin plugin, Consumer<CommandSender> consumer, AdvancedCommand fatherCommand,
			String command, String description, String permission) {
		super(plugin, fatherCommand, command, description, permission);
		this.consumer = consumer;
	}

	public ConsumerSubCommand(MessagesPlugin plugin, Consumer<CommandSender> consumer, AdvancedCommand fatherCommand,
			String command, String description, String permission, int numOfSubCommandsPerHelpPage)
			throws IllegalArgumentException {
		super(plugin, fatherCommand, command, description, permission, numOfSubCommandsPerHelpPage);
		this.consumer = consumer;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean superOnCommandReturnValue = super.onCommand(sender, command, label, args);
		consumer.accept(sender);
		return superOnCommandReturnValue;
	}

}
