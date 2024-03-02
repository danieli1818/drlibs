package drlibs.services.events.callers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import drlibs.services.DRLibsPluginService;
import drlibs.services.Service;

public abstract class EventCallerService extends DRLibsPluginService implements Service, Listener {

	@Override
	public boolean registerService() {
		if (isRegistered()) {
			return true;
		}
		Bukkit.getPluginManager().registerEvents(this, getPlugin());
		setIsRegistered(true);
		return true;
	}
	
}
