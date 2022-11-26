package drlibs.common.commands;

import drlibs.common.plugin.MessagesPlugin;

public abstract class SubCommand extends BaseCommand implements AdvancedCommand {

	private AdvancedCommand fatherCommand;

	public SubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String description, String permission) {
		super(plugin, description, permission);
		this.fatherCommand = fatherCommand;
	}

	public SubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String description, String permission,
			int numOfSubCommandsPerHelpPage) {
		super(plugin, description, permission, numOfSubCommandsPerHelpPage);
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

}
