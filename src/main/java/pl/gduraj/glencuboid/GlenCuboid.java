package pl.gduraj.glencuboid;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.gduraj.glencuboid.config.ConfigManager;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.cuboid.CuboidManager;
import pl.gduraj.glencuboid.managers.MiniMessageManager;
import pl.gduraj.glencuboid.storage.StorageManager;

import java.sql.SQLException;

public final class GlenCuboid extends JavaPlugin implements Listener {

    private static GlenCuboid instance;
    private MiniMessageManager miniMessageManager;
    private ConfigManager configManager;
    private StorageManager storageManager;
    private CuboidManager cuboidManager;
    private MiniMessage miniMessage;
    @Override
    public void onEnable() {
        instance = this;

        configManager = new ConfigManager();
        configManager.install();

        storageManager = new StorageManager();
        miniMessageManager = new MiniMessageManager();
        miniMessageManager.enableMiniMessage();

        cuboidManager = new CuboidManager();
        cuboidManager.load();

        getServer().getPluginManager().registerEvents(this, this);

        getCommand("test").setExecutor(new testCommand());

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

    public static GlenCuboid getInstance() {
        return instance;
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

    public void disablePlugin(){
        getServer().getPluginManager().disablePlugin(this);
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public CuboidManager getCuboidManager() {
        return cuboidManager;
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent ev){
        if(ev.isCancelled()) return;

        Cuboid cub = getCuboidManager().getByLoc(ev.getBlock().getLocation());
        if(cub != null){
            if(!cub.isOwner(ev.getPlayer())){
                ev.setBuild(false);
                ev.setCancelled(true);
                ev.getPlayer().sendMessage("Nie mozesz tu stawiac blokow");
            }
        }

    }

}
