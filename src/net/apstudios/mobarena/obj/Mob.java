package net.apstudios.mobarena.obj;

import net.apstudios.mobarena.managers.ArenaManager;
import net.apstudios.mobarena.Placeholders;
import net.apstudios.mobarena.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Mob {

    private String mobName;
    private String type;
    private boolean useCustomName;
    private String customName;
    private double health;
    private boolean isGlowing;
    private boolean dropsExp;
    private int exp;
    private List<PotionEffect> potionEffects;
    private List<Drop> drops;
    private List<String> commandsWhenKilledRunOnce;
    private List<String> commandsWhenKilledRunForEveryone;

    public Mob(String mobName, String type, boolean useCustomName, String customName, double health, boolean isGlowing,
               boolean dropsExp, int exp, List<PotionEffect> potionEffects, List<Drop> drops,
               List<String> commandsWhenKilledRunOnce, List<String> commandsWhenKilledRunForEveryone) {
        this.mobName = mobName;
        this.type = type;
        this.useCustomName = useCustomName;
        this.customName = customName;
        this.health = health;
        this.isGlowing = isGlowing;
        this.dropsExp = dropsExp;
        this.exp = exp;
        this.potionEffects = potionEffects;
        this.drops = drops;
        this.commandsWhenKilledRunOnce = commandsWhenKilledRunOnce;
        this.commandsWhenKilledRunForEveryone = commandsWhenKilledRunForEveryone;
    }

    public LivingEntity spawnEntity(ArenaManager arenaManager, Arena arena, Wave wave, Location spawnLocation) {
        LivingEntity entity = (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation,
                EntityType.valueOf(type));
        if(useCustomName) {
            entity.setCustomName(Util.colorText(customName));
        }
        entity.setHealth(health);
        entity.setGlowing(isGlowing);
        for(PotionEffect effect : potionEffects) {
            entity.addPotionEffect(effect);
        }
        for(String command : commandsWhenKilledRunOnce) {
            Bukkit.dispatchCommand(null, command.replace(Placeholders.ARENA_NAME, arena.getArenaName()));
        }
        for(String command : commandsWhenKilledRunForEveryone) {
            for(Player p : arenaManager.getPlayersInArena().get(arena)) {
                Bukkit.dispatchCommand(null, command.replace(Placeholders.PLAYER_NAME, p.getName()).replace(Placeholders.ARENA_NAME, arena.getArenaName()));
            }
        }
        wave.addEntityToAliveMobsList(entity, this);
        return entity;
    }

    public boolean doesMobDropExp() {
        return dropsExp;
    }

    public int getExp() {
        return exp;
    }

    public List<Drop> getDrops() {
        return drops;
    }
}
