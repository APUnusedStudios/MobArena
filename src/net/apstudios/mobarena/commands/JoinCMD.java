package net.apstudios.mobarena.commands;

import net.apstudios.mobarena.MobArena;
import net.apstudios.mobarena.obj.Arena;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCMD implements CommandExecutor {

    private MobArena plugin;

    public JoinCMD(MobArena plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to do this!");
        } else {
            Player player = (Player) sender;
            if(plugin.getUtil().isPlayerInArena(player)) {
                sender.sendMessage(ChatColor.RED + "You must leave your arena before joining another one!");
            } else {
                if(args.length == 1) {
                    if(plugin.getUtil().doesArenaByNameExist(args[0])) {
                        Arena arena = plugin.getUtil().getArenaByName(args[0]);
                        if(plugin.getUtil().canPlayerJoinArena(arena)) {
                            plugin.getUtil().playerJoinArena(player, arena);
                        } else {
                            player.sendMessage(ChatColor.RED + "You can't join this arena! (Is it full?)");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Incorrect command usage! Correct usage: /join <arenaName>");
                }
            }
        }

        return true;
    }
}
