package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.gduraj.glencuboid.cuboid.team.CuboidTeam;

import java.util.UUID;

public class Cuboid {

    private String name;

    protected CuboidArea area;
    protected CuboidFlag flags;
    protected CuboidTeam team;
    //protected FlagSetiings flags;
    //protected Roles role;

    protected long createTime;

    public Cuboid(Player owner, String cuboidName, Location centerLoc, int radius, int height) {
        this.team = new CuboidTeam(owner.getUniqueId(), owner.getName().toLowerCase(), this);
        this.name = cuboidName;
        this.area = new CuboidArea(centerLoc, radius, height);
        this.flags = new CuboidFlag(this);
    }

    public Cuboid(UUID ownerUUID, String owner, String cuboidName, CuboidArea area) {
        this.team = new CuboidTeam(ownerUUID, owner, this);
        this.name = cuboidName;
        this.area = area;
        this.flags = new CuboidFlag(this);
    }

    public Cuboid(Player player, String cuboidName, CuboidArea area) {
        this.team = new CuboidTeam(player.getUniqueId(), player.getName().toLowerCase(), this);
        this.name = cuboidName;
        this.area = area;
        this.flags = new CuboidFlag(this);
    }

    private Cuboid() {
    }

    public void addArea(CuboidArea area) {

    }

    public boolean checkCollision(CuboidArea area) {
        return this.area.checkCollision(area);
    }

    public boolean containsLoc(Location loc) {
        return this.area.containsLoc(loc);
    }

    public boolean containsLoc(Vector vector, String world) {
        return this.area.containsLoc(vector, world);
    }

    public CuboidArea getAreaByLoc(Location loc) {
        return this.area.getAreaByLoc(loc);
    }

    public void tpToCuboid(Player owner, Player targetPlayer) {
        targetPlayer.teleport(this.area.getCenterLocation());
        owner.sendMessage("Teleportacja gracza " + targetPlayer.getName().toLowerCase() + " na srodek dzialki");
        owner.sendMessage("Teleportowano cie na dzialke: " + owner.getName().toLowerCase() + " | " + getName());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CuboidFlag getFlags() {
        return flags;
    }

    public void setFlags(CuboidFlag flags) {
        this.flags = flags;
    }

    public CuboidTeam getTeam() {
        return team;
    }

    public void setTeam(CuboidTeam team) {
        this.team = team;
    }

    public boolean isOwner(String name) {
        return getTeam().isOwner(name);
    }

    public boolean isOwner(UUID uuid) {
        return getTeam().isOwner(uuid);
    }

    public boolean isOwner(Player player) {
        return getTeam().isOwner(player);
    }

    public boolean isOwner(CommandSender sender) {
        return getTeam().isOwner(sender);
    }

}
