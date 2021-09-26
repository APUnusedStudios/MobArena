package net.apstudios.mobarena.managers;

import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.obj.Mob;

import java.util.HashMap;
import java.util.Map;

public class MobsManager {

    private MobArena plugin;

    private Map<String, Mob> mobs = new HashMap<>();

    public MobsManager(MobArena plugin) {
        this.plugin = plugin;
    }

    public Map<String, Mob> getMobs() {
        return mobs;
    }

}
