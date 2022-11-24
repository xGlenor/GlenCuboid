package pl.gduraj.glencuboid.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.util.ChunkRef;

import java.sql.SQLException;
import java.util.*;

public class CuboidManager {

    private GlenCuboid plugin;
    protected SortedMap<String, Cuboid> cuboids;
    protected Map<String, Map<ChunkRef, List<Cuboid>>> chunkCuboids;

    public CuboidManager(){
        this.plugin = GlenCuboid.getInstance();
        this.cuboids = new TreeMap<>();
        this.chunkCuboids = new HashMap<>();
    }

    public Cuboid getByLoc(Player player) {
        return getByLoc(player.getLocation());
    }

    public Cuboid getByLoc(Location loc){
        if(loc == null) return null;
        if(loc.getWorld() == null) return null;

        ChunkRef chunk = new ChunkRef(loc);
        Map<ChunkRef, List<Cuboid>> maps = chunkCuboids.get(loc.getWorld().getName());
        List<Cuboid> cuboids = maps.get(chunk);

        if(cuboids != null){
            for(Cuboid cub : cuboids){
                if(cub == null) continue;
                if(cub.containsLoc(loc))
                    return cub;
            }
        }
        return null;
    }

    public Cuboid getByName(String name){
        if(name == null) return null;
        return cuboids.get(name.toLowerCase());
    }

    public void add(Player player, Location location, String name){

        if(cuboids.containsKey(name)){
            player.sendMessage("Ta dzialka juz istnieje");
            return;
        }

        CuboidArea ca = new CuboidArea(location, 25, 255);
        Cuboid cb = new Cuboid(player, name, ca);

        try {
            plugin.getStorageManager().insertField(cb);
            cuboids.put(cb.getName().toLowerCase(), cb);
            calculateChunks(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void load(){
        this.cuboids.clear();

        for(String worldName : getWorldNames()){
            try{
                this.chunkCuboids.put(worldName, loadCuboids(worldName));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }


    }

    public Set<String> getWorldNames(){
        Set<String> worlds = new HashSet<>();

        for(World world : Bukkit.getWorlds())
            worlds.add(world.getName());

        return worlds;
    }

    public Map<ChunkRef, List<Cuboid>> loadCuboids(String worldName){
        Map<ChunkRef, List<Cuboid>> cubRes = new HashMap<>();

        for(Map.Entry<String, List<Cuboid>> cubStr : plugin.getStorageManager().loadFields(worldName).entrySet()){
            for(Cuboid cub : cubStr.getValue()){
                for(ChunkRef chunk : getChunks(cub)){
                    List<Cuboid> cubb = new ArrayList<>();
                    if(cubRes.containsKey(chunk))
                        cubb.addAll(cubRes.get(chunk));
                    cubb.add(cub);
                    cubRes.put(chunk, cubb);
                }
                cuboids.put(cub.getName(), cub);
            }
        }

        return cubRes;
    }

    private List<ChunkRef> getChunks(Cuboid cub){
        List<ChunkRef> chunks = new ArrayList<>();
        chunks.addAll(cub.getArea().getChunks());
        return chunks;
    }

    public void calculateChunks(String name){
        Cuboid cub = cuboids.get(name);
        if(cub != null){
            String world = cub.getArea().getWorldName();
            if(this.chunkCuboids.get(world) == null){
                this.chunkCuboids.put(world, new HashMap<>());
            }
            for(ChunkRef chunk : getChunks(cub)){
                List<Cuboid> cubb = new ArrayList<>();
                if(this.chunkCuboids.get(world).containsKey(chunk))
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
