package net.apstudios.mobarena.managers;

import net.apstudios.mobarena.MobArena;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class SpawnLocationsManager {

    private MobArena plugin;

    private Map<String, Location> spawnLocations = new HashMap<>();

    public SpawnLocationsManager(MobArena plugin) {
        this.plugin = plugin;
    }

    Map<String, Location> getSpawnLocations() {
        return spawnLocations;
    }


}
