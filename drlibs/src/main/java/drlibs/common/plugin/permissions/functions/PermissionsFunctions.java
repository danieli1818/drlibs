package drlibs.common.plugin.permissions.functions;

import org.bukkit.entity.Player;

public interface PermissionsFunctions {

	public boolean hasPermission(Player player, String permission);
	
	public boolean givePermission(Player player, String permission);
	
}
