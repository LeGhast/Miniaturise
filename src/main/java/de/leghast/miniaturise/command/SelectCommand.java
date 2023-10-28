package de.leghast.miniaturise.command;

import de.leghast.miniaturise.manager.MiniatureManager;
import de.leghast.miniaturise.manager.RegionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(RegionManager.loc1 != null && RegionManager.loc2 != null) {
                RegionManager.createRegion(player);
                MiniatureManager.miniaturiseSelection(player.getLocation());
            }else{
                player.sendMessage("§cPlease select two positions");
            }
        }

        return false;
    }
}
