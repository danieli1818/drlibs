package drlibs.utils.functions;

import org.bukkit.inventory.ItemStack;

public class ItemsUtils {

	public static final boolean isArmor(final ItemStack itemStack) {
        if (itemStack == null)
            return false;
        final String typeNameString = itemStack.getType().name();
        if (typeNameString.endsWith("_HELMET")
                || typeNameString.endsWith("_CHESTPLATE")
                || typeNameString.endsWith("_LEGGINGS")
                || typeNameString.endsWith("_BOOTS")) {
            return true;
            }

        return false;
    }
	
	public enum ArmorType {
		HELMET,
		CHESTPLATE,
		LEGGINGS,
		BOOTS
	}
	
	public static final ArmorType getArmorType(final ItemStack itemStack) {
        if (!isArmor(itemStack))
            return null;
        final String typeNameString = itemStack.getType().name();
        if (typeNameString.endsWith("_HELMET")) {
            return ArmorType.HELMET;
        } else if (typeNameString.endsWith("_CHESTPLATE")) {
        	return ArmorType.CHESTPLATE;
        } else if (typeNameString.endsWith("_LEGGINGS")) {
        	return ArmorType.LEGGINGS;
        } else {
        	return ArmorType.BOOTS;
        }
    }
	
}
