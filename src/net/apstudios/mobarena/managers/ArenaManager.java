package net.apstudios.mobarena.managers;

import com.google.gson.JsonObject;
import net.apstudios.mobarena.obj.Arena;
import net.apstudios.mobarena.obj.Drop;
import net.apstudios.mobarena.obj.Mob;
import net.apstudios.mobarena.obj.Wave;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {

    private List<Arena> arenas;
    private Map<Arena, List<Player>> playersInArena;

    private Map<Arena, List<JsonObject>> arenaConfigs;
    private Map<Arena, Team> arenaTeams;
    private Map<String, Drop> drops; // DropName|Drop

    public ArenaManager() {
        arenas = new ArrayList<>();
        playersInArena = new HashMap<>();
        arenaConfigs = new HashMap<>();
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public Map<Arena, List<Player>> getPlayersInArena() {
        return playersInArena;
    }

    public Map<Arena, List<JsonObject>> getArenaConfigs() {
        return arenaConfigs;
    }

    public Map<Arena, Team> getArenaTeams() {
        return arenaTeams;
    }

    public Map<String, Drop> getDrops() {
        return drops;
    }

    public Map<Integer, Mob> getAllAliveMobs() {
        Map<Integer, Mob> aliveMobs = new HashMap<>();
        for(Arena arena : arenas) {
            if(arena.isGameStarted()) {
                Wave wave = arena.getWaves().get(arena.getCurrentWave());
                for(Map.Entry<Integer, Mob> entry : wave.getAliveMobs().entrySet()) {
                    aliveMobs.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return aliveMobs;
    }

    public void addPlayerToArena(Player player, Arena arena) {
        List<Player> players = playersInArena.get(arena);
        players.add(player);
        playersInArena.replace(arena, players);

        if(players.size() == arena.getNeededPlayersToStart()) {
            arena.startGame();
        }
    }

}
