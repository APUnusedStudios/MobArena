package net.apstudios.mobarena.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.apstudios.mobarena.MobArena;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class ConfigManager {

    private MobArena plugin;

    private JsonObject config;
    private JsonObject mobConfig;
    private JsonObject spawnLocationsConfig;
    private JsonObject dropsConfig;

    public ConfigManager(MobArena plugin) {
        this.plugin = plugin;

        loadArenaDirectory();
        loadKitsDirectory();
        loadConfig();
        loadMobConfig();
        loadSpawnLocationsConfig();
        loadDropsConfig();
    }

    private void loadConfig() {
        plugin.getLogger().log(Level.INFO, "Checking if config.json file exists...");

        File configFile = new File(plugin.getDataFolder(), "config.json");
        if (!configFile.exists()) {
            plugin.getLogger().log(Level.INFO, "The config.json file wasn't found, creating one...");
            configFile.getParentFile().mkdirs();
            plugin.getLogger().log(Level.INFO, "Successfully created the config.json file! Attempting to load config.json...");
            try {
                config = new JsonObject();

                JsonObject standalone_server = new JsonObject();
                standalone_server.addProperty("enabled", false);
                JsonArray arenas = new JsonArray();
                arenas.add(new JsonPrimitive("MyArena"));
                standalone_server.add("arenas", arenas);
                config.add("standalone_server", standalone_server);

                JsonObject leaveArenaLocation = new JsonObject();
                leaveArenaLocation.addProperty("world", "world");
                leaveArenaLocation.addProperty("x", 0.5);
                leaveArenaLocation.addProperty("y", 100.0);
                leaveArenaLocation.addProperty("z", 0.5);
                leaveArenaLocation.addProperty("yaw", 0.0);
                leaveArenaLocation.addProperty("pitch", 0.0);
                config.add("leaveArenaLocation", leaveArenaLocation);

                saveJsonToFile(configFile, config);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the config.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            plugin.getLogger().log(Level.INFO, "The config.json file was found! Attempting to load config.json...");
            try {
                Scanner scanner = new Scanner(configFile);
                String fileContent = "";
                while (scanner.hasNextLine()) {
                    fileContent += scanner.nextLine();
                }
                config = plugin.getGson().fromJson(fileContent, JsonObject.class);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the config.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void loadMobConfig() {
        plugin.getLogger().log(Level.INFO, "Checking if mobs.json file exists...");

        File mobConfigFile = new File(plugin.getDataFolder(), "mobs.json");
        if (!mobConfigFile.exists()) {
            plugin.getLogger().log(Level.INFO, "The mobs.json file wasn't found, creating one...");
            mobConfigFile.getParentFile().mkdirs();
            plugin.getLogger().log(Level.INFO, "Successfully created the mobs.json file! Attempting to load mobs.json...");
            try {
                mobConfig = new JsonObject();

                JsonArray mobs = new JsonArray();
                JsonObject myZombie = new JsonObject();
                myZombie.addProperty("name", "MyZombie");
                myZombie.addProperty("type", "Zombie");
                myZombie.addProperty("useCustomName", false);
                myZombie.addProperty("customName", "ACustomName");
                myZombie.addProperty("health", 20.0);
                myZombie.addProperty("isGlowing", false);
                myZombie.addProperty("dropsExp", false);
                myZombie.addProperty("exp", 20);

                JsonArray potionEffects = new JsonArray();
                JsonObject effect = new JsonObject();
                effect.addProperty("type", "SPEED");
                effect.addProperty("level", 1);
                effect.addProperty("duration", 10);
                potionEffects.add(effect);
                myZombie.add("potionEffects", potionEffects);

                JsonArray drops = new JsonArray();
                drops.add(new JsonPrimitive("MyDrop"));
                myZombie.add("drops", drops);

                JsonObject commandsWhenKilled = new JsonObject();
                JsonArray runOnce = new JsonArray();
                JsonArray runForEveryone = new JsonArray();
                commandsWhenKilled.add("runOnce", runOnce);
                commandsWhenKilled.add("runForEveryone", runForEveryone);
                myZombie.add("commandsWhenKilled", commandsWhenKilled);
                mobs.add(myZombie);
                mobConfig.add("mobs", mobs);

                saveJsonToFile(mobConfigFile, mobConfig);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the mobs.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            plugin.getLogger().log(Level.INFO, "The config.json file was found! Attempting to load mobs.json...");
            try {
                Scanner scanner = new Scanner(mobConfigFile);
                String fileContent = "";
                while (scanner.hasNextLine()) {
                    fileContent += scanner.nextLine();
                }
                mobConfig = plugin.getGson().fromJson(fileContent, JsonObject.class);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the mobs.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void loadSpawnLocationsConfig() {
        plugin.getLogger().log(Level.INFO, "Checking if spawn_locations.json file exists...");

        File spawnLocationsConfigFile = new File(plugin.getDataFolder(), "spawn_locations.json");
        if (!spawnLocationsConfigFile.exists()) {
            plugin.getLogger().log(Level.INFO, "The spawn_locations.json file wasn't found, creating one...");
            spawnLocationsConfigFile.getParentFile().mkdirs();
            plugin.getLogger().log(Level.INFO, "Successfully created the spawn_locations.json file! Attempting to load spawn_locations.json...");
            try {
                spawnLocationsConfig = new JsonObject();

                JsonArray spawnLocations = new JsonArray();
                JsonObject myLocation = new JsonObject();
                myLocation.addProperty("name", "MyLocation");
                myLocation.addProperty("world", "world");
                myLocation.addProperty("x", 0.5);
                myLocation.addProperty("y", 100.0);
                myLocation.addProperty("z", 0.5);
                myLocation.addProperty("yaw", 0.0);
                myLocation.addProperty("pitch", 0.0);
                spawnLocations.add(myLocation);
                spawnLocationsConfig.add("spawnLocations", spawnLocations);

                saveJsonToFile(spawnLocationsConfigFile, spawnLocationsConfig);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the spawn_locations.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            plugin.getLogger().log(Level.INFO, "The spawn_locations.json file was found! Attempting to load spawn_locations.json...");
            try {
                Scanner scanner = new Scanner(spawnLocationsConfigFile);
                String fileContent = "";
                while (scanner.hasNextLine()) {
                    fileContent += scanner.nextLine();
                }
                spawnLocationsConfig = plugin.getGson().fromJson(fileContent, JsonObject.class);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the spawn_locations.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void loadDropsConfig() {
        plugin.getLogger().log(Level.INFO, "Checking if drops.json file exists...");

        File dropsConfigFile = new File(plugin.getDataFolder(), "drops.json");
        if (!dropsConfigFile.exists()) {
            plugin.getLogger().log(Level.INFO, "The drops.json file wasn't found, creating one...");
            dropsConfigFile.getParentFile().mkdirs();
            plugin.getLogger().log(Level.INFO, "Successfully created the drops.json file! Attempting to load drops.json...");
            try {
                dropsConfig = new JsonObject();

                JsonArray drops = new JsonArray();
                JsonObject myDrop = new JsonObject();
                myDrop.addProperty("name", "MyDrop");
                myDrop.addProperty("type", "WOOD_HOE");
                myDrop.addProperty("amount", 1);
                myDrop.addProperty("durability", 50);
                myDrop.addProperty("unbreakable", true);
                myDrop.addProperty("useCustomName", true);
                myDrop.addProperty("customName", "&6My Wooden Hoe");
                myDrop.addProperty("isGlowing", true);
                JsonArray lore = new JsonArray();
                lore.add(new JsonPrimitive("This"));
                lore.add(new JsonPrimitive("is"));
                lore.add(new JsonPrimitive("a"));
                lore.add(new JsonPrimitive("lore!"));
                myDrop.add("lore", lore);
                JsonArray enchantments = new JsonArray();
                JsonObject enchantment = new JsonObject();
                enchantment.addProperty("type", "SHARPNESS");
                enchantment.addProperty("level", 1);
                enchantments.add(enchantment);
                myDrop.add("enchantments", enchantments);
                drops.add(myDrop);

                dropsConfig.add("drops", drops);

                saveJsonToFile(dropsConfigFile, dropsConfig);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the drops.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            plugin.getLogger().log(Level.INFO, "The drops.json file was found! Attempting to load drops.json...");
            try {
                Scanner scanner = new Scanner(dropsConfigFile);
                String fileContent = "";
                while (scanner.hasNextLine()) {
                    fileContent += scanner.nextLine();
                }
                dropsConfig = plugin.getGson().fromJson(fileContent, JsonObject.class);
                plugin.getLogger().log(Level.INFO, "Successfully loaded the drops.json file!");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void loadArenaDirectory() {
        plugin.getLogger().log(Level.INFO, "Checking if the Arenas directory exists...");
        File arenaDirectory = new File(plugin.getDataFolder(), "Arenas");
        if(!arenaDirectory.exists()) {
            plugin.getLogger().log(Level.INFO, "The Arenas directory wasn't found, creating the directory...");
            if(arenaDirectory.mkdirs()) {
                plugin.getLogger().log(Level.INFO, "Successfully created the Arenas directory!");
            } else {
                plugin.getLogger().log(Level.SEVERE, "Error! Couldn't create the Arenas directory!");
            }
        } else {
            plugin.getLogger().log(Level.INFO, "The Arenas directory was found!");
        }
    }

    private void loadKitsDirectory() {
        plugin.getLogger().log(Level.INFO, "Checking if the Kits directory exists...");
        File arenaDirectory = new File(plugin.getDataFolder(), "Kits");
        if(!arenaDirectory.exists()) {
            plugin.getLogger().log(Level.INFO, "The Kits directory wasn't found, creating the directory...");
            if(arenaDirectory.mkdirs()) {
                plugin.getLogger().log(Level.INFO, "Successfully created the Kits directory!");
            } else {
                plugin.getLogger().log(Level.SEVERE, "Error! Couldn't create the Kits directory!");
            }
        } else {
            plugin.getLogger().log(Level.INFO, "The Kits directory was found!");
        }
    }

    private void saveJsonToFile(File file, JsonObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(plugin.getGson().toJson(jsonObject));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getMobConfig() {
        return mobConfig;
    }

    public JsonObject getSpawnLocationsConfig() {
        return spawnLocationsConfig;
    }

    public JsonObject getConfig() {
        return config;
    }

}
