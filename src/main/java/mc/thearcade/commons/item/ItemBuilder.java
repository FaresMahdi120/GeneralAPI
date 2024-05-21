package mc.thearcade.commons.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public final class ItemBuilder {

    public static ItemBuilder from(final ConfigurationSection section) {
        Material material;
        try {
            material = Material.getMaterial(Objects.requireNonNull(section.getString("material")));
        }
        catch (Exception e) {
            material = Material.STONE;
        }
        final String name = section.getString("name");
        final List<String> lore = (List<String>)section.getStringList("lore");
        final int amount = section.getInt("amount", 1);
        return new ItemBuilder(material)
                .setDisplayName(name)
                .addLores(lore)
                .setAmount(amount);
    }

    private final Material material;
    private final List<ItemFlag> itemFlags;
    private final List<String> lore;
    private final Map<Enchantment, Integer> enchantments;
    private int amount;
    private boolean unbreakable;
    private String displayName;
    private short damage;

    public ItemBuilder(Material material, int amount) {
        this.material = material;
        this.amount = amount;
        this.unbreakable = false;
        this.displayName = material.name();
        this.lore = new ArrayList<>();
        this.enchantments = new HashMap<>();
        this.itemFlags = new ArrayList<>();
        this.damage = -1;
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.displayName = ChatColor.translateAlternateColorCodes('&', displayName);
        return this;
    }

    public ItemBuilder addLores(String... lores) {
        for (String lore : lores) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        return this;
    }
    public ItemBuilder addLore(List<String> lore){
        for (String line : lore){
            this.lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return this;
    }

    public ItemBuilder addLores(Collection<String> lores) {
        for (String lore : lores) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
        }
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        this.itemFlags.addAll(Arrays.asList(itemFlags));
        return this;
    }

    public ItemBuilder setDamage(short damage) {
        this.damage = damage;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack;
        if (damage == -1) {
            itemStack = new ItemStack(this.material, this.amount);
        }
        else {
            itemStack = new ItemStack(this.material, this.amount, this.damage);
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(this.unbreakable);
        meta.setDisplayName(this.displayName);
        meta.setLore(this.lore);
        for (Map.Entry<Enchantment, Integer> entry : this.enchantments.entrySet()) {
            meta.addEnchant(entry.getKey(), entry.getValue(), true);
        }
        meta.addItemFlags(this.itemFlags.toArray(new ItemFlag[0]));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
