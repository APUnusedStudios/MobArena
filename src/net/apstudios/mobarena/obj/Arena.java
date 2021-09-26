package net.apstudios.mobarena.obj;

import com.google.gson.JsonObject;
import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Arena {

    private MobArena plugin;

    private String arenaName;
    private GameMode playerGamemode;
    private GameMode spectatorGamemode;
    private Location lobbyLocation;
    private Location spawnLocation;
    private Location respawnLocation;
    private Location spectatorLocation;
    private JsonObject mobsConfig;
    private List<String> commandsWhenGameFinishedRunOnce;
    private List<String> commandsWhenGameFinishedRunForEveryone;
    private int maxPlayers;
    private int neededPlayersToStart;
    private long gameStartCountdownInSeconds;

    private boolean gameStarted;
    private List<Wave> waves;
    private int currentWave;

    public Arena(String arenaName, String playerGamemode, String spectatorGamemode, Location lobbyLocation,
                 Location spawnLocation, Location respawnLocation, Location spectatorLocation, List<Wave> waves,
                 MobArena plugin, JsonObject mobsConfig, List<String> commandsWhenGameFinishedRunOnce,
                 List<String> commandsWhenGameFinishedRunForEveryone, int maxPlayers, int neededPlayersToStart,
                 int gameStartCountdownInSeconds) {
        this.arenaName = arenaName;
        this.playerGamemode = GameMode.valueOf(playerGamemode);
        this.spectatorGamemode = GameMode.valueOf(spectatorGamemode);
        this.lobbyLocation = lobbyLocation;
        this.spawnLocation = spawnLocation;
        this.respawnLocation = respawnLocation;
        this.spectatorLocation = spectatorLocation;
        this.gameStarted = false;
        this.waves = waves;
        this.currentWave = 0;
        this.plugin = plugin;
        this.mobsConfig = mobsConfig;
        this.commandsWhenGameFinishedRunOnce = commandsWhenGameFinishedRunOnce;
        this.commandsWhenGameFinishedRunForEveryone = commandsWhenGameFinishedRunForEveryone;
        this.maxPlayers = maxPlayers;
        this.neededPlayersToStart = neededPlayersToStart;
        this.gameStartCountdownInSeconds = gameStartCountdownInSeconds;
    }

    public void startGame() {
        gameStarted = true;

        for(Player player : plugin.getArenaManager().getPlayersInArena().get(this)) {
            player.sendMessage(ChatColor.GREEN + "Game is starting in " + gameStartCountdownInSeconds + " seconds!");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : plugin.getArenaManager().getPlayersInArena().get(this)) {
                    player.teleport(spawnLocation);
                }
            }
        }, gameStartCountdownInSeconds*20);
        waves.get(currentWave).startWave();
    }

    public void endGame() {
        for(String command : commandsWhenGameFinishedRunOnce) {
            Bukkit.dispatchCommand(null, command.replace(Placeholders.ARENA_NAME, arenaName));
        }
        for(String command : commandsWhenGameFinishedRunForEveryone) {
            for(Player p : plugin.getArenaManager().getPlayersInArena().get(this)) {
                Bukkit.dispatchCommand(null, command.replace(Placeholders.PLAYER_NAME, p.getName()).replace(Placeholders.ARENA_NAME, arenaName));
            }
        }
    }

    public void nextWave() {
        currentWave++;
        if(waves.size() == currentWave-1) {
            waves.get(currentWave).setLastWave(true);
        }
        waves.get(currentWave).startWave();
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public List<Wave> getWaves() {
        return waves;
    }

    public String getArenaName() {
        return arenaName;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getNeededPlayersToStart() {
        return neededPlayersToStart;
    }

    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public GameMode getPlayerGamemode() {
        return playerGamemode;
    }

}
