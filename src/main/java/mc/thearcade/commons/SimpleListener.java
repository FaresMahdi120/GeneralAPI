package mc.thearcade.commons;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

public class SimpleListener<T extends Event> implements Listener {

    private final Consumer<T> consumer;

    public SimpleListener(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @EventHandler
    public void onEvent(T event) {
        consumer.accept(event);
    }

}
