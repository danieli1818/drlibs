package drlibs.utils.functions;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class InventoriesUtils {

	/**
     * Determine the type of the slot by its raw slot ID.
     * <p>
     * If the type of the slot is unknown, then
     * {@link SlotType#CONTAINER} will be returned.
     *
     * @param inventory The inventory with the current slot
     * @param slot The raw slot ID
     * @return the slot type
     */
    public static final SlotType getSlotType(Inventory inventory, int slot) {
    	SlotType type = SlotType.CONTAINER;
        if (slot >= 0 && slot < inventory.getSize()) {
            switch(inventory.getType()) {
            case FURNACE:
                if (slot == 2) {
                    type = SlotType.RESULT;
                } else if(slot == 1) {
                    type = SlotType.FUEL;
                } else {
                    type = SlotType.CRAFTING;
                }
                break;
            case BREWING:
                if (slot == 3) {
                    type = SlotType.FUEL;
                } else {
                    type = SlotType.CRAFTING;
                }
                break;
            case ENCHANTING:
                type = SlotType.CRAFTING;
                break;
            case WORKBENCH:
            case CRAFTING:
                if (slot == 0) {
                    type = SlotType.RESULT;
                } else if (slot < 5) {
                	type = SlotType.CRAFTING;
                } else if (slot < 9) {
                    type = SlotType.ARMOR;
                } else if (slot > 35) {
                    type = SlotType.QUICKBAR;
                }
                break;
            case MERCHANT:
                if (slot == 2) {
                    type = SlotType.RESULT;
                } else {
                    type = SlotType.CRAFTING;
                }
                break;
            case BEACON:
                type = SlotType.CRAFTING;
                break;
            case ANVIL:
                if (slot == 2) {
                    type = SlotType.RESULT;
                } else {
                    type = SlotType.CRAFTING;
                }
                break;
            default:
                // Nothing to do, it's a CONTAINER slot
            }
        }
        return type;
    }
    
    /**
     * Determine the type of the slot by its raw slot ID.
     * <p>
     * If the type of the slot is unknown, then
     * {@link InventoryType.SlotType#CONTAINER} will be returned.
     *
     * @param inventoryView The inventory view with the slot
     * @param slot The raw slot ID
     * @return the slot type
     */
    public final InventoryType.SlotType getSlotType(InventoryView inventoryView, int slot) {
        InventoryType.SlotType type = InventoryType.SlotType.CONTAINER;
        if (slot >= 0 && slot < inventoryView.getTopInventory().getSize()) {
            switch(inventoryView.getType()) {
            case FURNACE:
                if (slot == 2) {
                    type = InventoryType.SlotType.RESULT;
                } else if(slot == 1) {
                    type = InventoryType.SlotType.FUEL;
                } else {
                    type = InventoryType.SlotType.CRAFTING;
                }
                break;
            case BREWING:
                if (slot == 3) {
                    type = InventoryType.SlotType.FUEL;
                } else {
                    type = InventoryType.SlotType.CRAFTING;
                }
                break;
            case ENCHANTING:
                type = InventoryType.SlotType.CRAFTING;
                break;
            case WORKBENCH:
            case CRAFTING:
                if (slot == 0) {
                    type = InventoryType.SlotType.RESULT;
                } else {
                    type = InventoryType.SlotType.CRAFTING;
                }
                break;
            case MERCHANT:
                if (slot == 2) {
                    type = InventoryType.SlotType.RESULT;
                } else {
                    type = InventoryType.SlotType.CRAFTING;
                }
                break;
            case BEACON:
                type = InventoryType.SlotType.CRAFTING;
                break;
            case ANVIL:
                if (slot == 2) {
                    type = InventoryType.SlotType.RESULT;
                } else {
                    type = InventoryType.SlotType.CRAFTING;
                }
                break;
            default:
                // Nothing to do, it's a CONTAINER slot
            }
        } else {
            if (slot < 0) {
                type = InventoryType.SlotType.OUTSIDE;
            } else if (inventoryView.getType() == InventoryType.CRAFTING) { // Also includes creative inventory
                if (slot < 9) {
                    type = InventoryType.SlotType.ARMOR;
                } else if (slot > 35) {
                    type = InventoryType.SlotType.QUICKBAR;
                }
            } else if (slot >= (inventoryView.countSlots() - (9 + 4 + 1))) { // Quickbar, Armor, Offhand
                type = InventoryType.SlotType.QUICKBAR;
            }
        }
        return type;
    }
    
    /**
     * Parses raw slot to inventory slot
     * @param inventoryView The InventoryView of the raw slot
     * @param rawSlot The raw slot
     * @return InventorySlotData of the inventory slot
     */
    public static InventorySlotData parseRawSlot(InventoryView inventoryView, int rawSlot) {
    	int numOfTopSlots = inventoryView.getTopInventory().getSize();
    	return new InventorySlotData(inventoryView, rawSlot < numOfTopSlots, inventoryView.convertSlot(rawSlot));
    }
    
    public static class InventorySlotData {
    	
    	private InventoryView inventoryView;
    	private boolean isTopInventory;
    	private int slot; // No
    	
    	public InventorySlotData(InventoryView inventoryView, boolean isTopInventory, int slot) {
    		this.inventoryView = inventoryView;
    		this.isTopInventory = isTopInventory;
    		this.slot = slot;
		}
    	
    	public InventoryView getInventoryView() {
			return inventoryView;
		}
    	
    	public boolean isTopInventory() {
			return isTopInventory;
		}
    	
    	public int getSlot() {
			return slot;
		}
    	
    }
	
}
