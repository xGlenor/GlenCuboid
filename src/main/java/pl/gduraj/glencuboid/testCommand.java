package pl.gduraj.glencuboid;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.cuboid.Flag;
import pl.gduraj.glencuboid.util.ChatUtil;

public class testCommand implements CommandExecutor {

    private GlenCuboid plugin;

    public testCommand(){
        this.plugin = GlenCuboid.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length > 0) {
                if (args[0].equalsIgnoreCase("test")) {
                    System.out.println("COSIK: " + plugin.getCuboidManager().getCuboids());
                    return true;
                } else if(args[0].equalsIgnoreCase("flag")) {
                    Flag flag1 = Flag.getFlag(args[1].toUpperCase());
                    if(flag1 != null){
                        Cuboid cub = plugin.getCuboidManager().getByLoc(player);
                        if(cub.getFlags().hasFlag(flag1))
                            cub.getFlags().addDisabledFlag(flag1);
                        else
                            cub.getFlags().removeDisabledFlag(flag1);

                        ChatUtil.sendMSGColor(player, "<green> Pomyślnie zmieniono flagę");
                        plugin.getCuboidManager().updateCuboid(cub);
                        return true;
                    }
                    ChatUtil.sendMSGColor(player, "<red>Nie ma takiej flagi");
                    return true;

                } else if(args[0].equalsIgnoreCase("owner")) {
                    String ownerNew = args[1];

                    Player player1 = Bukkit.getPlayer(ownerNew);
                    Cuboid cub = plugin.getCuboidManager().getByLoc(player);
                    if (cub != null) {
                        if (player1 == null) {
                            OfflinePlayer ofPlayer = Bukkit.getOfflinePlayer(ownerNew);
                            cub.setOwner(ofPlayer.getName().toLowerCase());
                            cub.setOwnerUUID(ofPlayer.getUniqueId());
                            plugin.getCuboidManager().updateCuboid(cub);
                            ChatUtil.sendMSGColor(player, "<green>Zmieniono właściciela na: " + ofPlayer.getName());
                            return true;
                        }

                        cub.setOwner(player1.getName().toLowerCase());
                        cub.setOwnerUUID(player1.getUniqueId());
                        plugin.getCuboidManager().updateCuboid(cub);
                        ChatUtil.sendMSGColor(player, "<green>Zmieniono właściciela na: " + player1.getName());
                        return true;
                    }
                }else if(args[0].equalsIgnoreCase("check")){
                    Cuboid cub = plugin.getCuboidManager().getByLoc(player);
                    if(cub != null){
                        player.sendMessage(cub.getName());
                        player.sendMessage(cub.getOwner());
                        player.sendMessage(cub.getOwnerUUID());
                        player.sendMessage("FLAGS:");
                        System.out.println(cub.getFlags().getFlags());
                        System.out.println(cub.getFlags().getDisabledFlags());
                        for(Flag f : cub.getFlags().getFlags()){
                            player.sendMessage(f.toString());
                        }
                        player.sendMessage("DISABLE FLAGS:");
                        for(Flag f : cub.getFlags().getDisabledFlags()){
                            player.sendMessage(f.toString());
                        }

                    }else{
                        player.sendMessage("nie znaleziono");
                    }
                    return true;
                }else if(args[0].equalsIgnoreCase("info")){
                    plugin.getCuboidManager().getCuboids().forEach((s, cuboid) -> {
                        System.out.println(cuboid);
                    });

                    plugin.getCuboidManager().getChunkCuboids().forEach((s, chunkRefListMap) -> {
                        System.out.println( s + " " + chunkRefListMap);
                    });
                    return true;
                }

                plugin.getCuboidManager().add(player, player.getLocation(), getStringFromArray(args));
                return true;
            }

            return true;
        }

        return true;
    }

    private String getStringFromArray(String[] args){
        StringBuilder sb = new StringBuilder();

        for (String s : args)
            sb.append(s).append(' ');
        return sb.toString();
    }
}
