package mc.thearcade.commons;

import java.util.HashMap;

public final class Cooldown<T> {

    private final HashMap<T, Long> cooldowns = new HashMap<>();
    private final long cooldown;

    public Cooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isOnCooldown(T key) {
        return cooldowns.containsKey(key) && cooldowns.get(key) > System.currentTimeMillis();
    }

    public boolean setCooldown(T key) {
        if (isOnCooldown(key)) return false;
        cooldowns.put(key, System.currentTimeMillis() + cooldown);
        return true;
    }

}
