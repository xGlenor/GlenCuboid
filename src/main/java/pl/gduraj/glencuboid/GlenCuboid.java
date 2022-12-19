package pl.gduraj.glencuboid;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.gduraj.glencuboid.config.ConfigManager;
import pl.gduraj.glencuboid.cuboid.CuboidManager;
import pl.gduraj.glencuboid.cuboid.team.TeamManager;
import pl.gduraj.glencuboid.listeners.UseFlagListener;
import pl.gduraj.glencuboid.managers.MiniMessageManager;
import pl.gduraj.glencuboid.storage.StorageManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class GlenCuboid extends JavaPlugin implements Listener {

    private static GlenCuboid instance;
    private static List<String> messageLoaded;
    private MiniMessageManager miniMessageManager;
    private ConfigManager configManager;
    private StorageManager storageManager;
    private CuboidManager cuboidManager;
    private TeamManager teamManager;
    private MiniMessage miniMessage;

    public static GlenCuboid getInstance() {
        return instance;
    }

    public static List<String> getMessageLoaded() {
        return messageLoaded;
    }

    @Override
    public void onEnable() {
        instance = this;
        messageLoaded = new ArrayList<>();

        configManager = new ConfigManager();
        configManager.install();

        storageManager = new StorageManager();
        miniMessageManager = new MiniMessageManager();
        miniMessageManager.enableMiniMessage();

        cuboidManager = new CuboidManager();
        cuboidManager.load();

        teamManager = new TeamManager();


        getServer().getPluginManager().registerEvents(this, this);

        getCommand("test").setExecutor(new testCommand());

        getServer().getPluginManager().registerEvents(new UseFlagListener(), this);

        messageLoaded.forEach(s -> {
            getServer().getConsoleSender().sendMessage(ChatColor.GREEN + s);
        });

    }

    @Override
    public void onDisable() {
        miniMessageManager.disableMiniMessage();
        try {
            getStorageManager().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MiniMessage getMiniMessage() {
        return miniMessageManager.getMiniMessage();
    }

    public MiniMessageManager getMiniMessageManager() {
        return miniMessageManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void disablePlugin() {
        getServer().getPluginManager().disablePlugin(this);
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public CuboidManager getCuboidManager() {
        return cuboidManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }
}
