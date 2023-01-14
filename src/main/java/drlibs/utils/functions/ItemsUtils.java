package drlibs.utils.functions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

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
	
	public static final String toString(final ItemStack itemStack) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(outputStream);
		bukkitObjectOutputStream.writeObject(itemStack);
		bukkitObjectOutputStream.close();
		return Base64Coder.encodeLines(outputStream.toByteArray());
	}
	
	public static final ItemStack fromString(final String string) throws IOException, ClassNotFoundException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
		BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(inputStream);
		Object object = bukkitObjectInputStream.readObject();
		if (object == null || !(object instanceof ItemStack)) {
			return null;
		}
		return (ItemStack)object;
	}
	
}
