//package drlibs.services.events.callers.inventory.moveitemtootherinventory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Material;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.inventory.InventoryAction;
//import org.bukkit.event.inventory.InventoryClickEvent;
//import org.bukkit.event.inventory.InventoryType;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.InventoryView;
//import org.bukkit.inventory.ItemStack;
//
//import drlibs.events.inventory.moveitemtootherinventory.MoveItemToOtherInventoryEvent;
//import drlibs.events.inventory.moveitemtootherinventory.SlotsAmounts;
//import drlibs.services.Service;
//import drlibs.services.events.callers.EventCallerService;
//import drlibs.utils.functions.InventoriesUtils;
//import drlibs.utils.functions.ItemsUtils;
//
//public class MoveItemToOtherInventoryEventCallerBackup extends EventCallerService implements Service, Listener {
//
//	public static MoveItemToOtherInventoryEventCallerBackup instance;
//
//	private MoveItemToOtherInventoryEventCallerBackup() {
//	}
//
//	public static MoveItemToOtherInventoryEventCallerBackup getInstance() {
//		if (instance == null) {
//			instance = new MoveItemToOtherInventoryEventCallerBackup();
//		}
//		return instance;
//	}
//
//	@EventHandler
//	public void onInventoryClickEvent(InventoryClickEvent event) {
//		System.out.println("Raw slot clicked: " + event.getRawSlot() + ", Slot clicked: " + event.getSlot());
//		if (event.isCancelled() || !event.isShiftClick()
//				|| event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY) {
//			return;
//		}
//		Inventory clickedInventory = event.getClickedInventory();
//		Inventory toInventory = null;
//		Player player = (Player) event.getWhoClicked();
//		if (clickedInventory.getType() == InventoryType.PLAYER) { // Player clicked on a slot inside his inventory
//			InventoryView openInventory = player.getOpenInventory();
//			if (openInventory.getType() == InventoryType.CRAFTING) { // Player doesn't have another inventory open
//																		// except his own
//				toInventory = clickedInventory;
//			} else { // Player has another inventory open except his own for example a chest
//				toInventory = openInventory.getTopInventory();
//			}
//		} else { // Player clicked on a slot in other inventory that is open for him like a chest
//			toInventory = player.getInventory();
//		}
//		Bukkit.getPluginManager()
//				.callEvent(new MoveItemToOtherInventoryEvent(event,
//						getSlotsAmountsAfter(event.getSlot(), event.getCurrentItem(), clickedInventory, toInventory),
//						clickedInventory, toInventory));
//	}
//
//	private SlotsAmounts getSlotsAmountsAfter(int fromSlot, ItemStack item, Inventory fromInventory,
//			Inventory toInventory) {
//		switch (fromInventory.getType()) {
//		case PLAYER:
//			switch (toInventory.getType()) {
//			case PLAYER:
//				return getSlotsAmountsAfterInnerCraftingInventoryMove(fromSlot, item, fromInventory, toInventory);
//			default:
//				return getSlotsAmountsAfterFromPlayerInventoryToOther(fromSlot, item, fromInventory, toInventory);
//			}
//		case CRAFTING:
//		case WORKBENCH:
//			return getSlotsAmountsAfterFromCraftingInventory(fromSlot, item, fromInventory,
//					toInventory);
//		default:
//			return getSlotsAmountsAfterToPlayerInventory(fromSlot, item, fromInventory, toInventory);
//		}
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterInnerCraftingInventoryMove(int fromSlot, ItemStack item,
//			Inventory fromInventory, Inventory toInventory) {
//		if (ItemsUtils.isArmor(item)) {
//			SlotsAmounts slotsAmounts = getSlotsAmountsAfterInnerCraftingInventoryMovePreprocessArmor(fromSlot, item,
//					toInventory);
//			if (slotsAmounts != null) {
//				return slotsAmounts;
//			}
//		}
//		System.out.println("Yay in inner move function!");
//		System.out.println("From Slot: " + fromSlot);
//		if (fromSlot < 9) {
//			return getSlotsAmountsAfterInnerCraftingInventoryMoveFromQuickbar(fromSlot, item, fromInventory,
//					toInventory);
//		} else if (fromSlot < 36) {
//			return getSlotsAmountsAfterInnerCraftingInventoryMoveFromRestOfInventory(fromSlot, item, fromInventory,
//					toInventory);
//		} else {
//			return getSlotsAmountsAfterInnerCraftingInventoryMoveFromArmorOrOffhandSlots(fromSlot, item, fromInventory,
//					toInventory);
//		}
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterInnerCraftingInventoryMovePreprocessArmor(int fromSlot, ItemStack item,
//			Inventory playerInventory) {
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] playerInventoryContents = playerInventory.getContents();
//		int armorSlot = -1;
//		switch (ItemsUtils.getArmorType(item)) {
//		case HELMET:
//			armorSlot = 39;
//			break;
//		case CHESTPLATE:
//			armorSlot = 38;
//			break;
//		case LEGGINGS:
//			armorSlot = 37;
//			break;
//		default:
//			armorSlot = 36;
//		}
//		ItemStack armorSlotItemStack = playerInventoryContents[armorSlot];
//		if (armorSlotItemStack == null || armorSlotItemStack.getType() == Material.AIR) {
//			int armorSlotNewAmount = Math.min(amount, item.getMaxStackSize());
//			toSlotsAmounts.put(armorSlot, armorSlotNewAmount);
//			amount -= armorSlotNewAmount;
//		} else if (item.isSimilar(armorSlotItemStack)) {
//			amount -= processSlotSimilar(amount, item, armorSlot, armorSlotItemStack, playerInventory, toSlotsAmounts);
//		}
//		if (toSlotsAmounts.isEmpty()) {
//			return null;
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterInnerCraftingInventoryMoveFromQuickbar(int fromSlot, ItemStack item,
//			Inventory fromInventory, Inventory toInventory) {
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] toInventoryContents = toInventory.getContents();
//		System.out.println("Yay!!!! inner from quickbar function!");
//		for (int slot = 9; slot < 36; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 9; slot < 36; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterInnerCraftingInventoryMoveFromRestOfInventory(int fromSlot, ItemStack item,
//			Inventory fromInventory, Inventory toInventory) {
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] toInventoryContents = toInventory.getContents();
//		for (int slot = 0; slot < 9; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 0; slot < 9; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterInnerCraftingInventoryMoveFromArmorOrOffhandSlots(int fromSlot,
//			ItemStack item, Inventory fromInventory, Inventory toInventory) {
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] toInventoryContents = toInventory.getContents();
//		for (int slot = 9; slot < 36; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 0; slot < 9; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 9; slot < 36; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 0; slot < 9; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterToPlayerInventory(int fromSlot, ItemStack item, Inventory fromInventory,
//			Inventory toInventory) {
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] toInventoryContents = toInventory.getContents();
//		for (int slot = 8; slot >= 0; slot--) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 35; slot >= 9; slot--) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 8; slot >= 0; slot--) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 35; slot >= 9; slot--) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterFromCraftingInventory(int fromSlot, ItemStack item,
//			Inventory fromInventory, Inventory toInventory) {
//		if (fromSlot == 0) {
//			return getSlotsAmountsAfterFromCrafting(fromSlot, item, fromInventory, toInventory);
//		}
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] toInventoryContents = toInventory.getContents();
//		for (int slot = 9; slot < 36; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 0; slot < 9; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 9; slot < 36; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 0; slot < 9; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//	
//	private SlotsAmounts getSlotsAmountsAfterFromCrafting(int fromSlot, ItemStack item, Inventory fromInventory, Inventory toInventory) {
//		Integer craftingTimes = getCraftingTimes(fromInventory);
//		if (craftingTimes == null) {
//			return null;
//		}
//		int maxNumOfCraftedItems = craftingTimes * item.getAmount();
//		ItemStack finalItem = new ItemStack(item);
//		finalItem.setAmount(maxNumOfCraftedItems);
//		SlotsAmounts slotsAmounts = getSlotsAmountsAfterToPlayerInventory(fromSlot, finalItem, fromInventory, toInventory);
//		int numOfLeftItems = slotsAmounts.getFromSlotAmount();
//		Map<Integer, Integer> toSlotsAmounts = slotsAmounts.getToSlotsAmounts();
//		if (numOfLeftItems > 0) {
//			return new SlotsAmounts(item.getAmount(), toSlotsAmounts);
//		}
//		return slotsAmounts;
//	}
//	
//	private Integer getCraftingTimes(Inventory craftingInventory) {
//		Integer craftingTimes = null;
//		for (int craftingSlot = 1; craftingSlot < craftingInventory.getSize(); craftingSlot++) {
//			ItemStack currentItemStack = craftingInventory.getItem(craftingSlot);
//			if (currentItemStack != null) {
//				if (craftingTimes == null) {
//					craftingTimes = currentItemStack.getAmount();
//				} else {
//					craftingTimes = Math.min(craftingTimes, currentItemStack.getAmount());
//				}
//			}
//		}
//		return craftingTimes;
//	}
//
//	private SlotsAmounts getSlotsAmountsAfterFromPlayerInventoryToOther(int fromSlot, ItemStack item,
//			Inventory fromInventory, Inventory toInventory) {
//		int amount = item.getAmount();
//		Map<Integer, Integer> toSlotsAmounts = new HashMap<>();
//		ItemStack[] toInventoryContents = toInventory.getContents();
//		for (int slot = 0; slot < toInventoryContents.length; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotSimilar(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		for (int slot = 0; slot < toInventoryContents.length; slot++) {
//			if (amount == 0) {
//				break;
//			}
//			ItemStack slotItem = toInventoryContents[slot];
//			amount -= processSlotEmpty(amount, item, slot, slotItem, toInventory, toSlotsAmounts);
//		}
//		return new SlotsAmounts(amount, toSlotsAmounts);
//	}
//
//	private int processSlotSimilar(int amount, ItemStack item, int slot, ItemStack slotItem, Inventory toInventory,
//			Map<Integer, Integer> toSlotsAmounts) {
//		if (item.isSimilar(slotItem)) {
//			int amountToAdd = Math.min(amount, slotItem.getMaxStackSize() - slotItem.getAmount());
//			if (amountToAdd > 0) {
//				toSlotsAmounts.put(slot, slotItem.getAmount() + amountToAdd);
//				return amountToAdd;
//			}
//		}
//		return 0;
//	}
//
//	private int processSlotEmpty(int amount, ItemStack item, int slot, ItemStack slotItem, Inventory toInventory,
//			Map<Integer, Integer> toSlotsAmounts) {
//		if (slotItem == null || slotItem.getType() == Material.AIR) {
//			int newSlotAmount = Math.min(amount, item.getMaxStackSize());
//			switch (InventoriesUtils.getSlotType(toInventory, slot)) {
//			case CONTAINER:
//			case CRAFTING:
//			case QUICKBAR:
//				toSlotsAmounts.put(slot, newSlotAmount);
//				return newSlotAmount;
//			case ARMOR:
//				if (ItemsUtils.isArmor(item)) {
//					toSlotsAmounts.put(slot, newSlotAmount);
//					return newSlotAmount;
//				}
//				break;
//			case FUEL:
//				if (item.getType().isFuel()) {
//					toSlotsAmounts.put(slot, newSlotAmount);
//					return newSlotAmount;
//				}
//				break;
//			default: // Outside isn't possible and result you can't put items there
//				break;
//			}
//		}
//		return 0;
//	}
//
//}
