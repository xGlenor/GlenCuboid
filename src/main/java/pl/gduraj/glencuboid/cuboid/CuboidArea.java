package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import pl.gduraj.glencuboid.util.ChunkRef;

import java.util.ArrayList;
import java.util.List;

public class CuboidArea {

    private Vector highPoints;
    private Vector lowPoints;

    private Location centerLocation;
    private String worldName;
    private World world;

    public CuboidArea(Location centerLoc, int radius, int height) {
        int lowx = (int) (centerLoc.getX() - radius);
        int highx = (int) (centerLoc.getX() + radius);
        int lowz = (int) (centerLoc.getZ() - radius);
        int highz = (int) (centerLoc.getZ() + radius);
        int lowy = (int) (centerLoc.getY() - radius);
        int highy = (int) (centerLoc.getY() + radius);

        if (height > 0) {
            lowy = (int) (centerLoc.getY() - ((height - 1) / 2));
            highy = (int) (centerLoc.getY() + ((height - 1) / 2));
        }
        this.highPoints = new Vector(highx, highy, highz);
        this.lowPoints = new Vector(lowx, lowy, lowz);

        this.world = (centerLoc.getWorld() != null) ? centerLoc.getWorld() : ((centerLoc.getWorld() != null) ? centerLoc.getWorld() : null);
        this.worldName = (this.world != null) ? this.world.getName() : null;

        this.centerLocation = centerLoc;
    }

    public CuboidArea(Location startLoc, Location endLoc) {
        int highx, highy, highz, lowx, lowy, lowz;
        if (startLoc == null || endLoc == null)
            return;
        if (startLoc.getBlockX() > endLoc.getBlockX()) {
            highx = startLoc.getBlockX();
            lowx = endLoc.getBlockX();
        } else {
            highx = endLoc.getBlockX();
            lowx = startLoc.getBlockX();
        }
        if (startLoc.getBlockY() > endLoc.getBlockY()) {
            highy = startLoc.getBlockY();
            lowy = endLoc.getBlockY();
        } else {
            highy = endLoc.getBlockY();
            lowy = startLoc.getBlockY();
        }
        if (startLoc.getBlockZ() > endLoc.getBlockZ()) {
            highz = startLoc.getBlockZ();
            lowz = endLoc.getBlockZ();
        } else {
            highz = endLoc.getBlockZ();
            lowz = startLoc.getBlockZ();
        }
        this.highPoints = new Vector(highx, highy, highz);
        this.lowPoints = new Vector(lowx, lowy, lowz);
        this.world = (startLoc.getWorld() != null) ? startLoc.getWorld() : ((startLoc.getWorld() != null) ? startLoc.getWorld() : null);
        this.worldName = (this.world != null) ? this.world.getName() : null;
    }

    public CuboidArea() {
    }

    public boolean isAreaWithinArea(CuboidArea area) {
        return (containsLoc(area.highPoints, area.getWorldName()) && containsLoc(area.lowPoints, area.getWorldName()));
    }

    public boolean containsLoc(Location loc) {
        return containsLoc(loc.toVector(), loc.getWorld().getName());
    }

    public boolean containsLoc(Vector loc, String world) {
        if (loc == null)
            return false;
        if (!world.equals(this.worldName))
            return false;
        if (this.lowPoints.getBlockX() > loc.getBlockX())
            return false;
        if (this.highPoints.getBlockX() < loc.getBlockX())
            return false;
        if (this.lowPoints.getBlockZ() > loc.getBlockZ())
            return false;
        if (this.highPoints.getBlockZ() < loc.getBlockZ())
            return false;
        if (this.lowPoints.getBlockY() > loc.getBlockY())
            return false;
        return this.highPoints.getBlockY() >= loc.getBlockY();
    }

    public boolean checkCollision(CuboidArea area) {
        if (!area.getWorld().equals(getWorld()))
            return false;
        if (area.containsLoc(this.lowPoints, getWorldName()) || area.containsLoc(this.highPoints, getWorldName()) || containsLoc(area.highPoints, getWorldName()) || containsLoc(area.lowPoints,
                getWorldName()))
            return true;
        return advCuboidCheckCollision(this.highPoints, this.lowPoints, area.highPoints, area.lowPoints);
    }

    private static boolean advCuboidCheckCollision(Vector A1High, Vector A1Low, Vector A2High, Vector A2Low) {
        int A1HX = A1High.getBlockX();
        int A1LX = A1Low.getBlockX();
        int A2HX = A2High.getBlockX();
        int A2LX = A2Low.getBlockX();
        if ((A1HX >= A2LX && A1HX <= A2HX) || (A1LX >= A2LX && A1LX <= A2HX) || (A2HX >= A1LX && A2HX <= A1HX) || (A2LX >= A1LX && A2LX <= A1HX)) {
            int A1HY = A1High.getBlockY();
            int A1LY = A1Low.getBlockY();
            int A2HY = A2High.getBlockY();
            int A2LY = A2Low.getBlockY();
            if ((A1HY >= A2LY && A1HY <= A2HY) || (A1LY >= A2LY && A1LY <= A2HY) || (A2HY >= A1LY && A2HY <= A1HY) || (A2LY >= A1LY && A2LY <= A1HY)) {
                int A1HZ = A1High.getBlockZ();
                int A1LZ = A1Low.getBlockZ();
                int A2HZ = A2High.getBlockZ();
                int A2LZ = A2Low.getBlockZ();
                return (A1HZ >= A2LZ && A1HZ <= A2HZ) || (A1LZ >= A2LZ && A1LZ <= A2HZ) || (A2HZ >= A1LZ && A2HZ <= A1HZ) || (A2LZ >= A1LZ && A2LZ <= A1HZ);
            }
        }
        return false;
    }

    public List<ChunkRef> getChunks() {
        List<ChunkRef> chunks = new ArrayList<>();
        Vector high = this.highPoints;
        Vector low = this.lowPoints;
        int lowX = ChunkRef.getChunkCoord(low.getBlockX());
        int lowZ = ChunkRef.getChunkCoord(low.getBlockZ());
        int highX = ChunkRef.getChunkCoord(high.getBlockX());
        int highZ = ChunkRef.getChunkCoord(high.getBlockZ());
        for (int x = lowX; x <= highX; x++) {
            for (int z = lowZ; z <= highZ; z++)
                chunks.add(new ChunkRef(x, z));
        }
        return chunks;
    }

    public CuboidArea getAreaByLoc(Location location) {
        if (this.containsLoc(location))
            return this;
        return null;
    }

    public Vector getHighPoints() {
        return highPoints;
    }

    public void setHighPoints(Vector highPoints) {
        this.highPoints = highPoints;
    }

    public Vector getLowPoints() {
        return lowPoints;
    }

    public void setLowPoints(Vector lowPoints) {
        this.lowPoints = lowPoints;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Location getCenterLocation() {
        return centerLocation;
    }

    public void setCenterLocation(Location centerLocation) {
        this.centerLocation = centerLocation;
    }
}
