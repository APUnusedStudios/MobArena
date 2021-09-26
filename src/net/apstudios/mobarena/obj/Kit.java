package net.apstudios.mobarena.obj;

import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Kit {

    private String name;
    private int lives;
    private List<PotionEffect> potionEffects;
    private List<InventoryItem> items;
    private List<ArmorItem> armorItems;

    public Kit(String name, int lives, List<PotionEffect> potionEffects, List<InventoryItem> items, List<ArmorItem> armorItems) {
        this.name = name;
        this.lives = lives;
        this.potionEffects = potionEffects;
        this.items = items;
        this.armorItems = armorItems;
    }

}
