package de.leghast.miniaturise.command;

import de.leghast.miniaturise.Miniaturise;
import de.leghast.miniaturise.instance.miniature.Miniature;
import de.leghast.miniaturise.instance.miniature.PlacedMiniature;
import de.leghast.miniaturise.instance.region.Region;
import de.leghast.miniaturise.manager.ConfigManager;
import de.leghast.miniaturise.util.Util;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectCommand implements CommandExecutor {

    private Miniaturise main;

    public SelectCommand(Miniaturise main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission("miniaturise.use")){
                if(main.getRegionManager().hasSelectedLocations(player.getUniqueId())){
                    if(main.getRegionManager().getSelectedLocations(player.getUniqueId()).isValid()){
                        try{
                            Region region = new Region(main.getRegionManager().getSelectedLocations(player.getUniqueId()));
                            if(main.getRegionManager().hasRegion(player.getUniqueId())) {
                                main.getRegionManager().getRegions().replace(player.getUniqueId(), region);
                            }else{
                                main.getRegionManager().addRegion(player.getUniqueId(), region);
                            }
                            Miniature miniature = new Miniature(region, player.getLocation(), ConfigManager.getDefaultSize());
                            if(miniature.getBlocks().size() >= ConfigManager.getMaxEntityLimit()){
                                player.sendMessage(Util.PREFIX + "§cThe current selection §e(" + miniature.getBlocks().size() +
                                        " blocks) §cexceeds the limit of §e" + ConfigManager.getMaxEntityLimit() + " §cblocks");
                                main.getRegionManager().removeRegion(player.getUniqueId());
                                return false;
                            }
                            if(main.getMiniatureManager().hasMiniature(player.getUniqueId())){
                                main.getMiniatureManager().getMiniatures().replace(player.getUniqueId(), miniature);
                            }else{
                                main.getMiniatureManager().addMiniature(player.getUniqueId(), miniature);
                            }

                            List<BlockDisplay> blockDisplays = Util.getBlockDisplaysFromRegion(player, region);

                            if(!blockDisplays.isEmpty()){
                                if(main.getMiniatureManager().hasPlacedMiniature(player.getUniqueId())){
                                    if(main.getMiniatureManager().getPlacedMiniature(player.getUniqueId()) != null){

                                    }
                                    main.getMiniatureManager().getPlacedMiniatures().replace(player.getUniqueId(), new PlacedMiniature(blockDisplays));
                                }else{
                                    main.getMiniatureManager().addPlacedMiniature(player.getUniqueId(), new PlacedMiniature(blockDisplays));
                                }
                            }
                            player.sendMessage(Util.PREFIX + "§aThe region was selected §e(" + miniature.getBlocks().size() +
                                    " block" + (miniature.getBlocks().size() == 1 ? "" : "s") + ")");
                            return true;
                        }catch(IllegalArgumentException e){
                            player.sendMessage(Util.PREFIX + "§c" + e.getMessage());
                            return false;
                        }
                    }else{
                        player.sendMessage(Util.PREFIX + "§cPlease select two locations");
                    }
                }else{
                    player.sendMessage(Util.PREFIX + "§cYou have not selected any locations");
                }
            }
        }
        return false;
    }
}
