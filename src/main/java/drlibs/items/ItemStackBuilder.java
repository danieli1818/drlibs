package drlibs.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemStackBuilder {
	
	private ItemStack itemStack;
	
	public ItemStackBuilder(Material material) {
		this.itemStack = new ItemStack(material);
	}
	
	public ItemStackBuilder(ItemStack itemStack) {
		this.itemStack = new ItemStack(itemStack);
	}
	
	public ItemStackBuilder setAmount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}
	
	public ItemStackBuilder setData(MaterialData data) {
		itemStack.setData(data);
		return this;
	}
	
	public ItemStackBuilder setDurability(short durability) {
		itemStack.setDurability(durability);
		return this;
	}
	
	public ItemStackBuilder setItemMeta(ItemMeta itemMeta) {
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemStackBuilder setType(Material type) {
		itemStack.setType(type);
		return this;
	}
	
	public ItemStackBuilder setDisplayName(String displayName) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemStackBuilder setLocalizedName(String localizedName) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setLocalizedName(localizedName);
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemStackBuilder setLore(List<String> lore) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemStackBuilder setLoreString(String loreString) {
		List<String> lore = new ArrayList<>();
		lore.add(loreString);
		return setLore(lore);
	}
	
	public ItemStackBuilder setUnbreakable(boolean isUnbreakable) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setUnbreakable(isUnbreakable);
		itemStack.setItemMeta(itemMeta);
		return this;
	}
	
	public ItemStack build() {
		return itemStack;
	}

}
