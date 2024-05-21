package mc.thearcade.commons.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class PlayerUtils {

    private PlayerUtils() {}


    public static void subtractHeldItem(final Player player, final int amount) {
        ItemStack heldItem = player.getItemInHand();
        if (heldItem.getAmount() < amount) {
            player.setItemInHand(null);
            return;
        }
        ItemStack clone = heldItem.clone();
        clone.setAmount(heldItem.getAmount() - amount);
        player.setItemInHand(clone);
    }

}
