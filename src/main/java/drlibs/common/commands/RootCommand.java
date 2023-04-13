package drlibs.common.commands;

import drlibs.common.plugin.MessagesPlugin;

public abstract class RootCommand extends BaseCommand {

	private String invalidCommandMessageID;
	private String noPermissionMessageID;
	private String playerCommandMessageID;

	public RootCommand(MessagesPlugin plugin, String command, String description, String permission,
			String invalidCommandMessageID, String noPermissionMessageID, String playerCommandMessageID) {
		super(plugin, command, description, permission);
		this.invalidCommandMessageID = invalidCommandMessageID;
		this.noPermissionMessageID = noPermissionMessageID;
		this.playerCommandMessageID = playerCommandMessageID;
	}

	public RootCommand(MessagesPlugin plugin, String command, String description, String permission,
			int numOfSubCommandsPerHelpPage, String invalidCommandMessageID, String noPermissionMessageID,
			String playerCommandMessageID) throws IllegalArgumentException {
		super(plugin, command, description, permission, numOfSubCommandsPerHelpPage);
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
