package pl.gduraj.glencuboid.util;

import org.bukkit.Location;

public class ChunkRef {
    private final int z;

    private final int x;

    public static int getChunkCoord(int val) {
        return val >> 4;
    }

    public ChunkRef(Location loc) {
        this.x = getChunkCoord(loc.getBlockX());
        this.z = getChunkCoord(loc.getBlockZ());
    }

    public ChunkRef(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChunkRef other = (ChunkRef) obj;
        return (this.x == other.x && this.z == other.z);
    }

    public int hashCode() {
        return this.x ^ this.z;
    }

    public String toString() {
        return "{ x: " + this.x + ", z: " + this.z + " }";
    }

    public int getZ() {
        return this.z;
    }

    public int getX() {
        return this.x;
    }
}
