package drlibs.services.events.callers.inventory;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import drlibs.events.inventory.DragInventoryDragAndDropInventoryEvent;
import drlibs.events.inventory.NormalDragAndDropInventoryEvent;
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
	public void onInventoryClickEvent(InventoryClickEvent event) {
		printEvent(event);
		System.out.println("Is player dragging item: " + isPlayerDraggingItem((Player) event.getWhoClicked()));
		if (event.isCancelled() || event.isShiftClick() || event.getClick() == ClickType.CREATIVE) { // Don't run if
																										// event is
																										// cancelled or
																										// shift clicked
																										// or clicked in
																										// creative mode
			return;
		}
		Player player = (Player) event.getWhoClicked();
		if (isPlayerDraggingItem(player)) { // Player dragging item
			if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) { // TODO Add handling to
																							// place one and keeping
																							// items in
																							// cursor to not remove the start drag player event for
																							// place some too
				removeStartDragPlayerEvent(player);
				return;
			}
			InventoryClickEvent startDragPlayerEvent;
			switch (event.getAction()) {
			case SWAP_WITH_CURSOR:
				startDragPlayerEvent = removeStartDragPlayerEvent(player);
				Bukkit.getPluginManager().callEvent(new NormalDragAndDropInventoryEvent(startDragPlayerEvent, event));
				setStartDragPlayerEvent(player, event);
				return;
			case PLACE_ALL:
			case PLACE_SOME: // See if there is a place like this and if needed move to act like the
								// PLACE_ONE action case
			case DROP_ALL_CURSOR:
			case DROP_ALL_SLOT:
				startDragPlayerEvent = removeStartDragPlayerEvent(player);
				Bukkit.getPluginManager().callEvent(new NormalDragAndDropInventoryEvent(startDragPlayerEvent, event));
				return;
			case PLACE_ONE:
			case DROP_ONE_CURSOR:
			case DROP_ONE_SLOT:
				if (event.getCursor().getAmount() == 1) {
					startDragPlayerEvent = removeStartDragPlayerEvent(player);
				} else {
					startDragPlayerEvent = getStartDragPlayerEvent(player);
				}
				Bukkit.getPluginManager().callEvent(new NormalDragAndDropInventoryEvent(startDragPlayerEvent, event));
				return;
			default:
				return;
			}
		} else {
			if (!isEventActionPickup(event)) {
				return;
			}
//			System.out.println("Player started dragging item!" + event.getCurrentItem().getType());
			setStartDragPlayerEvent(player, event);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDragEvent(InventoryDragEvent event) {
		printEvent(event);
		if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) {
			Bukkit.getPluginManager().callEvent(new DragInventoryDragAndDropInventoryEvent(
					removeStartDragPlayerEvent((Player) event.getWhoClicked()), event));
			return;
		}
		Bukkit.getPluginManager().callEvent(new DragInventoryDragAndDropInventoryEvent(
				getStartDragPlayerEvent((Player) event.getWhoClicked()), event));
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

	// DEBUG
	@SuppressWarnings("unused")
	private void printEvent(InventoryDragEvent event) {
		System.out.println("=================================================");
		System.out.println("Event is cancelled: " + event.isCancelled());
		System.out.println("Event new items: " + event.getNewItems());
		System.out.println("Old cursor: " + event.getOldCursor());
		System.out.println("Cursor Item: " + event.getCursor());
		System.out.println("Result: " + event.getResult());
		System.out.println("Drag Type: " + event.getType());
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
		return startDragPlayerEvents.containsKey(player) && player.getItemOnCursor() != null && player.getItemOnCursor().getType() != Material.AIR;
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

	private InventoryClickEvent getStartDragPlayerEvent(Player player) {
		return startDragPlayerEvents.get(player);
	}

}
