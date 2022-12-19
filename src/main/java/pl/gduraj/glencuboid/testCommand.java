package pl.gduraj.glencuboid;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.enums.CuboidRole;
import pl.gduraj.glencuboid.enums.Flag;
import pl.gduraj.glencuboid.util.ChatUtil;

import java.util.Map;

public class testCommand implements CommandExecutor {

    private final GlenCuboid plugin;

    public testCommand() {
        this.plugin = GlenCuboid.getInstance();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("test")) {
                    System.out.println("COSIK: " + plugin.getCuboidManager().getCuboids());
                    return true;
                } else if (args[0].equalsIgnoreCase("flag")) {
                    Flag flag1 = Flag.getFlag(args[1].toUpperCase());
                    if (flag1 != null) {
                        Cuboid cub = plugin.getCuboidManager().getByLoc(player);
                        if (cub.getFlags().hasFlag(flag1))
                            cub.getFlags().addDisabledFlag(flag1);
                        else
                            cub.getFlags().removeDisabledFlag(flag1);

                        ChatUtil.sendMSGColor(player, "<green> Pomyślnie zmieniono flagę");
                        plugin.getCuboidManager().updateCuboid(cub);
                        return true;
                    }
                    ChatUtil.sendMSGColor(player, "<red>Nie ma takiej flagi");
                    return true;

                } else if (args[0].equalsIgnoreCase("owner")) {
                    String ownerNew = args[1];

                    Player player1 = Bukkit.getPlayer(ownerNew);
                    Cuboid cub = plugin.getCuboidManager().getByLoc(player);
                    if (cub != null) {
                        if (player1 == null) {
                            OfflinePlayer ofPlayer = Bukkit.getOfflinePlayer(ownerNew);
                            cub.getTeam().setOwner(ofPlayer.getName().toLowerCase());
                            cub.getTeam().setOwnerUUID(ofPlayer.getUniqueId());
                            plugin.getCuboidManager().updateCuboid(cub);
                            ChatUtil.sendMSGColor(player, "<green>Zmieniono właściciela na: " + ofPlayer.getName());
                            return true;
                        }

                        cub.getTeam().setOwner(player1.getName().toLowerCase());
                        cub.getTeam().setOwnerUUID(player1.getUniqueId());
                        plugin.getCuboidManager().updateCuboid(cub);
                        ChatUtil.sendMSGColor(player, "<green>Zmieniono właściciela na: " + player1.getName());
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("allow")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Cuboid cub = plugin.getCuboidManager().getEnabledSource(player.getLocation(), Flag.ALL);

                    if (cub != null) {
                        if (target == null) {
                            target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
                        }
                        if (cub.getTeam().addPlayer(target)) {
                            player.sendMessage("Gracz zostal dodany do dzialki ez");
                        } else {
                            player.sendMessage("Nie zostal dodany do dzialki");
                        }
                        plugin.getStorageManager().offerCuboid(cub);
                        return true;

                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("setRole")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    Cuboid cub = plugin.getCuboidManager().getEnabledSource(player.getLocation(), Flag.ALL);

                    if (cub != null) {
                        if (target == null) {
                            target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
                        }
                        if (cub.getTeam().setRole(target.getName().toLowerCase(), args[2])) {
                            player.sendMessage("Nadano role");
                        } else {
                            player.sendMessage("nie nadano");
                        }
                        return true;

                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("check")) {
                    Cuboid cub = plugin.getCuboidManager().getByLoc(player);
                    if (cub != null) {
                        player.sendMessage(cub.getName());
                        player.sendMessage(cub.getTeam().getOwner());
                        player.sendMessage(cub.getTeam().getOwnerUUID());
                        player.sendMessage("FLAGS:");
                        System.out.println(cub.getFlags().getFlags());
                        System.out.println(cub.getFlags().getDisabledFlags());
                        for (Flag f : cub.getFlags().getFlags()) {
                            player.sendMessage(f.toString());
                        }
                        player.sendMessage("\nDISABLE FLAGS:");
                        for (Flag f : cub.getFlags().getDisabledFlags()) {
                            player.sendMessage(f.toString());
                        }

                        player.sendMessage("\nAllowed");
                        for (Map.Entry<String, CuboidRole> allowed : cub.getTeam().getAllowed().entrySet()) {
                            ChatUtil.sendMSGColor(player, String.format("<red>%s <gray>: <green>%s", allowed.getKey(), allowed.getValue()));
                        }

                    } else {
                        player.sendMessage("nie znaleziono");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    plugin.getCuboidManager().getCuboids().forEach((s, cuboid) -> {
                        System.out.println(cuboid);
                    });

                    plugin.getCuboidManager().getChunkCuboids().forEach((s, chunkRefListMap) -> {
                        System.out.println(s + " " + chunkRefListMap);
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

    private String getStringFromArray(String[] args) {
        StringBuilder sb = new StringBuilder();

        for (String s : args)
            sb.append(s).append(' ');
        return sb.toString();
    }
}
