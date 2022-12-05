package pl.gduraj.glencuboid.util;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.gduraj.glencuboid.GlenCuboid;

import java.util.List;

public class ChatUtil {

    private static final LegacyComponentSerializer serializer = BukkitComponentSerializer.legacy();
    private static final MiniMessage mm = GlenCuboid.getInstance().getMiniMessage();


    public static void sendMSGColor(CommandSender sender, String message) {
        MiniMessage miniMessage = GlenCuboid.getInstance().getMiniMessage();
        Component msg = miniMessage.deserialize(message);
        GlenCuboid.getInstance().getMiniMessageManager().getAudiences().sender(sender).sendMessage(msg);
    }

    public static void sendMSGColor(Player player, String message) {
        MiniMessage miniMessage = GlenCuboid.getInstance().getMiniMessage();
        Component msg = miniMessage.deserialize(message);
        GlenCuboid.getInstance().getMiniMessageManager().getAudiences().player(player).sendMessage(msg);
    }

    public static String strToColor(String name) {
        return serializer.serialize(mm.deserialize(name));
    }

    public static List<String> listToColor(List<String> lores) {
        for (int i = 0; i < lores.size(); i++) {
            lores.set(i, serializer.serialize(mm.deserialize(lores.get(i))));
        }
        return lores;
    }

}
