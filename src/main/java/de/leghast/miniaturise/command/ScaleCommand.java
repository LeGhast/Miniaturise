package de.leghast.miniaturise.command;

import de.leghast.miniaturise.Miniaturise;
import de.leghast.miniaturise.instance.miniature.Miniature;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScaleCommand implements CommandExecutor {

    private Miniaturise main;

    public ScaleCommand(Miniaturise main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(args.length >= 1 && (args[0].equalsIgnoreCase("selection") || args[0].equalsIgnoreCase("s"))){
                if(args.length == 2){
                    if(main.getMiniatureManager().hasMiniature(player.getUniqueId())){
                        try{
                            Miniature miniature = main.getMiniatureManager().getMiniature(player.getUniqueId());
                            miniature.scale(Double.parseDouble(args[1]));
                            player.sendMessage(main.PREFIX + "§aThe selected miniature was scaled to §e" + miniature.getSize() + " §ablocks");
                            return true;
                        }catch (NumberFormatException e){
                            player.sendMessage(main.PREFIX + "§cPlease provide a valid scale factor");
                            return false;
                        }
                    }else{
                        player.sendMessage(main.PREFIX + "§cPlease create a miniature first");
                    }
                }else{
                    player.sendMessage(main.PREFIX + "§cPlease provide a valid scale factor");
                }
            }else if(args.length >= 1 && (args[0].equalsIgnoreCase("miniature") || args[0].equalsIgnoreCase("m"))){
                if(args.length == 2){
                    try{
                        if(main.getMiniatureManager().hasPlacedMiniature(player.getUniqueId())){
                            main.getMiniatureManager().getPlacedMiniature(player.getUniqueId()).scale(Double.parseDouble(args[1]));
                        }else{
                            player.sendMessage(main.PREFIX + "§cYou have not placed/selected a placed miniature yet");
                        }
                    }catch (NumberFormatException e){
                        player.sendMessage(main.PREFIX + "§cPlease provide a valid scale factor");
                        return false;
                    }
                }else{
                    player.sendMessage(main.PREFIX + "§cPlease provide a valid scale factor");
                }
            }else{
                player.sendMessage(main.PREFIX + "§cPlease specify, what you want to scale");
            }
        }
        return false;
    }
}
