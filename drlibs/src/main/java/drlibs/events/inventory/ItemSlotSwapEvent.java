package drlibs.events.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSlotSwapEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private InventoryClickEvent swapClickEvent;
	private ItemStack hotbarItemStack;

	public ItemSlotSwapEvent(InventoryClickEvent swapClickEvent) {
		if (swapClickEvent == null || swapClickEvent.getHotbarButton() == -1) {
			throw new IllegalArgumentException("swapClickEvent must be a hotbar number inventory click event!");
		}
		this.swapClickEvent = swapClickEvent;
		Player player = (Player) swapClickEvent.getWhoClicked();
		hotbarItemStack = player.getInventory().getItem(swapClickEvent.getHotbarButton());
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public InventoryClickEvent getSwapClickEvent() {
		return swapClickEvent;
	}

	public int getClickedSlot() {
		return swapClickEvent.getSlot();
	}

	public int getHotbarSlot() {
		return swapClickEvent.getHotbarButton();
	}

	public ItemStack getClickedSlotItemStack() {
		return swapClickEvent.getCurrentItem();
	}

	public ItemStack getHotbarSlotItemStack() {
		return hotbarItemStack;
	}

	@Override
	public String toString() {
		return "Clicked slot and item: " + getClickedSlot() + ", " + getClickedSlotItemStack()
				+ "\nHotbar slot and item: " + getHotbarSlot() + ", " + getHotbarSlotItemStack();
	}

	@Override
	public boolean isCancelled() {
		return swapClickEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		swapClickEvent.setCancelled(cancel);
	}

}
