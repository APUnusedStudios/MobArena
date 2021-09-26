package net.apstudios.mobarena.commands;

import com.google.gson.JsonObject;
import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.obj.Arena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCMD implements CommandExecutor {

    private MobArena plugin;

    public LeaveCMD(MobArena plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to do this!");
        } else {
            Player player = (Player) sender;
            if(plugin.getUtil().isPlayerInArena(player)) {
                JsonObject hubLoc = plugin.getConfigManager().getConfig().get("leaveArenaLocation").getAsJsonObject();
                Location hubLocation = new Location(Bukkit.getWorld(hubLoc.get("world").getAsString()),
                        hubLoc.get("x").getAsDouble(), hubLoc.get("y").getAsDouble(), hubLoc.get("z").getAsDouble(),
                        hubLoc.get("yaw").getAsFloat(), hubLoc.get("pitch").getAsFloat());
                player.teleport(hubLocation);
                plugin.getUtil().playerLeaveArena(player);
            } else {
                player.sendMessage(ChatColor.RED + "You are currently not in an arena!");
            }
        }

        return true;
    }
}
