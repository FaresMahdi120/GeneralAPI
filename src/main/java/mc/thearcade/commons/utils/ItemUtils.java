package mc.thearcade.commons.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemUtils {

    private ItemUtils() {}

    public static boolean isValid(final ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }

    public static ItemStack hidePotionEffects(final ItemStack itemstack) {
        final ItemStack is = itemstack.clone();
        final ItemMeta meta = is.hasItemMeta() ? is.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemstack.getType());
        if (meta == null) {
            return itemstack;
        }
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        is.setItemMeta(meta);
        return is;
    }
}
