package net.apstudios.mobarena.obj;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.List;

public class ArmorItem {

    private Material type;
    private String slot;
    private boolean useCustomDisplayName;
    private String customDisplayName;
    private int amount;
    private double durability;
    private boolean unbreakable;
    private List<String> lore;
    private List<Enchantment> enchantments;

    public ArmorItem(Material type, String slot, boolean useCustomDisplayName, String customDisplayName, int amount, double durability, boolean unbreakable, List<String> lore, List<Enchantment> enchantments) {
        this.type = type;
        this.slot = slot;
        this.useCustomDisplayName = useCustomDisplayName;
        this.customDisplayName = customDisplayName;
        this.amount = amount;
        this.durability = durability;
        this.unbreakable = unbreakable;
        this.lore = lore;
        this.enchantments = enchantments;
    }

}
