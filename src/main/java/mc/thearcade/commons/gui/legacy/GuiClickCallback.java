package mc.thearcade.commons.gui.legacy;

import mc.thearcade.commons.CommonPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface GuiClickCallback {

    <T extends CommonPlugin> void onClick(Gui<T> gui, ClickType clickType, Player player, ItemStack itemUsed, int slotUsed, InventoryClickEvent event);

}