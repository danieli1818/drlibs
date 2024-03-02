package drlibs.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import drlibs.common.general.interfaces.PropertyChangeObservable;

public interface DragAndDropInventoryEvent extends Cancellable, PropertyChangeObservable {

	public InventoryClickEvent getStartDragEvent();
	public InventoryInteractEvent getDropEvent();
	public Player getPlayer();
	
}
