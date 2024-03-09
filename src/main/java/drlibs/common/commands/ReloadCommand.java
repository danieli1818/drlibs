package drlibs.common.commands;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drlibs.common.plugin.PluginParameters;
import drlibs.messages.BaseCommandsMessagesIDs;
import drlibs.utils.reloader.ReloaderManager;

public class ReloadCommand extends SubCommand {
	
	private ReloaderManager reloaderManager;

	public ReloadCommand(PluginParameters pluginParameters, ReloaderManager reloaderManager, AdvancedCommand fatherCommand, String command, String description,
			String permission) {
		super(pluginParameters, fatherCommand, command, description, permission);
		this.reloaderManager = reloaderManager;
	}

	public ReloadCommand(PluginParameters pluginParameters, ReloaderManager reloaderManager, AdvancedCommand fatherCommand, String command) {
		this(pluginParameters, reloaderManager, fatherCommand, command, "Reloads the plugin's configurations", pluginParameters.getPluginID() + ".reload");
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
			getPluginParameters().getMessagesSender().sendTranslatedMessage(BaseCommandsMessagesIDs.PLAYER_COMMAND_MESSAGE_ID,
					sender);
			return false;
		}
		reloaderManager.reloadAllSet();
		getPluginParameters().getPluginLogger().logTranslated(Level.INFO,
				BaseCommandsMessagesIDs.RELOAD_CONFIGURATIONS_LOG_MESSAGE_ID);
		getPluginParameters().getMessagesSender()
				.sendTranslatedMessage(BaseCommandsMessagesIDs.SUCCESSFULLY_RELOADED_CONFIGURATIONS_MESSAGE_ID, sender);
		return true;
	}

}
