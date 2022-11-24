package pl.gduraj.glencuboid;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentApplicable;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.util.ItemBuilder;

public class testCommand implements CommandExecutor {

    private GlenCuboid plugin;

    public testCommand(){
        this.plugin = GlenCuboid.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length > 0){
                if(args[0].equalsIgnoreCase("test")){
                    System.out.println("COSIK: " + plugin.getCuboidManager().getCuboids());
                    return true;
                }else if(args[0].equalsIgnoreCase("check")){
                    String name = args[1].split(" ")[0];
                    Cuboid cub = plugin.getCuboidManager().getByName(name);
                    if(cub != null){
                        player.sendMessage(cub.getName());
                        player.sendMessage(cub.getOwner());
                        player.sendMessage(cub.getOwnerUUID());
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
