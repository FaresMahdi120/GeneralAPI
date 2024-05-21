package mc.thearcade.commons.utils;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public class InventoryUtils
{
    public static int availableSlots(final PlayerInventory inventory) {
        int count = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack item = inventory.getItem(i);
            if (item == null || item.getType().equals((Object)Material.AIR)) {
                ++count;
            }
        }
        return count;
    }

    public static int getFirstAvailableSlot(final PlayerInventory inventory) {
        for (int i = 0; i < 45; ++i) {
            final ItemStack item = inventory.getItem(i);
            if (item == null || item.getType().equals((Object)Material.AIR)) {
                return i;
            }
        }
        return -1;
    }

    public static String getTitle(final HumanEntity he) {
        final InventoryView view = he.getOpenInventory();
        try {
            return view.getTitle();
        }
        catch (IllegalStateException ex) {
            return "";
        }
    }
}
