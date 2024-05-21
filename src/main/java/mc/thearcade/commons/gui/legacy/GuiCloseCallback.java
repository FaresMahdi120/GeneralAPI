package mc.thearcade.commons.gui.legacy;


import org.bukkit.entity.Player;

@FunctionalInterface
public interface GuiCloseCallback {

    void onClose(Gui<?> gui, Player player);

}