package drlibs.services.events.callers.inventory.moveitemtootherinventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drlibs.events.inventory.moveitemtootherinventory.SlotsAmounts;
import drlibs.utils.functions.ItemsUtils;

public class InnerMoveLogic {

	public static SlotsAmounts preprocessArmor(int fromSlot, ItemStack item,
			Inventory playerInventory) {
		int amount = item.getAmount();
		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
		ItemStack[] playerInventoryContents = playerInventory.getContents();
		int armorSlot = -1;
		switch (ItemsUtils.getArmorType(item)) {
		case HELMET:
			armorSlot = 39;
			break;
		case CHESTPLATE:
			armorSlot = 38;
			break;
		case LEGGINGS:
			armorSlot = 37;
			break;
		default:
			armorSlot = 36;
		}
		ItemStack armorSlotItemStack = playerInventoryContents[armorSlot];
		if (armorSlotItemStack == null || armorSlotItemStack.getType() == Material.AIR) {
			int armorSlotNewAmount = Math.min(amount, item.getMaxStackSize());
			toSlotsAmounts.put(armorSlot, armorSlotNewAmount);
			amount -= armorSlotNewAmount;
		} else if (item.isSimilar(armorSlotItemStack)) {
			amount -= SlotsProcessor.processSlotSimilar(amount, item, armorSlot, armorSlotItemStack, toSlotsAmounts);
		}
		if (toSlotsAmounts.isEmpty()) {
			return null;
		}
		return new SlotsAmounts(fromSlot, amount, toSlotsAmounts);
	}

	public static SlotsAmounts processMoveFromQuickbar(int fromSlot, ItemStack item,
			Inventory playerInventory) {
		System.out.println("Yay!!!! inner from quickbar function!");
		Map<Integer, Integer> ranges = new LinkedHashMap<>();
		ranges.put(9, 36);
		Entry<Integer, Map<Integer, Integer>> result = SlotsProcessor.processSlotsAscending(item, playerInventory, ranges);
		return new SlotsAmounts(fromSlot, result.getKey(), result.getValue());
	}

	public static SlotsAmounts processMoveFromRestOfInventory(int fromSlot, ItemStack item,
			Inventory playerInventory) {
		Map<Integer, Integer> ranges = new LinkedHashMap<>();
		ranges.put(0, 9);
		Entry<Integer, Map<Integer, Integer>> result = SlotsProcessor.processSlotsAscending(item, playerInventory, ranges);
		return new SlotsAmounts(fromSlot, result.getKey(), result.getValue());
	}

	public static SlotsAmounts processMoveFromArmorOrOffhandSlots(int fromSlot,
			ItemStack item, Inventory playerInventory) {
		Map<Integer, Integer> ranges = new LinkedHashMap<>();
		ranges.put(9, 36);
		ranges.put(0, 9);
		Entry<Integer, Map<Integer, Integer>> result = SlotsProcessor.processSlotsAscending(item, playerInventory, ranges);
		return new SlotsAmounts(fromSlot, result.getKey(), result.getValue());
	}
	
}
