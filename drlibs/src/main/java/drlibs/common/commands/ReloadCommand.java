package drlibs.common.commands;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drlibs.common.plugin.MessagesPlugin;
import drlibs.messages.BaseCommandsMessagesIDs;

public class ReloadCommand extends SubCommand {

	public ReloadCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command, String description,
			String permission) {
		super(plugin, fatherCommand, command, description, permission);
	}

	public ReloadCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String command) {
		this(plugin, fatherCommand, command, "Reloads the plugin's configurations", plugin.getID() + ".reload");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (parentOnCommand(sender, command, label, args)) {
			return true;
		}
		return runOnCommand(sender, command, label, args);
	}

	protected final boolean parentOnCommand(CommandSender sender, Command command, String label, String[] args) {
		return super.onCommand(sender, command, label, args);
	}

	protected final boolean runOnCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			getPlugin().getMessagesSender().sendTranslatedMessage(BaseCommandsMessagesIDs.PLAYER_COMMAND_MESSAGE_ID,
					sender);
			return false;
		}
		getPlugin().getReloaderManager().reloadAllSet();
		getPlugin().getPluginLogger().logTranslated(Level.INFO,
				BaseCommandsMessagesIDs.RELOAD_CONFIGURATIONS_LOG_MESSAGE_ID);
		getPlugin().getMessagesSender()
				.sendTranslatedMessage(BaseCommandsMessagesIDs.SUCCESSFULLY_RELOADED_CONFIGURATIONS_MESSAGE_ID, sender);
		return true;
	}

}
