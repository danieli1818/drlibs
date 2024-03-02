package drlibs.services.events.callers.inventory.moveitemtootherinventory;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drlibs.events.inventory.moveitemtootherinventory.SlotsAmounts;

public class CraftingMoveLogic {

	public static SlotsAmounts processCrafting(int fromSlot, ItemStack item, Inventory fromInventory, Inventory toInventory) {
		Integer maxCraftingTimes = getMaxCraftingTimes(fromInventory);
		if (maxCraftingTimes == null) {
			return null;
		}
		int maxNumOfCraftedItems = maxCraftingTimes * item.getAmount();
		ItemStack finalItem = new ItemStack(item);
		finalItem.setAmount(maxNumOfCraftedItems);
		SlotsAmounts slotsAmounts = Logic.processInwardsMove(fromSlot, finalItem, fromInventory, toInventory);
		Map<Integer, Integer> fromSlotsInventoryAmounts = slotsAmounts.getFromInventorySlotsAmounts();
		int numOfLeftItems = fromSlotsInventoryAmounts.values().iterator().next(); // There is only 1 slot in the fromSlotsAmounts result
		Map<Integer, Integer> toInventorySlotsAmounts = slotsAmounts.getToInventorySlotsAmounts();
		if (numOfLeftItems > 0) {
			int droppedItems = numOfLeftItems % item.getAmount();
			if (droppedItems > 0) {
				fromSlotsInventoryAmounts.put(-999, droppedItems); // Dropped items go to -999 which is outside the GUI
				numOfLeftItems -= droppedItems;
			}
			int craftingTimes = (maxNumOfCraftedItems - numOfLeftItems) / item.getAmount();
			for (int slot = 1; slot < fromInventory.getSize(); slot++) {
				ItemStack slotItemStack = fromInventory.getItem(slot);
				if (slotItemStack != null && slotItemStack.getType() != Material.AIR) {
					fromSlotsInventoryAmounts.put(slot, slotItemStack.getAmount() - craftingTimes);
				}
			}
			fromSlotsInventoryAmounts.put(fromSlot, item.getAmount());
		} else {
			for (int slot = 1; slot < fromInventory.getSize(); slot++) {
				ItemStack slotItemStack = fromInventory.getItem(slot);
				if (slotItemStack != null && slotItemStack.getType() != Material.AIR) {
					fromSlotsInventoryAmounts.put(slot, slotItemStack.getAmount() - maxCraftingTimes);
				}
			}
		}
		return new SlotsAmounts(fromSlotsInventoryAmounts, toInventorySlotsAmounts);
	}
	
	/**
	 * Calculates and returns the max crafting times for the current recipe with the current amounts of ingredients
	 * if there is enough space in the player's inventory.
	 * @param craftingInventory The crafting inventory of the player
	 * @return The max crafting times for the current recipe with the current amounts of ingredients
	 * if there is enough space in the player's inventory.
	 */
	public static Integer getMaxCraftingTimes(Inventory craftingInventory) {
		Integer craftingTimes = null;
		for (int craftingSlot = 1; craftingSlot < craftingInventory.getSize(); craftingSlot++) {
			ItemStack currentItemStack = craftingInventory.getItem(craftingSlot);
			if (currentItemStack != null) {
				if (craftingTimes == null) {
					craftingTimes = currentItemStack.getAmount();
				} else {
					craftingTimes = Math.min(craftingTimes, currentItemStack.getAmount());
				}
			}
		}
		return craftingTimes;
	}
	
}
