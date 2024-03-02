package drlibs.services.events.callers.inventory.moveitemtootherinventory;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drlibs.utils.functions.InventoriesUtils;
import drlibs.utils.functions.ItemsUtils;

public class SlotsProcessor {

	public static Map.Entry<Integer, Map<Integer, Integer>> processSlotsAscending(ItemStack item, Inventory toInventory,
			Map<Integer, Integer> ranges) {
		int amount = item.getAmount();
		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
		for (Map.Entry<Integer, Integer> range : ranges.entrySet()) {
			if (amount == 0) {
				break;
			}
			for (int slot = range.getKey(); slot < range.getValue(); slot++) {
				if (amount == 0) {
					break;
				}
				amount -= processSlotSimilar(amount, item, slot, toInventory.getItem(slot), toSlotsAmounts);
			}
		}
		for (Map.Entry<Integer, Integer> range : ranges.entrySet()) {
			if (amount == 0) {
				break;
			}
			for (int slot = range.getKey(); slot < range.getValue(); slot++) {
				if (amount == 0) {
					break;
				}
				amount -= processSlotEmpty(amount, item, slot, toInventory.getItem(slot), toInventory, toSlotsAmounts);
			}
		}
		return new AbstractMap.SimpleEntry<Integer, Map<Integer, Integer>>(amount, toSlotsAmounts);
	}
	
	public static Map.Entry<Integer, Map<Integer, Integer>> processSlotsDescending(ItemStack item, Inventory toInventory,
			Map<Integer, Integer> ranges) {
		int amount = item.getAmount();
		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
		for (Map.Entry<Integer, Integer> range : ranges.entrySet()) {
			if (amount == 0) {
				break;
			}
			for (int slot = range.getKey(); slot > range.getValue(); slot--) {
				if (amount == 0) {
					break;
				}
				amount -= processSlotSimilar(amount, item, slot, toInventory.getItem(slot), toSlotsAmounts);
			}
		}
		for (Map.Entry<Integer, Integer> range : ranges.entrySet()) {
			if (amount == 0) {
				break;
			}
			for (int slot = range.getKey(); slot > range.getValue(); slot--) {
				if (amount == 0) {
					break;
				}
				amount -= processSlotEmpty(amount, item, slot, toInventory.getItem(slot), toInventory, toSlotsAmounts);
			}
		}
		return new AbstractMap.SimpleEntry<Integer, Map<Integer, Integer>>(amount, toSlotsAmounts);
	}

	public static int processSlotSimilar(int amount, ItemStack item, int slot, ItemStack slotItem,
			Map<Integer, Integer> toSlotsAmounts) {
		if (item.isSimilar(slotItem)) {
			int amountToAdd = Math.min(amount, slotItem.getMaxStackSize() - slotItem.getAmount());
			if (amountToAdd > 0) {
				toSlotsAmounts.put(slot, slotItem.getAmount() + amountToAdd);
				return amountToAdd;
			}
		}
		return 0;
	}

	public static int processSlotEmpty(int amount, ItemStack item, int slot, ItemStack slotItem, Inventory toInventory,
			Map<Integer, Integer> toSlotsAmounts) {
		if (slotItem == null || slotItem.getType() == Material.AIR) {
			int newSlotAmount = Math.min(amount, item.getMaxStackSize());
			switch (InventoriesUtils.getSlotType(toInventory, slot)) {
			case CONTAINER:
			case CRAFTING:
			case QUICKBAR:
				toSlotsAmounts.put(slot, newSlotAmount);
				return newSlotAmount;
			case ARMOR:
				if (ItemsUtils.isArmor(item)) {
					toSlotsAmounts.put(slot, newSlotAmount);
					return newSlotAmount;
				}
				break;
			case FUEL:
				if (item.getType().isFuel()) {
					toSlotsAmounts.put(slot, newSlotAmount);
					return newSlotAmount;
				}
				break;
			default: // Outside isn't possible and result you can't put items there
				break;
			}
		}
		return 0;
	}

}
