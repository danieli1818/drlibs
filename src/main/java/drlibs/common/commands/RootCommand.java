package drlibs.common.commands;

import drlibs.common.plugin.PluginParameters;

public abstract class RootCommand extends BaseCommand {

	private String invalidCommandMessageID;
	private String noPermissionMessageID;
	private String playerCommandMessageID;

	public RootCommand(PluginParameters pluginParameters, String command, String description, String permission,
			String invalidCommandMessageID, String noPermissionMessageID, String playerCommandMessageID) {
		super(pluginParameters, command, description, permission);
		this.invalidCommandMessageID = invalidCommandMessageID;
		this.noPermissionMessageID = noPermissionMessageID;
		this.playerCommandMessageID = playerCommandMessageID;
	}

	public RootCommand(PluginParameters pluginParameters, String command, String description, String permission,
			int numOfSubCommandsPerHelpPage, String invalidCommandMessageID, String noPermissionMessageID,
			String playerCommandMessageID) throws IllegalArgumentException {
		super(pluginParameters, command, description, permission, numOfSubCommandsPerHelpPage);
		this.invalidCommandMessageID = invalidCommandMessageID;
		this.noPermissionMessageID = noPermissionMessageID;
		this.playerCommandMessageID = playerCommandMessageID;
	}

	@Override
	public String getInvalidCommandMessageID() {
		return this.invalidCommandMessageID;
	}

	@Override
	public String getNoPermissionMessageID() {
		return this.noPermissionMessageID;
	}

	@Override
	public String getPlayerCommandMessageID() {
		return this.playerCommandMessageID;
	}

}
