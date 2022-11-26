package drlibs.common.plugin.permissions.functions;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import drlibs.common.plugin.exceptions.PluginMissingException;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class LuckPermsFunctions implements PermissionsFunctions {

	private LuckPerms luckPermsAPI;
	
	public LuckPermsFunctions() {
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider == null) {
			throw new PluginMissingException("LuckPerms");
		}
		luckPermsAPI = provider.getProvider();
	}
	
	private void modifyUser(Player player, Consumer<User> action) {
		luckPermsAPI.getUserManager().modifyUser(player.getUniqueId(), action);
	}

	@Override
	public boolean hasPermission(Player player, String permission) {
		return player.hasPermission(permission);
	}

	@Override
	public boolean givePermission(Player player, String permission) {
		if (hasPermission(player, permission)) {
			return false;
		}
		modifyUser(player, (User user) -> user.data().add(Node.builder(permission).build()));
		return true;
	}
	
}
