package drlibs.services.events.callers.inventory.moveitemtootherinventory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import drlibs.events.inventory.moveitemtootherinventory.SlotsAmounts;
import drlibs.utils.functions.ItemsUtils;

public class Logic {
	
	public static Inventory getToInventory(InventoryClickEvent event) {
		Inventory clickedInventory = event.getClickedInventory();
		Inventory toInventory = null;
		Player player = (Player) event.getWhoClicked();
		if (clickedInventory.getType() == InventoryType.PLAYER) { // Player clicked on a slot inside his inventory
			InventoryView openInventory = player.getOpenInventory();
			if (openInventory.getType() == InventoryType.CRAFTING) { // Player crafting inventory is open
				toInventory = clickedInventory;
			} else { // Player has another inventory open except his own for example a chest
				toInventory = openInventory.getTopInventory();
			}
		} else { // Player clicked on a slot in other inventory that is open for him like a chest
			toInventory = player.getInventory();
		}
		return toInventory;
	}
	
	public static SlotsAmounts processSlotsAmounts(int fromSlot, ItemStack item, Inventory fromInventory,
			Inventory toInventory) {
		switch (fromInventory.getType()) {
		case PLAYER:
			switch (toInventory.getType()) {
			case PLAYER:
				return processInnerMove(fromSlot, item, fromInventory);
			default:
				return processOutwardsMove(fromSlot, item, fromInventory, toInventory);
			}
		case CRAFTING:
		case WORKBENCH:
			return processCraftingMove(fromSlot, item, fromInventory,
					toInventory);
		default:
			return processInwardsMove(fromSlot, item, fromInventory, toInventory);
		}
	}
	
	public static SlotsAmounts processInnerMove(int fromSlot, ItemStack item, Inventory playerInventory) {
		if (ItemsUtils.isArmor(item)) {
			SlotsAmounts slotsAmounts = InnerMoveLogic.preprocessArmor(fromSlot, item,
					playerInventory);
			if (slotsAmounts != null) {
				return slotsAmounts;
			}
		}
		System.out.println("Yay in inner move function!");
		System.out.println("From Slot: " + fromSlot);
		if (fromSlot < 9) {
			return InnerMoveLogic.processMoveFromQuickbar(fromSlot, item,
					playerInventory);
		} else if (fromSlot < 36) {
			return InnerMoveLogic.processMoveFromRestOfInventory(fromSlot, item,
					playerInventory);
		} else {
			return InnerMoveLogic.processMoveFromArmorOrOffhandSlots(fromSlot, item,
					playerInventory);
		}
	}
	
	public static SlotsAmounts processOutwardsMove(int fromSlot, ItemStack item, Inventory fromInventory,
			Inventory toInventory) {
		Map<Integer, Integer> ranges = new LinkedHashMap<>();
		ranges.put(0, toInventory.getSize());
		Entry<Integer, Map<Integer, Integer>> result = SlotsProcessor.processSlotsAscending(item, toInventory, ranges);
		return new SlotsAmounts(fromSlot, result.getKey(), result.getValue());
	}
	
	public static SlotsAmounts processCraftingMove(int fromSlot, ItemStack item, Inventory fromInventory,
			Inventory toInventory) {
		if (fromSlot == 0) {
			return CraftingMoveLogic.processCrafting(fromSlot, item, fromInventory, toInventory);
		}
		Map<Integer, Integer> ranges = new LinkedHashMap<>();
		ranges.put(9, 36);
		ranges.put(0, 9);
		Entry<Integer, Map<Integer, Integer>> result = SlotsProcessor.processSlotsAscending(item, toInventory, ranges);
		return new SlotsAmounts(fromSlot, result.getKey(), result.getValue());
	}
	
	public static SlotsAmounts processInwardsMove(int fromSlot, ItemStack item, Inventory fromInventory,
			Inventory toInventory) {
		Map<Integer, Integer> ranges = new LinkedHashMap<>();
		ranges.put(8, -1);
		ranges.put(35, 8);
		Entry<Integer, Map<Integer, Integer>> result = SlotsProcessor.processSlotsDescending(item, toInventory, ranges);
		return new SlotsAmounts(fromSlot, result.getKey(), result.getValue());
	}

}
