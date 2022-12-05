package pl.gduraj.glencuboid.managers;

import org.bukkit.plugin.PluginManager;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.listeners.UseFlagListener;

public class ListenersManager {

    private final GlenCuboid plugin;
    private final PluginManager pm;


    public ListenersManager() {
        this.plugin = GlenCuboid.getInstance();
        this.pm = plugin.getServer().getPluginManager();

        registerEvent();
    }

    private void registerEvent() {
        pm.registerEvents(new UseFlagListener(), plugin);
    }


}
