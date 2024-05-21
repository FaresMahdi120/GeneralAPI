package mc.thearcade.commons;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractPlugin extends JavaPlugin {

    public static String getServerVersion() {
         final String packageName = Bukkit.getServer().getClass().getPackage().getName();
         return packageName.substring(packageName.lastIndexOf(46) + 1);
    }


    private CommandMap commandMap;
    private List<Runnable> shutdownHooks;

    @Override
    public void onEnable() {
        try {
            Field f;
            f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            commandMap = (CommandMap) f.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().severe("Failed to get command map!");
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (shutdownHooks != null) {
            for (Runnable runnable : shutdownHooks) {
                runnable.run();
            }
        }
    }

    public void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(this, runnable);
    }

    public BukkitTask async(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(this, runnable, delay);
    }

    public BukkitTask async(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, period);
    }

    public BukkitTask sync(Runnable runnable) {
        return Bukkit.getScheduler().runTask(this, runnable);
    }

    public BukkitTask sync(Runnable runnable, long delay) {
        return Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public BukkitTask sync(Runnable runnable, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(this, runnable, delay, period);
    }


    public void addShutdownHook(Runnable runnable) {
        if (shutdownHooks == null) {
            shutdownHooks = new ArrayList<>(1);
        }
        shutdownHooks.add(runnable);
    }

    protected void registerCommand(BukkitCommand command) {
        commandMap.register(command.getName(), command);
    }

    protected void registerCommands(BukkitCommand... commands) {
        for (BukkitCommand command : commands) {
            registerCommand(command);
        }
    }

    protected <T extends Event> void registerListener(Class<T> eventClass, Consumer<T> consumer) {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onEvent(T event) {
                consumer.accept(event);
            }
        }, this);
    }

    protected void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    protected void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            registerListener(listener);
        }
    }
}
