package drlibs.events.inventory.moveitemtootherinventory;

import java.util.Map;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MoveItemToOtherInventoryEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private InventoryClickEvent moveItemToOtherInventoryClickEvent;
	private SlotsAmounts slotsAmountsAfter;
	private Inventory fromInventory;
	private Inventory toInventory;

	public MoveItemToOtherInventoryEvent(InventoryClickEvent moveItemToOtherInventoryClickEvent,
			SlotsAmounts slotsAmountsAfter, Inventory fromInventory, Inventory toInventory) {
		this.moveItemToOtherInventoryClickEvent = moveItemToOtherInventoryClickEvent;
		this.slotsAmountsAfter = slotsAmountsAfter;
		this.fromInventory = fromInventory;
		this.toInventory = toInventory;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public InventoryClickEvent getSwapClickEvent() {
		return moveItemToOtherInventoryClickEvent;
	}

	public int getFromSlot() {
		return moveItemToOtherInventoryClickEvent.getSlot();
	}

	public int getFromSlotAmountBefore() {
		return getItemStack().getAmount();
	}

	public SlotsAmounts getSlotsAmountsAfter() {
		return slotsAmountsAfter;
	}

	public Map<Integer, Integer> getFromInventorySlotsAmountsAfter() {
		return getSlotsAmountsAfter().getFromInventorySlotsAmounts();
	}

	public Map<Integer, Integer> getToInventorySlotsAmounts() {
		return slotsAmountsAfter.getToInventorySlotsAmounts();
	}

	public ItemStack getItemStack() {
		return moveItemToOtherInventoryClickEvent.getCurrentItem();
	}

	public Inventory getFromInventory() {
		return fromInventory;
	}

	public Inventory getToInventory() {
		return toInventory;
	}

	@Override
	public String toString() {
		return "Clicked slot and item: " + getFromSlot() + ", " + getItemStack()
				+ "\nSlots amounts from:" + getFromInventorySlotsAmountsAfter() + ", to:" + getToInventorySlotsAmounts();
	}

	@Override
	public boolean isCancelled() {
		return moveItemToOtherInventoryClickEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		moveItemToOtherInventoryClickEvent.setCancelled(cancel);
	}

}
