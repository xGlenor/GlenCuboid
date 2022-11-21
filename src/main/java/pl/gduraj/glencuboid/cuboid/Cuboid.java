package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Cuboid {

    private String name;
    private UUID ownerUUID;
    private String owner;

    protected CuboidArea area;
    //protected FlagSetiings flags;
    //protected Roles role;

    protected long createTime;

    public Cuboid(Player owner, String cuboidName, Location centerLoc, int radius, int height){
        this.ownerUUID = owner.getUniqueId();
        this.owner = owner.getName().toLowerCase();
        this.name = cuboidName;
        this.area = new CuboidArea(centerLoc, radius, height);
    }

    public Cuboid(UUID ownerUUID, String owner, String cuboidName, CuboidArea area){
        this.ownerUUID = ownerUUID;
        this.owner = owner.toLowerCase();
        this.name = cuboidName;
        this.area = area;
    }

    public Cuboid(Player player, String cuboidName, CuboidArea area){
        this.ownerUUID = player.getUniqueId();
        this.owner = player.getName().toLowerCase();
        this.name = cuboidName;
        this.area = area;
    }

    public void addArea(CuboidArea area){

    }

    public boolean checkCollision(CuboidArea area) {
        return this.area.checkCollision(area);
    }

    public boolean containsLoc(Location loc){
        return this.area.containsLoc(loc);
    }

    public boolean containsLoc(Vector vector, String world){
        return this.area.containsLoc(vector, world);
    }

    public CuboidArea getAreaByLoc(Location loc){
        return this.area.getAreaByLoc(loc);
    }

    public void tpToCuboid(Player owner, Player targetPlayer){
        targetPlayer.teleport(this.area.getCenterLocation());
        owner.sendMessage("Teleportacja gracza " + targetPlayer.getName().toLowerCase()  + " na srodek dzialki");
        owner.sendMessage("Teleportowano cie na dzialke: " + owner.getName().toLowerCase() + " | " + getName());
    }

    private Cuboid(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CuboidArea getArea() {
        return area;
    }

    public void setArea(CuboidArea area) {
        this.area = area;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isOwner(String name){
        Player player = Bukkit.getPlayer(name);
        if(player != null)
            isOwner(player);
        return this.owner.equals(name.toLowerCase());
    }

    public boolean isOwner(UUID uuid){
        return this.getOwnerUUID().toString().equals(uuid.toString());
    }

    public boolean isOwner(Player player){
        if(player == null) return false;
        return this.getOwnerUUID().equals(player.getUniqueId());
    }

    public boolean isOwner(CommandSender sender){
        if(sender instanceof Player)
            return this.getOwnerUUID().equals(((Player) sender).getUniqueId());
        return false;
    }

}
