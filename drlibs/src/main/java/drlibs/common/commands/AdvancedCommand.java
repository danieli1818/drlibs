package drlibs.common.commands;

import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public interface AdvancedCommand extends CommandExecutor, TabCompleter {

	/**
	 * Returns the command's description
	 * 
	 * @return The command's description
	 */
	public String getDescription();

	/**
	 * Returns the sub commands map which maps between each sub command's name to
	 * the sub command
	 * 
	 * @return The sub commands map
	 */
	public Map<String, AdvancedCommand> getSubCommands();

	/**
	 * Returns the invalid command message ID from the messages storage file, this
	 * message will be sent when the command isn't valid
	 * 
	 * @return The invalid command message ID
	 */
	public String getInvalidCommandMessageID();

	/**
	 * Returns the no permission message ID from the messages storage file, this
	 * message will be sent when the issuer of the command doesn't have permission
	 * to use it
	 * 
	 * @return The no permission message ID
	 */
	public String getNoPermissionMessageID();

	/**
	 * Returns the player command message ID from the messages storage file, this
	 * message will be sent when the issuer of the command isn't a player (the
	 * console) and the message needs a player to run it
	 * 
	 * @return The player command message ID
	 */
	public String getPlayerCommandMessageID();

	/**
	 * Returns the permission needed in order to run this command
	 * 
	 * @return The permission needed to use this command
	 */
	public String getPermission();
	
	/**
	 * Returns the command prefix.
	 * @return The command prefix
	 */
	public String getCommandPrefix();

}
