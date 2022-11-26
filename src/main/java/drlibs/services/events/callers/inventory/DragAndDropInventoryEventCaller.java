package drlibs.services.events.callers.inventory;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import drlibs.events.inventory.DragAndDropInventoryEvent;
import drlibs.services.Service;
import drlibs.services.events.callers.EventCallerService;

public class DragAndDropInventoryEventCaller extends EventCallerService implements Service, Listener {

	private static DragAndDropInventoryEventCaller instance = null;

	public static DragAndDropInventoryEventCaller getInstance() {
		if (instance == null) {
			instance = new DragAndDropInventoryEventCaller();
		}
		return instance;
	}

	private Map<Player, InventoryClickEvent> startDragPlayerEvents;

	public DragAndDropInventoryEventCaller() {
		startDragPlayerEvents = new HashMap<>();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClickEvent(InventoryClickEvent event) { // TODO Take care of the case of double click and swap hotbar by number clicking items
//		printEvent(event);
		if (event.isCancelled() || event.isShiftClick() || event.getClick() == ClickType.CREATIVE) { // Don't run if event is cancelled or shift clicked or clicked in creative mode
			return;
		}
		Player player = (Player) event.getWhoClicked();
		if (isPlayerDraggingItem(player)) { // Player dragging item
			if (isEventActionSwap(event)) {
				InventoryClickEvent startDragPlayerEvent = removeStartDragPlayerEvent(player);
				Bukkit.getPluginManager().callEvent(new DragAndDropInventoryEvent(startDragPlayerEvent, event));
				setStartDragPlayerEvent(player, event);
				return;
			}
			if (!isEventActionPlace(event)) {
				return;
			}
//			System.out.println("Player done dragging item!" + event.getCurrentItem().getType());
			InventoryClickEvent startDragPlayerEvent = removeStartDragPlayerEvent(player);
			Bukkit.getPluginManager().callEvent(new DragAndDropInventoryEvent(startDragPlayerEvent, event));
		} else {
			if (!isEventActionPickup(event)) {
				return;
			}
//			System.out.println("Player started dragging item!" + event.getCurrentItem().getType());
			setStartDragPlayerEvent(player, event);
		}
	}

	// DEBUG
	@SuppressWarnings("unused")
	private void printEvent(InventoryClickEvent event) {
		System.out.println("=================================================");
		System.out.println("Event is cancelled: " + event.isCancelled());
		System.out.println("Event is click: " + event.getClick());
		System.out.println("Current Item: " + event.getCurrentItem());
		System.out.println("Cursor Item: " + event.getCursor());
		System.out.println("Result: " + event.getResult());
		System.out.println("Action: " + event.getAction());
		System.out.println("=================================================");
	}

	private boolean isEventActionPickup(InventoryClickEvent event) {
		InventoryAction action = event.getAction();
		return action.equals(InventoryAction.PICKUP_ALL) || action.equals(InventoryAction.PICKUP_HALF)
				|| action.equals(InventoryAction.PICKUP_ONE) || action.equals(InventoryAction.PICKUP_SOME);
	}
	
	private boolean isEventActionPlace(InventoryClickEvent event) {
		InventoryAction action = event.getAction();
		return action.equals(InventoryAction.PLACE_ALL) || action.equals(InventoryAction.PLACE_ONE)
				|| action.equals(InventoryAction.PLACE_SOME);
	}
	
	private boolean isEventActionSwap(InventoryClickEvent event) {
		InventoryAction action = event.getAction();
		return action.equals(InventoryAction.SWAP_WITH_CURSOR);
	}

//	@EventHandler(priority = EventPriority.HIGHEST)
//	public void onInventoryCreativeEvent(InventoryCreativeEvent event) {
//		printEvent(event);
//	}

	private boolean isPlayerDraggingItem(Player player) {
//		System.out.println("Is Player Dragging Item: " + startDragPlayerEvents.containsKey(player));
		return startDragPlayerEvents.containsKey(player);
	}

//	private InventoryClickEvent getStartDragPlayerEvent(Player player) {
//		return startDragPlayerEvents.get(player);
//	}

	private void setStartDragPlayerEvent(Player player, InventoryClickEvent event) {
		startDragPlayerEvents.put(player, event);
	}

	private InventoryClickEvent removeStartDragPlayerEvent(Player player) {
		return startDragPlayerEvents.remove(player);
	}

}
