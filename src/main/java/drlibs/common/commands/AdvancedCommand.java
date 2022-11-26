package drlibs.common.commands;

import java.util.Map;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

public interface AdvancedCommand extends CommandExecutor, TabCompleter {

	public String getDescription();
	
	public Map<String, AdvancedCommand> getSubCommands();
	
	public String getInvalidCommandMessageID();
	
	public String getNoPermissionMessageID();
	
	public String getPlayerCommandMessageID();
	
	public String getPermission();
	
}
