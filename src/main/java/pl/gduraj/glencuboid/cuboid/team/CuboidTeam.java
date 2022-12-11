package pl.gduraj.glencuboid.cuboid.team;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.enums.Flag;

import java.util.*;

public class CuboidTeam {

    private UUID ownerUUID;
    private String owner;

    private final Cuboid cuboid;
    private HashMap<String, CuboidRole> allowed;

    public CuboidTeam(UUID ownerUUID, String owner, Cuboid cuboid) {
        this.ownerUUID = ownerUUID;
        this.owner = owner;
        this.cuboid = cuboid;
        this.allowed = new HashMap<>();
    }

    public CuboidTeam(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public boolean isAllowed(Player player){
        return isAllowed(player.getName().toLowerCase());
    }

    public boolean isAllowed(String name) {
        return getAllowed().containsValue(name.toLowerCase());
    }

    public boolean addPlayer(Player player) {
        return addPlayer(player.getName().toLowerCase());
    }

    public boolean addPlayer(String player){
        if(!allowed.containsKey(player)){
            allowed.put(player.toLowerCase(), CuboidRole.MEMBER);
            return true;
        }
        return false;
    }

    public boolean removePlayer(Player player){
        return removePlayer(player.getName().toLowerCase());
    }

    public boolean removePlayer(String player){
        if(allowed.containsKey(player)){
            allowed.remove(player.toLowerCase());
            return true;
        }
        return false;
    }

    public CuboidRole getRolePlayer(Player player) {
        return getRolePlayer(player.getName());
    }

    public CuboidRole getRolePlayer(String name) {
        if (allowed.containsKey(name.toLowerCase())) {
            for (Map.Entry<String, CuboidRole> p : allowed.entrySet()) {
                if (p.getKey().equalsIgnoreCase(name))
                    return p.getValue();
            }
        }
        return null;
    }

    public List<String> getPlayersByRole(CuboidRole role) {
        if (allowed == null) return null;

        List<String> players = new ArrayList<>();
        for (Map.Entry<String, CuboidRole> map : allowed.entrySet()) {
            if (map.getValue().equals(role))
                players.add(map.getKey());
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

    public HashMap<String, CuboidRole> getAllowed() {
        HashMap<String, CuboidRole> allowedPlayers = new HashMap<>();
        allowedPlayers.putAll(allowed);
        allowedPlayers.put(owner.toLowerCase(), CuboidRole.OWNER);
        return allowed;
    }

    public void setAllowed(HashMap<String, CuboidRole> allowed) {
        this.allowed = allowed;
    }

    public void setTeam(String jsonSTR) {
        if(allowed == null) allowed = new HashMap<>();
        else allowed.clear();

        JSONObject obj = (JSONObject) JSONValue.parse(jsonSTR);

        if (obj != null) {
            for(Object role : obj.keySet()){
                JSONArray players = (JSONArray) obj.get(role);

                if(players != null)
                    for(Object player : players)
                        allowed.put((String) player, CuboidRole.valueOf((String) role));
            }
        }
    }

}
