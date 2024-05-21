package mc.thearcade.commons.region;

import com.google.gson.JsonObject;
import mc.thearcade.commons.utils.JsonUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;

public final class Region {

    public static Region fromFileOrNull(final File file) {
        try {
            return new Region(file);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private final String name;
    private final World world;
    private final Vector min;
    private final Vector max;
    private Vector spawn;
    private float spawnYaw, spawnPitch;

    public Region(File file) throws IllegalArgumentException {
        JsonObject json = JsonUtils.castJson(file, JsonObject.class);
        if (json == null) throw new IllegalArgumentException("File is not a valid region file");
        this.name = json.get("name").getAsString();
        this.world = Bukkit.getWorld(json.get("world").getAsString());
        this.min = JsonUtils.castJson(json.get("min").getAsString(), Vector.class);
        this.max = JsonUtils.castJson(json.get("max").getAsString(), Vector.class);
        if (this.world == null || this.min == null || this.max == null) throw new IllegalArgumentException("File is not a valid region file");
        this.spawn = JsonUtils.castJson(json.get("spawn").getAsString(), Vector.class);
        this.spawnYaw = json.get("spawnYaw").getAsFloat();
        this.spawnPitch = json.get("spawnPitch").getAsFloat();
    }

    public Region(String name, World world, Vector min, Vector max, Vector spawn, float spawnYaw, float spawnPitch) {
        this.name = name;
        this.world = world;
        this.min = new Vector(Math.min(min.getX(), max.getX()), Math.min(min.getY(), max.getY()), Math.min(min.getZ(), max.getZ()));
        this.max = new Vector(Math.max(min.getX(), max.getX()), Math.max(min.getY(), max.getY()), Math.max(min.getZ(), max.getZ()));
    }

    public void teleportSpawn(Player player) {
        player.teleport(spawn.toLocation(world, spawnYaw, spawnPitch));
    }

    public boolean contains(double x, double y, double z) {
        return x >= min.getX() && x <= max.getX() && y >= min.getY() && y <= max.getY() && z >= min.getZ() && z <= max.getZ();
    }

    public boolean contains(Vector vector) {
        return contains(vector.getX(), vector.getY(), vector.getZ());
    }

    public boolean contains(Location location) {
        return location.getWorld().equals(world) && contains(location.getX(), location.getY(), location.getZ());
    }

    public void save(File file) {
        JsonObject json  = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("world", world.getName());
        json.addProperty("min", min.toString());
        json.addProperty("max", max.toString());
        JsonUtils.writeJson(file, json, JsonObject.class);
    }

}
