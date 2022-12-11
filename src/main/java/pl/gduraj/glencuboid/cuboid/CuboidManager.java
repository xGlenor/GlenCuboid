package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.team.CuboidRole;
import pl.gduraj.glencuboid.enums.Flag;
import pl.gduraj.glencuboid.util.ChunkRef;

import java.sql.SQLException;
import java.util.*;

public class CuboidManager {

    private final GlenCuboid plugin;
    protected SortedMap<String, Cuboid> cuboids;
    protected Map<String, Map<ChunkRef, List<Cuboid>>> chunkCuboids;

    public CuboidManager() {
        this.plugin = GlenCuboid.getInstance();
        this.cuboids = new TreeMap<>();
        this.chunkCuboids = new HashMap<>();
    }

    public Cuboid getByLoc(Player player) {
        return getByLoc(player.getLocation());
    }

    public Cuboid getByLoc(Location loc) {
        if (loc == null) return null;
        if (loc.getWorld() == null) return null;

        ChunkRef chunk = new ChunkRef(loc);
        Map<ChunkRef, List<Cuboid>> maps = chunkCuboids.get(loc.getWorld().getName());
        List<Cuboid> cuboids = maps.get(chunk);

        if (cuboids != null) {
            for (Cuboid cub : cuboids) {
                if (cub == null) continue;
                if (cub.containsLoc(loc))
                    return cub;
            }
        }
        return null;
    }

    public Cuboid getByName(String name) {
        if (name == null) return null;
        return cuboids.get(name.toLowerCase());
    }

    public void add(Player player, Location location, String name) {

        if (cuboids.containsKey(name)) {
            player.sendMessage("Ta dzialka juz istnieje");
            return;
        }

        CuboidArea ca = new CuboidArea(location, 25, 300);
        Cuboid cb = new Cuboid(player, name, ca);
        List<Flag> flags = new ArrayList<>();
        List<Flag> disabledflags = new ArrayList<>();
        flags.add(Flag.USE);
        flags.add(Flag.BEACON);
        flags.add(Flag.DOORS);
        flags.add(Flag.ANIMALSKILLING);
        flags.add(Flag.CRAFTING);
        flags.add(Flag.ENCHANT);
        flags.add(Flag.SHEARS);
        flags.add(Flag.BUTTONS);
        disabledflags.add(Flag.REDSTONE);
        disabledflags.add(Flag.ANIMALSKILLING);
        disabledflags.add(Flag.ANIMALSSPAWN);
        cb.getFlags().setFlags(flags);
        cb.getFlags().setDisabledFlags(disabledflags);

        try {
            plugin.getStorageManager().insertField(cb);
            cuboids.put(cb.getName().toLowerCase(), cb);
            calculateChunks(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Cuboid getEnabledSource(Location loc, Flag flag) {

        Cuboid cuboid = getByLoc(loc);

        if (cuboid.getFlags().hasFlag(flag)) {
            return cuboid;
        }
        return null;
    }


    public boolean playerHasPermission(Cuboid cuboid, Player player, Flag flag){
        if(player == null || cuboid == null || flag == null) return true;
        if(player.hasPermission("glencuboid.bypass.perms") || cuboid.isOwner(player))
            return true;

        if (!cuboid.getFlags().hasFlag(flag)) {
            return false;
        }

        if(cuboid.getTeam().isAllowed(player)){
            CuboidRole role = cuboid.getTeam().getRolePlayer(player);

            if(plugin.getTeamManager().isFlagInRole(role, flag)){
                return true;
            }
        }
        return false;
    }


    public boolean allowed(Cuboid cuboid, String target, Flag flag) {
        if (cuboid == null || target == null) {
            return false;
        }

        if (cuboid.isOwner(target))
            return true;

        if (cuboid.getTeam().isAllowed(target)) {
            return plugin.getTeamManager().isFlagInRole(cuboid.getTeam().getRolePlayer(target), flag);
        }
        return false;
    }

    public void updateCuboid(Cuboid cuboid) {
        cuboids.clear();
        cuboids.put(cuboid.getName(), cuboid);
        calculateChunks(cuboid.getName());
    }


    public void load() {
        this.cuboids.clear();

        for (String worldName : getWorldNames()) {
            try {
                this.chunkCuboids.put(worldName, loadCuboids(worldName));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }

    public Set<String> getWorldNames() {
        Set<String> worlds = new HashSet<>();

        for (World world : Bukkit.getWorlds())
            worlds.add(world.getName());

        return worlds;
    }

    public Map<ChunkRef, List<Cuboid>> loadCuboids(String worldName) {
        Map<ChunkRef, List<Cuboid>> cubRes = new HashMap<>();

        for (Map.Entry<String, List<Cuboid>> cubStr : plugin.getStorageManager().loadFields(worldName).entrySet()) {
            for (Cuboid cub : cubStr.getValue()) {
                for (ChunkRef chunk : getChunks(cub)) {
                    List<Cuboid> cubb = new ArrayList<>();
                    if (cubRes.containsKey(chunk))
                        cubb.addAll(cubRes.get(chunk));
                    cubb.add(cub);
                    cubRes.put(chunk, cubb);
                }
                cuboids.put(cub.getName(), cub);
            }
        }

        return cubRes;
    }

    private List<ChunkRef> getChunks(Cuboid cub) {
        List<ChunkRef> chunks = new ArrayList<>();
        chunks.addAll(cub.getArea().getChunks());
        return chunks;
    }

    public void calculateChunks(String name) {
        this.chunkCuboids.clear();
        Cuboid cub = cuboids.get(name);
        if (cub != null) {
            String world = cub.getArea().getWorldName();
            if (this.chunkCuboids.get(world) == null) {
                this.chunkCuboids.put(world, new HashMap<>());
            }
            for (ChunkRef chunk : getChunks(cub)) {
                List<Cuboid> cubb = new ArrayList<>();
                if (this.chunkCuboids.get(world).containsKey(chunk))
                    cubb.addAll(this.chunkCuboids.get(world).get(chunk));
                cubb.add(cub);
                this.chunkCuboids.get(world).put(chunk, cubb);
            }
        }
    }

    public SortedMap<String, Cuboid> getCuboids() {
        return cuboids;
    }

    public Map<String, Map<ChunkRef, List<Cuboid>>> getChunkCuboids() {
        return chunkCuboids;
    }
}
