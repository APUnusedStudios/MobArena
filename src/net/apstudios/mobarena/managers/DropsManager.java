package net.apstudios.mobarena.managers;

import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.obj.Kit;

import java.util.HashMap;
import java.util.Map;

public class DropsManager {

    private MobArena plugin;

    private Map<String, Kit> kits = new HashMap<>();

    public DropsManager(MobArena plugin) {
        this.plugin = plugin;
    }

    public Map<String, Kit> getKits() {
        return kits;
    }

}
