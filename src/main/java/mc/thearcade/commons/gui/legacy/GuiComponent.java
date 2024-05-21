package mc.thearcade.commons.gui.legacy;

import mc.thearcade.commons.CommonPlugin;
import mc.thearcade.commons.item.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
public final class GuiComponent {

    private final ItemStack item;
    private final GuiClickCallback callback;

    public GuiComponent(ItemStack item, GuiClickCallback callback) {
        this.item = item;
        this.callback = callback;
    }

    public GuiComponent(ItemBuilder builder, GuiClickCallback callback) {
        this(builder.build(), callback);
    }


    public GuiComponent(ItemStack itemStack) {
        this(itemStack, new GuiClickCallback() {
            @Override
            public <T extends CommonPlugin> void onClick(Gui<T> gui, ClickType clickType, Player player, ItemStack itemUsed, int slotUsed, InventoryClickEvent event) {

            }
        });
    }

    public GuiComponent(ItemBuilder builder) {
        this(builder.build());
    }

    public ItemStack getItem() {
        return item;
    }

    public GuiClickCallback getCallback() {
        return callback;
    }


}