package drlibs.common.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTransformer extends Command {

	private AdvancedCommand command;
	
	public CommandTransformer(String name, AdvancedCommand command) {
		super(name);
		this.command = command;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		return command.onCommand(sender, this, commandLabel, args);
	}

}
