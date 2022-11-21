package pl.gduraj.glencuboid;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import pl.gduraj.glencuboid.managers.MiniMessageManager;

public final class GlenCuboid extends JavaPlugin {

    private static GlenCuboid instance;
    private MiniMessageManager miniMessageManager;
    private MiniMessage miniMessage;
    @Override
    public void onEnable() {
        instance = this;
        miniMessageManager = new MiniMessageManager();
        miniMessageManager.enableMiniMessage();


    }

    @Override
    public void onDisable() {
        miniMessageManager.disableMiniMessage();
    }

    public static GlenCuboid getInstance() {
        return instance;
    }

    public MiniMessage getMiniMessage() {
        return miniMessageManager.getMiniMessage();
    }

    public void disablePlugin(){
        getServer().getPluginManager().disablePlugin(this);
    }
}
