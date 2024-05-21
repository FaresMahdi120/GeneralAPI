package mc.thearcade.commons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public class CoolDownManager implements Runnable{
    private static final HashMap<String, CooldownEntry> cooldowns = new HashMap<>();
    private static final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

    public static boolean isOnCooldown(String cooldownType, Player player) {
        String key = generateKey(cooldownType, player);
        CooldownEntry entry = cooldowns.get(key);
        return entry != null && entry.isActive();
    }

    public static long getTimeLeft(String cooldownType, Player player) {
        String key = generateKey(cooldownType, player);
        CooldownEntry entry = cooldowns.get(key);
        return entry != null ? entry.getTimeLeft() : 0;
    }

    public static String getCooldownMessage(String cooldownType, Player player) {
        String key = generateKey(cooldownType, player);
        CooldownEntry entry = cooldowns.get(key);
        return entry != null ? entry.getMessage() : null;
    }

    public static void setCooldown(String cooldownType, Player player, int cooldownSeconds, String message) {
        String key = generateKey(cooldownType, player);
        cooldowns.put(key, new CooldownEntry(cooldownSeconds, message));
        scheduleCooldownRemoval(key, cooldownSeconds);
    }

    private static void scheduleCooldownRemoval(String key, int cooldownSeconds) {
        scheduler.runTaskLater(CommonPlugin.getInstance(), () -> removeCooldown(key), cooldownSeconds * 20L); // 20 ticks per second
    }

    private static void removeCooldown(String key) {
        cooldowns.remove(key);
    }

    private static String generateKey(String cooldownType, Player player) {
        return cooldownType + ":" + player.getUniqueId().toString();
    }

    private static class CooldownEntry {
        private final long startTime;
        private final int cooldownSeconds;
        private final String message;

        public CooldownEntry(int cooldownSeconds, String message) {
            this.startTime = System.currentTimeMillis();
            this.cooldownSeconds = cooldownSeconds;
            this.message = message;
        }

        public boolean isActive() {
            return (System.currentTimeMillis() - startTime) / 1000 < cooldownSeconds;
        }

        public long getTimeLeft() {
            return Math.max(0, (cooldownSeconds * 1000 - (System.currentTimeMillis() - startTime)));
        }

        public String getMessage() {
            return message;
        }
    }

    @Override
    public void run() {
        // Optional: You can add additional logic here if needed for the scheduler task
    }
}
