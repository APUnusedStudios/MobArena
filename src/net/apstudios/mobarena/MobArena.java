package net.apstudios.mobarena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.apstudios.mobarena.events.EntityDeath;
import net.apstudios.mobarena.managers.ArenaManager;
import net.apstudios.mobarena.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MobArena extends JavaPlugin {

    private ArenaManager arenaManager;
    private Util util;
    private ConfigManager configManager;

    private Gson gson;

    @Override
    public void onEnable() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        arenaManager = new ArenaManager();
        util = new Util(this);
        configManager = new ConfigManager(this);

        Bukkit.getPluginManager().registerEvents(new EntityDeath(this), this);
    }

    @Override
    public void onDisable() {

    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public Util getUtil() {
        return util;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Gson getGson() {
        return gson;
    }
}
