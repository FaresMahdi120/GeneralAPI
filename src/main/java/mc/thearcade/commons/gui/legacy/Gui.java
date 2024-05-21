package mc.thearcade.commons.gui.legacy;

import mc.thearcade.commons.CommonPlugin;
import mc.thearcade.commons.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Gui<T extends CommonPlugin> implements InventoryHolder {

    public static final ItemStack FILLER_ITEM = new ItemBuilder(Material.STAINED_GLASS_PANE)
            .setDamage(DyeColor.BLACK.getDyeData())
            .setDisplayName(" ")
            .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            .build();

    private static final ItemStack CLOSE_ITEM = new ItemBuilder(Material.ARROW)
            .setDisplayName("&c&lClose")
            .build();

    protected final T plugin;
    private final Map<Integer, GuiComponent> components;
    private final Inventory inventory;
    private GuiCloseCallback closeCallback;

    public Gui(T plugin, int rows, String title) {
        this.plugin = plugin;
        this.inventory = plugin.getServer().createInventory(this, rows, ChatColor.translateAlternateColorCodes('&', title));
        this.components = new HashMap<>(this.inventory.getSize());
    }

    public Gui(T plugin, String title, InventoryType type) {
        this.plugin = plugin;
        this.inventory = plugin.getServer().createInventory(this, type, ChatColor.translateAlternateColorCodes('&', title));
        this.components = new HashMap<>(this.inventory.getSize());
    }

    public void setCloseCallback(GuiCloseCallback closeCallback) {
        this.closeCallback = closeCallback;
    }

    public void addComponent(int slot, GuiComponent component) {
        this.components.put(slot, component);
    }

    public void removeComponent(int slot) {
        this.components.remove(slot);
    }

    public GuiComponent getComponent(int slot) {
        return this.components.get(slot);
    }

    @Override
    public  Inventory getInventory() {
        return this.inventory;
    }

    public void open( Player player) {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            GuiComponent component = this.components.get(i);
            if (component == null) {
                this.inventory.setItem(i, FILLER_ITEM);
            } else {
                this.inventory.setItem(i, component.getItem());
            }
        }
        player.openInventory(this.inventory);
    }

    public void close(Player player){
        if (player.getOpenInventory().getTopInventory().getHolder() == this) {
            player.closeInventory();
        }
    }

    public void addCloseButton(int slot) {
        this.addComponent(slot, new GuiComponent(CLOSE_ITEM, new GuiClickCallback() {

            @Override
            public <T extends CommonPlugin> void onClick(Gui<T> gui, ClickType clickType, Player player, ItemStack itemUsed, int slotUsed, InventoryClickEvent event) {

            }
        }));
    }

    public void refresh() {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            GuiComponent component = this.components.get(i);
            if (component == null) {
                this.inventory.setItem(i, FILLER_ITEM);
            } else {
                this.inventory.setItem(i, component.getItem());
            }
        }
    }

    public void refresh(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            GuiComponent component = this.components.get(i);
            if (component == null) {
                inventory.setItem(i, FILLER_ITEM);
            } else {
                inventory.setItem(i, component.getItem());
            }
        }
    }

    public void simulateClick(InventoryClickEvent event) {
        event.setCancelled(true);
        GuiComponent component = this.components.get(event.getSlot());
        if (component != null && event.getWhoClicked() instanceof Player) {
            component.getCallback().onClick(this, event.getClick(), (Player) event.getWhoClicked(), event.getCurrentItem(), event.getSlot(), event);
        }
    }

    public void simulateClosing(InventoryCloseEvent event) {
        if (closeCallback == null) return;
        if (event.getPlayer() instanceof Player) {
            closeCallback.onClose(this, (Player) event.getPlayer());
        }
    }


}