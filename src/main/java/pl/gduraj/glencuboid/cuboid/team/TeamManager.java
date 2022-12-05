package pl.gduraj.glencuboid.cuboid.team;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.enums.Flag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManager {

    private final GlenCuboid plugin;
    private final List<String> roles;
    private final HashMap<String, List<String>> flagsByRole;
    private final List<String> displayFlags;
    private final FileConfiguration config;

    public TeamManager() {
        this.plugin = GlenCuboid.getInstance();
        this.roles = new ArrayList<>();
        this.displayFlags = new ArrayList<>();
        this.flagsByRole = new HashMap<>();
        this.config = plugin.getConfigManager().getConfig("roles");

        if (config == null)
            GlenCuboid.getMessageLoaded().add("TeamManager nie moze zostać zaladowany!");

        loadRoles();
        loadDisplayFlags();

    }

    private void loadRoles() {
        ConfigurationSection cs = config.getConfigurationSection("roles");
        for (String s : cs.getKeys(false)) {
            roles.add(s.toUpperCase());
            flagsByRole.put(s, cs.getStringList(s + ".flags"));
        }

        for(Map.Entry<String, List<String>> map : flagsByRole.entrySet()){
            System.out.println(map.getKey() + " -> " + map.getValue());
        }

        GlenCuboid.getMessageLoaded().add("TeamManager -> Zaladowano " + roles.size() + " flag do wyświetlania");
    }


    private void loadDisplayFlags() {
        for (String s : config.getStringList("display")) {
            displayFlags.add(s.toUpperCase());
        }

        GlenCuboid.getMessageLoaded().add("TeamManager -> Zaladowano " + displayFlags.size() + " flag do wyświetlania");
    }

    public List<String> getFlagsRole(String role){
        if(!isRole(role)) return null;
        return flagsByRole.get(role.toUpperCase());
    }

    public boolean isFlagInRole(String role, Flag flag){
        if(isRole(role)){
            return getFlagsRole(role).contains(flag.toString());
        }
        return false;
    }

    public boolean isRole(String val) {
        return roles.contains(val.toUpperCase());
    }

    public List<String> getRoles() {
        return roles;
    }

    public HashMap<String, List<String>> getFlagsByRole() {
        return flagsByRole;
    }

    public List<String> getDisplayFlags() {
        return displayFlags;
    }

    public GlenCuboid getPlugin() {
        return plugin;
    }
}
