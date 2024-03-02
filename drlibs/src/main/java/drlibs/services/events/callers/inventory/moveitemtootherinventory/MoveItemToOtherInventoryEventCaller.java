package drlibs.services.events.callers.inventory.moveitemtootherinventory;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
import drlibs.services.events.callers.EventCallerService;

public class MoveItemToOtherInventoryEventCaller extends EventCallerService {

	private static MoveItemToOtherInventoryEventCaller instance;

	private MoveItemToOtherInventoryEventCaller() {
	}

	public static MoveItemToOtherInventoryEventCaller getInstance() {
		if (instance == null) {
			instance = new MoveItemToOtherInventoryEventCaller();
		}
		return instance;
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if (event.isCancelled() || !event.isShiftClick()
				|| event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) {
			return;
		}
		Inventory clickedInventory = event.getClickedInventory();
		Inventory toInventory = Logic.getToInventory(event);
		Bukkit.getPluginManager()
				.callEvent(new MoveItemToOtherInventoryEvent(event,
						Logic.processSlotsAmounts(event.getSlot(), event.getCurrentItem(), clickedInventory, toInventory),
						clickedInventory, toInventory));
	}
	
}
