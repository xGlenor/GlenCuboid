package pl.gduraj.glencuboid.config;

import org.bukkit.configuration.file.FileConfiguration;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Flag;

import java.util.HashMap;

public class ConfigManager {

    private GlenCuboid plugin;
    private HashMap<String, ConfigHandler> configs;

    public ConfigManager(){
        this.plugin = GlenCuboid.getInstance();
        this.configs = new HashMap<>();
    }

    public void install(){
        configs.put("config", new ConfigHandler(plugin, "config"));
        configs.put("messages", new ConfigHandler(plugin, "messages"));
        configs.put("flags", new ConfigHandler(plugin, "flags"));
        configs.put("groups", new ConfigHandler(plugin, "groups"));

        configs.values().forEach(ConfigHandler::createDefault);

    }

    public void reloadAll(){
        configs.values().forEach(ConfigHandler::reload);
    }

    public void saveConfig(String config) {
        configs.get(config.toLowerCase()).saveConfig();
    }

    public FileConfiguration getConfig(String config){
        if(configs.containsKey(config.toLowerCase()))
            return configs.get(config.toLowerCase()).getConfig();
        else
            return configs.get("config").getConfig();
    }

}
