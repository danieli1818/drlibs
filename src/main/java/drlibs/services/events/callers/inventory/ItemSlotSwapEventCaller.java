package drlibs.services.events.callers.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import drlibs.events.inventory.ItemSlotSwapEvent;
import drlibs.services.Service;
import drlibs.services.events.callers.EventCallerService;

public class ItemSlotSwapEventCaller extends EventCallerService implements Service, Listener {

	private static ItemSlotSwapEventCaller instance = null;

	private ItemSlotSwapEventCaller() {}

	public static ItemSlotSwapEventCaller getInstance() {
		if (instance == null) {
			instance = new ItemSlotSwapEventCaller();
		}
		return instance;
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if (event.isCancelled()) { // Return on cancelled event
			return;
		}
		if (event.getAction() == InventoryAction.HOTBAR_SWAP) { // Number click hotbar swap event
			Bukkit.getPluginManager().callEvent(new ItemSlotSwapEvent(event));
		}
	}

}
