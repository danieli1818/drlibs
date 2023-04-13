package drlibs.common.commands;

import drlibs.common.plugin.MessagesPlugin;

public abstract class SubCommand extends BaseCommand implements AdvancedCommand {

	private AdvancedCommand fatherCommand;

	public SubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command, String description,
			String permission) {
		super(plugin, command, description, permission);
		this.fatherCommand = fatherCommand;
	}

	public SubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command, String description,
			String permission, int numOfSubCommandsPerHelpPage) {
		super(plugin, command, description, permission, numOfSubCommandsPerHelpPage);
		this.fatherCommand = fatherCommand;
	}

	@Override
	public String getInvalidCommandMessageID() {
		return fatherCommand.getInvalidCommandMessageID();
	}

	@Override
	public String getNoPermissionMessageID() {
		return fatherCommand.getNoPermissionMessageID();
	}

	@Override
	public String getPlayerCommandMessageID() {
		return fatherCommand.getPlayerCommandMessageID();
	}
	
	@Override
	public String getCommandPrefix() {
		return fatherCommand.getCommandPrefix() + " " + super.getCommandPrefix();
	}

}
