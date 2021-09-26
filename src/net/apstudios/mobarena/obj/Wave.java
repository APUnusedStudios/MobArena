package net.apstudios.mobarena.obj;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wave {

    private MobArena plugin;

    private String waveName;
    private boolean useStartSound;
    private Sound startSound;
    private float startSoundVolume;
    private float startSoundPitch;
    private boolean useEndSound;
    private Sound endSound;
    private float endSoundVolume;
    private float endSoundPitch;
    private boolean isPauseAfterWaveEnabled;
    private long pauseAfterWaveTimeInTicks;
    private boolean isTeleportAtPauseEnabled;
    private Location teleportPauseStartLocation;
    private Location teleportPauseEndLocation;
    private List<String> commandsToBeRunOnceOnWaveStart;
    private List<String> commandsToBeRunOnceOnWaveEnd;
    private List<String> commandsToBeRunForEveryoneOnWaveStart;
    private List<String> commandsToBeRunForEveryoneOnWaveEnd;
    private JsonArray mobsList;

    private Map<Integer, Mob> aliveMobs; // EntityID, Mob.class
    private Arena arena;

    private boolean isLastWave = false;

    public Wave(String waveName, boolean useStartSound, Sound startSound, float startSoundVolume, float startSoundPitch,
                boolean useEndSound, Sound endSound, float endSoundVolume, float endSoundPitch,
                boolean isPauseAfterWaveEnabled, long pauseAfterWaveTimeInTicks, boolean isTeleportAtPauseEnabled,
                Location teleportPauseStartLocation, Location teleportPauseEndLocation,
                List<String> commandsToBeRunOnceOnWaveStart, List<String> commandsToBeRunOnceOnWaveEnd,
                List<String> commandsToBeRunForEveryoneOnWaveStart, List<String> commandsToBeRunForEveryoneOnWaveEnd,
                JsonArray mobsList, Arena arena, MobArena plugin) {
        this.waveName = waveName;
        this.useStartSound = useStartSound;
        this.startSound = startSound;
        this.startSoundVolume = startSoundVolume;
        this.startSoundPitch = startSoundPitch;
        this.useEndSound = useEndSound;
        this.endSound = endSound;
        this.endSoundVolume = endSoundVolume;
        this.endSoundPitch = endSoundPitch;
        this.isPauseAfterWaveEnabled = isPauseAfterWaveEnabled;
        this.pauseAfterWaveTimeInTicks = pauseAfterWaveTimeInTicks;
        this.isTeleportAtPauseEnabled = isTeleportAtPauseEnabled;
        this.teleportPauseStartLocation = teleportPauseStartLocation;
        this.teleportPauseEndLocation = teleportPauseEndLocation;
        this.commandsToBeRunOnceOnWaveStart = commandsToBeRunOnceOnWaveStart;
        this.commandsToBeRunOnceOnWaveEnd = commandsToBeRunOnceOnWaveEnd;
        this.commandsToBeRunForEveryoneOnWaveStart = commandsToBeRunForEveryoneOnWaveStart;
        this.commandsToBeRunForEveryoneOnWaveEnd = commandsToBeRunForEveryoneOnWaveEnd;
        this.mobsList = mobsList;

        this.aliveMobs = new HashMap<>();
        this.arena = arena;

        this.plugin = plugin;
    }

    public void startWave() {
        if (useStartSound) {
            for (Player player : plugin.getArenaManager().getPlayersInArena().get(arena)) {
                player.playSound(player.getLocation(), startSound, startSoundVolume, startSoundPitch);
            }
        }
        for (String command : commandsToBeRunOnceOnWaveStart) {
            Bukkit.dispatchCommand(null, command);
        }
        for (String command : commandsToBeRunForEveryoneOnWaveStart) {
            for (Player player : plugin.getArenaManager().getPlayersInArena().get(arena)) {
                Bukkit.dispatchCommand(null, command.replace(Placeholders.PLAYER_NAME, player.getName()).replace(Placeholders.ARENA_NAME, arena.getArenaName()));
            }
        }

        for (JsonElement mobEntry : mobsList) {
            JsonObject mobObj = (JsonObject) mobEntry;
            Mob mob = plugin.getUtil().getMobFromConfig(mobObj.get("mobName").getAsString());
            LivingEntity mobEntity = mob.spawnEntity(plugin.getArenaManager(), arena, this, plugin.getUtil().getSpawnLocation(mobObj.get("spawnLocation").getAsString()));
            aliveMobs.put(mobEntity.getEntityId(), mob);
        }
    }

    public void endWave() {
        if (useEndSound) {
            for (Player player : plugin.getArenaManager().getPlayersInArena().get(arena)) {
                player.playSound(player.getLocation(), endSound, endSoundVolume, endSoundPitch);
            }
        }
        for (String command : commandsToBeRunOnceOnWaveEnd) {
            Bukkit.dispatchCommand(null, command);
        }
        for (String command : commandsToBeRunForEveryoneOnWaveEnd) {
            for (Player player : plugin.getArenaManager().getPlayersInArena().get(arena)) {
                Bukkit.dispatchCommand(null, command.replace(Placeholders.PLAYER_NAME, player.getName()).replace(Placeholders.ARENA_NAME, arena.getArenaName()));
            }
        }

        if(isLastWave) {
            arena.endGame();
        }

        if (isPauseAfterWaveEnabled) {
            if (isTeleportAtPauseEnabled) {
                for (Player player : plugin.getArenaManager().getPlayersInArena().get(arena)) {
                    player.teleport(teleportPauseStartLocation);
                }
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (isTeleportAtPauseEnabled) {
                        for (Player player : plugin.getArenaManager().getPlayersInArena().get(arena)) {
                            player.teleport(teleportPauseEndLocation);
                        }
                    }
                    arena.nextWave();
                }
            }, pauseAfterWaveTimeInTicks);
        } else {
            arena.nextWave();
        }

    }

    public void addEntityToAliveMobsList(LivingEntity entity, Mob mob) {
        aliveMobs.put(entity.getEntityId(), mob);
    }

    public void removeEntityFromAliveMobsList(int entityID) {
        aliveMobs.remove(entityID);
    }

    public Map<Integer, Mob> getAliveMobs() {
        return aliveMobs;
    }

    public void setLastWave(boolean isLastWave) {
        this.isLastWave = isLastWave;
    }
}
