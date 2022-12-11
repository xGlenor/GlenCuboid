package pl.gduraj.glencuboid.cuboid.team;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.yaml.snakeyaml.util.EnumUtils;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.enums.Flag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManager {

    private final GlenCuboid plugin;
    private final HashMap<CuboidRole, List<String>> flagsbyRole;
    private final List<String> displayFlags;
    private final FileConfiguration config;

    public TeamManager() {
        this.plugin = GlenCuboid.getInstance();
        this.displayFlags = new ArrayList<>();
        this.flagsbyRole = new HashMap<>();
        this.config = plugin.getConfigManager().getConfig("roles");

        if (config == null)
            GlenCuboid.getMessageLoaded().add("TeamManager nie moze zostać zaladowany!");

        loadRoles();
        loadDisplayFlags();

    }

    private void loadRoles() {
        for(CuboidRole role : CuboidRole.values()){
            List<String> flags = new ArrayList<>();
            for(String s : config.getStringList(role.getPath() + ".flags")){
                flags.add(s.toUpperCase());
            }
            flagsbyRole.put(role, flags);
        }
    }


    private void loadDisplayFlags() {
        for (String s : config.getStringList("displayFlags")) {
            displayFlags.add(s.toUpperCase());
        }

        GlenCuboid.getMessageLoaded().add("TeamManager -> Zaladowano " + displayFlags.size() + " flag do wyświetlania");
    }

    public List<String> getFlagsRole(String role) {
        if (!isRole(role)) return null;
        return flagsbyRole.get(CuboidRole.valueOf(role));
    }

    public boolean isFlagInRole(CuboidRole role, Flag flag){
        return isFlagInRole(role.toString(), flag);
    }

    public boolean isFlagInRole(String role, Flag flag) {
        if (isRole(role)) {
            return getFlagsRole(role).contains(flag.toString());
        }
        return false;
    }

    public boolean isRole(String role) {
        return CuboidRole.isRole(role);
    }

    public HashMap<CuboidRole, List<String>> getFlagsbyRole() {
        return flagsbyRole;
    }

    public List<String> getDisplayFlags() {
        return displayFlags;
    }

    public GlenCuboid getPlugin() {
        return plugin;
    }
}
