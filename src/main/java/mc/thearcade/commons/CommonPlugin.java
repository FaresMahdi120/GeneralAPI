package mc.thearcade.commons;

import mc.thearcade.commons.gui.legacy.Gui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public abstract class CommonPlugin extends AbstractPlugin {

    static CommonPlugin instance;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        this.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                InventoryHolder holder = event.getInventory().getHolder();
                if (holder instanceof Gui<?>) {
                    Gui<?> clickedGui = (Gui<?>) holder;
                    clickedGui.simulateClick(event);
                }
            }
            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                InventoryHolder holder = event.getInventory().getHolder();
                if (holder instanceof Gui<?>) {
                    Gui<?> clickedGui = (Gui<?>) holder;
                    clickedGui.simulateClosing(event);
                }
            }
        }, this);
    }

    public static CommonPlugin getInstance() {
        return instance;
    }
}
