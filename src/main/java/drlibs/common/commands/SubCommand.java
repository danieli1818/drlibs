package drlibs.common.commands;

import drlibs.common.plugin.PluginParameters;

public abstract class SubCommand extends BaseCommand implements AdvancedCommand {

	private AdvancedCommand fatherCommand;

	public SubCommand(PluginParameters pluginParameters, AdvancedCommand fatherCommand, String command, String description,
			String permission) {
		super(pluginParameters, command, description, permission);
		this.fatherCommand = fatherCommand;
	}

	public SubCommand(PluginParameters pluginParameters, AdvancedCommand fatherCommand, String command, String description,
			String permission, int numOfSubCommandsPerHelpPage) {
		super(pluginParameters, command, description, permission, numOfSubCommandsPerHelpPage);
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
