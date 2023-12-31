package de.leghast.miniaturise.command;

import de.leghast.miniaturise.Miniaturise;
import de.leghast.miniaturise.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CutCommand implements CommandExecutor {

    private Miniaturise main;

    public CutCommand(Miniaturise main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission("miniaturise.use")) {
                Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
                    if (main.getRegionManager().hasRegion(player.getUniqueId())) {
                        int blockAmount = 0;
                        for (Block block : main.getRegionManager().getRegion(player.getUniqueId()).getBlocks()) {
                            blockAmount++;
                            block.setType(Material.AIR);
                        }
                        player.sendMessage(Util.PREFIX + "§aThe selected region was cut from the world §e(" +
                                blockAmount + " block" + (blockAmount == 1 ? "" : "s") + ")");
                    } else {
                        player.sendMessage(Util.PREFIX + "§cPlease select a region first");
                    }
                });
            }
        }
        return false;
    }
}
