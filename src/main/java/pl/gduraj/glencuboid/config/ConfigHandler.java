package pl.gduraj.glencuboid.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Flag;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    private GlenCuboid plugin;
    private String name;
    private FileConfiguration config;
    private File file;

    public ConfigHandler(GlenCuboid plugin, String name){
        this.plugin = plugin;
        this.name = name + ".yml";
        this.file = new File(plugin.getDataFolder(), this.name);
        this.config = new YamlConfiguration();
    }

    public void createDefault() {
        if(!file.exists()){
            plugin.saveResource(name, false);
        }

        try {
            config.load(file);
            if(name.equalsIgnoreCase("flags.yml")){
                Flag.loadFLAG();
            }

        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            plugin.getLogger().warning("Error create file: " + name);
        }
    }

    public void saveConfig(){
        if(config == null || file == null) return;

        try{
            getConfig().save(file);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile(){
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
