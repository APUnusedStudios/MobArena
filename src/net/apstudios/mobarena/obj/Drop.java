package net.apstudios.mobarena.obj;

import net.apstudios.mobarena.Util;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Drop {

    private String dropName;
    private Material type;
    private int amount;
    private int durability;
    private boolean unbreakable;
    private boolean useCustomName;
    private String customName;
    private boolean isGlowing;
    private List<String> lore;
    private Map<Enchantment, Integer> enchantments;

    public Drop(String dropName, Material type, int amount, int durability, boolean unbreakable,
                boolean useCustomName, String customName, boolean isGlowing, List<String> lore,
                Map<Enchantment, Integer> enchantments) {
        this.dropName = dropName;
        this.type = type;
        this.amount = amount;
        this.durability = durability;
        this.unbreakable = unbreakable;
        this.useCustomName = useCustomName;
        this.customName = customName;
        this.isGlowing = isGlowing;
        this.lore = lore;
        this.enchantments = enchantments;
    }

    public boolean isGlowing() {
        return isGlowing;
    }

    public ItemStack getDropItemStack() {
        ItemStack is = new ItemStack(type, amount, (short) durability);
        ItemMeta im = is.getItemMeta();
        im.spigot().setUnbreakable(unbreakable);
        if(useCustomName) {
            im.setDisplayName(Util.colorText(customName));
        }
        im.setLore(lore);
        for(Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            im.addEnchant(entry.getKey(), entry.getValue(), true);
        }
        is.setItemMeta(im);
        return is;
    }
}
