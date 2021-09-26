package net.apstudios.mobarena;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.apstudios.mobarena.obj.Arena;
import net.apstudios.mobarena.obj.Drop;
import net.apstudios.mobarena.obj.Mob;
import net.apstudios.mobarena.obj.Wave;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    private MobArena plugin;

    public Util(MobArena plugin) {
        this.plugin = plugin;
    }

    public static String colorText(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public boolean doesMobExist(int entityID) {
        for (Arena arena : plugin.getArenaManager().getArenas()) {
            if (arena.getWaves().get(arena.getCurrentWave()).getAliveMobs().containsKey(entityID)) {
                return true;
            }
        }
        return false;
    }

    public List<Object> getInfoFromMob(int entityID) {
        List<Object> list = new ArrayList<>();
        for (Arena arena : plugin.getArenaManager().getArenas()) {
            if (arena.getWaves().get(arena.getCurrentWave()).getAliveMobs().containsKey(entityID)) {
                Mob mob = arena.getWaves().get(arena.getCurrentWave()).getAliveMobs().get(entityID);
                list.set(0, mob);
                list.set(1, arena.getWaves().get(arena.getCurrentWave()));
                list.set(2, arena);
                return list;
            }
        }
        return null;
    }

    public Mob getMobFromConfig(String name) {
        for(JsonElement mobEntry : plugin.getConfigManager().getMobConfig().get("mobs").getAsJsonArray()) {
            JsonObject mobObj = (JsonObject) mobEntry;
            if(mobObj.get("name").getAsString().equals(name)) {
                List<PotionEffect> potionEffects = new ArrayList<>();
                for(JsonElement potionEffectEntry : mobObj.get("potionEffects").getAsJsonArray()) {
                    JsonObject potionEffectObj = (JsonObject) potionEffectEntry;
                    String type = potionEffectObj.get("type").getAsString();
                    int level = potionEffectObj.get("level").getAsInt();
                    int duration = potionEffectObj.get("duration").getAsInt();
                    PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(type), duration, level);
                    potionEffects.add(potionEffect);
                }
                List<Drop> drops = new ArrayList<>();
                for(JsonElement dropEntry : mobObj.get("drops").getAsJsonArray()) {
                    drops.add(plugin.getArenaManager().getDrops().get(dropEntry.getAsString()));
                }
                List<String> commandsWhenKilledRunOnce = new ArrayList<>();
                for(JsonElement commandRunOnceEntry : mobObj.get("commandsWhenKilled").getAsJsonObject().get("runOnce")
                        .getAsJsonArray()) {
                    commandsWhenKilledRunOnce.add(commandRunOnceEntry.getAsString());
                }
                List<String> commandsWhenKilledRunForEveryone = new ArrayList<>();
                for(JsonElement commandRunForEveryoneEntry : mobObj.get("commandsWhenKilled").getAsJsonObject()
                        .get("runForEveryone").getAsJsonArray()) {
                    commandsWhenKilledRunForEveryone.add(commandRunForEveryoneEntry.getAsString());
                }
                Mob mob = new Mob(
                        mobObj.get("name").getAsString(),
                        mobObj.get("type").getAsString(),
                        mobObj.get("useCustomName").getAsBoolean(),
                        mobObj.get("customName").getAsString(),
                        mobObj.get("health").getAsDouble(),
                        mobObj.get("isGlowing").getAsBoolean(),
                        mobObj.get("dropsExp").getAsBoolean(),
                        mobObj.get("exp").getAsInt(),
                        potionEffects,
                        drops,
                        commandsWhenKilledRunOnce,
                        commandsWhenKilledRunForEveryone
                );
                return mob;
            }
        }
        return null;
    }

    public Location getSpawnLocation(String locationName) {
        for(JsonElement locationEntry : plugin.getConfigManager().getSpawnLocationsConfig().get("spawnLocations")
                .getAsJsonArray()) {
            JsonObject locationObj = (JsonObject) locationEntry;
            String name = locationObj.get("name").getAsString();
            if(name.equals(locationName)) {
                World world = Bukkit.getWorld(locationObj.get("world").getAsString());
                double x = locationObj.get("x").getAsDouble();
                double y = locationObj.get("y").getAsDouble();
                double z = locationObj.get("z").getAsDouble();
                float pitch = locationObj.get("pitch").getAsFloat();
                float yaw = locationObj.get("yaw").getAsFloat();
                return new Location(world, x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    public boolean doesArenaByNameExist(String arenaName) {
        for(Arena arena : plugin.getArenaManager().getArenas()) {
            if(arena.getArenaName().equalsIgnoreCase(arenaName)) {
                return true;
            }
        }
        return false;
    }

    public Arena getArenaByName(String arenaName) {
        for(Arena arena : plugin.getArenaManager().getArenas()) {
            if(arena.getArenaName().equalsIgnoreCase(arenaName)) {
                return arena;
            }
        }
        return null;
    }

    public boolean canPlayerJoinArena(Arena arena) {
        if(arena.getMaxPlayers() == plugin.getArenaManager().getPlayersInArena().size()) {
            return false;
        }
        return true;
    }

    public void playerJoinArena(Player player, Arena arena) {
        if(plugin.getArenaManager().getArenas().contains(arena)) {
            plugin.getArenaManager().addPlayerToArena(player, arena);
            player.teleport(arena.getLobbyLocation());
            player.setGameMode(arena.getPlayerGamemode());
        }
    }

    public Arena getArenaPlayerIsIn(Player player) {
        for(Map.Entry<Arena, List<Player>> entry : plugin.getArenaManager().getPlayersInArena().entrySet()) {
            List<Player> players = entry.getValue();
            if(players.contains(player)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void playerLeaveArena(Player player) {
        List<Player> players = plugin.getArenaManager().getPlayersInArena().get(getArenaPlayerIsIn(player));
        players.remove(player);
        plugin.getArenaManager().getPlayersInArena().replace(getArenaPlayerIsIn(player), players);
    }

    public boolean isPlayerInArena(Player player) {
        for(Map.Entry<Arena, List<Player>> entry : plugin.getArenaManager().getPlayersInArena().entrySet()) {
            List<Player> players = entry.getValue();
            if(players.contains(player)) {
                return true;
            }
        }
        return false;
    }

}
