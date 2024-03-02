package drlibs.services;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import drlibs.DRLibs;

public abstract class DRLibsPluginService extends BaseService implements Service {

	public Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin(DRLibs.NAME);
	}
	
}
