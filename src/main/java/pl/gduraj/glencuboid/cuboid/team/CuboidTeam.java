package pl.gduraj.glencuboid.cuboid.team;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.enums.Flag;

import java.util.*;

public class CuboidTeam {

    private UUID ownerUUID;
    private String owner;

    private final Cuboid cuboid;
    private HashMap<String, String> allowed;

    public CuboidTeam(UUID ownerUUID, String owner, Cuboid cuboid) {
        this.ownerUUID = ownerUUID;
        this.owner = owner;
        this.cuboid = cuboid;
        this.allowed = new HashMap<>();
    }

    public CuboidTeam(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public boolean isAllowed(String name) {
        return allowed.containsValue(name.toLowerCase());
    }

    public void addPlayer(Player player){
        allowed.put("MEMBER", player.getName().toLowerCase());
    }

    public boolean isFlaginRole(String name, Flag flag){
        if(name == null || flag == null) return false;
        return
    }


    public String getRolePlayer(Player player) {
        return getRolePlayer(player.getName());
    }

    public String getRolePlayer(String name) {
        if (allowed.containsValue(name.toLowerCase())) {
            for (Map.Entry<String, String> p : allowed.entrySet()) {
                if (p.getValue().equalsIgnoreCase(name))
                    return p.getKey().toUpperCase();
            }
        }
        return null;
    }

    public List<String> getPlayersByRole(String role) {
        if (allowed == null) return null;

        List<String> players = new ArrayList<>();
        for (Map.Entry<String, String> map : allowed.entrySet()) {
            if (map.getKey().equals(role))
                players.add(map.getValue());
        }
        return players;
    }

    public boolean isOwner(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player != null)
            isOwner(player);
        return this.owner.equals(name.toLowerCase());
    }

    public boolean isOwner(UUID uuid) {
        return this.getOwnerUUID().toString().equals(uuid.toString());
    }

    public boolean isOwner(Player player) {
        if (player == null) return false;
        return this.getOwnerUUID().equals(player.getUniqueId());
    }

    public boolean isOwner(CommandSender sender) {
        if (sender instanceof Player)
            return this.getOwnerUUID().equals(((Player) sender).getUniqueId());
        return false;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public HashMap<String, String> getAllowed() {
        HashMap<String, String> allowedPlayers = new HashMap<>();
        allowedPlayers.putAll(allowed);
        allowedPlayers.put("Owner", owner.toLowerCase());
        return allowed;
    }

    public void setAllowed(HashMap<String, String> allowed) {
        this.allowed = allowed;
    }
}
