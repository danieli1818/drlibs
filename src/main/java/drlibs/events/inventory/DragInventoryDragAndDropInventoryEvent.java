package drlibs.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

// TODO Add cancel action to return the item back to the previous slot and if there is an item there put it in other slot or drop it to the ground
public class DragInventoryDragAndDropInventoryEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private InventoryClickEvent startDragEvent;
	private InventoryDragEvent dropEvent;

	private boolean isCancelled;

	public DragInventoryDragAndDropInventoryEvent(InventoryClickEvent startDragEvent, InventoryDragEvent dropEvent) {
		this.startDragEvent = startDragEvent;
		this.dropEvent = dropEvent;
		this.isCancelled = false;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public InventoryClickEvent getStartDragEvent() {
		return startDragEvent;
	}

	public InventoryDragEvent getDropEvent() {
		return dropEvent;
	}

	public Player getPlayer() {
		return (Player) startDragEvent.getWhoClicked();
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}

}
