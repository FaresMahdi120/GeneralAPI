package mc.thearcade.commons;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        try {
            Object e;
            Constructor subtitleConstructor;
            if (title != null) {
                title = ChatColor.translateAlternateColorCodes('&', title);
                title = title.replaceAll("%player%", player.getDisplayName());
                e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
                Object chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object titlePacket = subtitleConstructor.newInstance(e, chatTitle, fadeIn, stay, fadeOut);
                this.sendPacket(player, titlePacket);
                e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object)null);
                chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"));
                titlePacket = subtitleConstructor.newInstance(e, chatTitle);
                this.sendPacket(player, titlePacket);
            }

            if (subtitle != null) {
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object)null);
                Object chatSubtitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + title + "\"}");
                subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                Object subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                this.sendPacket(player, subtitlePacket);
                e = this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object)null);
                chatSubtitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke((Object)null, "{\"text\":\"" + subtitle + "\"}");
                subtitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
                subtitlePacket = subtitleConstructor.newInstance(e, chatSubtitle, fadeIn, stay, fadeOut);
                this.sendPacket(player, subtitlePacket);
            }
        } catch (Exception var13) {
            CommonPlugin.getInstance().getLogger().log(Level.WARNING, "Failed to handle reflection", var13);
        }

    }
    private void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", this.getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception var5) {
            CommonPlugin.getInstance().getLogger().log(Level.WARNING, "Failed to send packet", var5);
        }

    }

    private Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var4) {
            return null;
        }
    }
    private static String findAndReplaceRegex(String regex, String input) {
        int i = 0;
        ArrayList<String> matches = new ArrayList<>();
        ArrayList<net.md_5.bungee.api.ChatColor> colorSet = new ArrayList<>();
        Matcher patternMatcher = Pattern.compile(regex).matcher(input);
        while(patternMatcher.find()) {
            matches.add(patternMatcher.group());
        }
        for(String match : matches) {
            colorSet.add(net.md_5.bungee.api.ChatColor.valueOf(match.substring(1)));
        }
        Iterator<String> matchIterator = matches.iterator();
        Iterator<net.md_5.bungee.api.ChatColor> colorIterator = colorSet.iterator();
        while(matchIterator.hasNext() && colorIterator.hasNext()) {
            input = input.replaceFirst(matchIterator.next(), colorIterator.next().toString());
        }
        return input;
    }
    public static double removeExtra(double num){
        String numberString = Double.toString(num);
        int decimalPlaces = numberString.length() - numberString.indexOf('.') - 1;
        if (decimalPlaces > 1){
            return Math.floor((num * 100) / 100);
        }
        return num;
    }
    public static String convertToRoman(int num) {
        final int[] ROMAN_VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        final String[] ROMAN_LETTERS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ROMAN_VALUES.length; i++) {
            while (num >= ROMAN_VALUES[i]) {
                result.append(ROMAN_LETTERS[i]);
                num -= ROMAN_VALUES[i];
            }
        }
        return result.toString();
    }
    public static String colorWithPlaceHolders(String text, Player player) {
        text = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes(net.md_5.bungee.api.ChatColor.COLOR_CHAR, text);
        text = findAndReplaceRegex("!#[0-9,a-f,A-F]{6}", text);
        text = findAndReplaceRegex("&#[0-9,a-f,A-F]{6}", text);
        text = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', text);

        return PlaceholderAPI.setPlaceholders(player, text);
    }
    public static String colorNoPlaceholder(String text) {
        text = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes(net.md_5.bungee.api.ChatColor.COLOR_CHAR, text);
        text = findAndReplaceRegex("!#[0-9,a-f,A-F]{6}", text);
        text = findAndReplaceRegex("&#[0-9,a-f,A-F]{6}", text);
        text = net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', text);

        return text;
    }
    public static String decode(String text){
        return ChatColor.stripColor(text);
    }
}
