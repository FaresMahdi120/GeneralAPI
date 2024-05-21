package mc.thearcade.commons;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collection;

public final class Chat {

    private Chat() {}

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void message(CommandSender sender, String... messages) {
        for (String message : messages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public static void message(CommandSender sender, Collection<String> messages) {
        for (String message : messages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
