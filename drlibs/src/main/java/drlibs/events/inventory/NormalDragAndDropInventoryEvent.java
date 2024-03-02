package drlibs.events.inventory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

import drlibs.common.general.interfaces.PropertyChangeObservable;

// TODO Either add support to uncancel after canceling or to change the API to only allow to cancel without enabling uncanceling
public class NormalDragAndDropInventoryEvent extends Event implements Cancellable, PropertyChangeObservable, DragAndDropInventoryEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	private InventoryClickEvent startDragEvent;
	private InventoryClickEvent dropEvent;

	private boolean isCancelled;
	
	private PropertyChangeSupport propertyChangeSupport;

	public NormalDragAndDropInventoryEvent(InventoryClickEvent startDragEvent, InventoryClickEvent dropEvent) {
		this.startDragEvent = startDragEvent;
		this.dropEvent = dropEvent;
		this.isCancelled = false;
		this.propertyChangeSupport = new PropertyChangeSupport(this);
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

	public InventoryClickEvent getDropEvent() {
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
		if (this.isCancelled == cancel) {
			return;
		}
		this.isCancelled = cancel;
		dropEvent.setCancelled(cancel);
		propertyChangeSupport.firePropertyChange("cancel", !this.isCancelled, this.isCancelled);
	}

	@Override
	public void addObserver(PropertyChangeListener observer) {
		propertyChangeSupport.addPropertyChangeListener(observer);
	}

	@Override
	public void removeObserver(PropertyChangeListener observer) {
		propertyChangeSupport.removePropertyChangeListener(observer);
	}

}
