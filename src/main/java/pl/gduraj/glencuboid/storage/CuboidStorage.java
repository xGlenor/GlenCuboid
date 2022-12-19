package pl.gduraj.glencuboid.storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.cuboid.CuboidArea;
import pl.gduraj.glencuboid.enums.Dirty;

import java.sql.*;
import java.util.*;

public class CuboidStorage {

    private final GlenCuboid plugin;
    private final Storage storage;
    private final Connection conn;

    private final HashMap<String, Cuboid> pending;

    public CuboidStorage(GlenCuboid plugin, Storage storage, Connection conn) {
        this.plugin = plugin;
        this.storage = storage;
        this.conn = conn;

        this.pending = new HashMap<>();
    }

    public void offerCuboid(Cuboid cuboid) {
        synchronized (pending) {
            pending.put(cuboid.getIDString(), cuboid);
        }
    }

    public void updateCuboid(Cuboid cuboid) throws SQLException {
        QueryBuilder builder = new QueryBuilder();

        if (cuboid.isDirty(Dirty.CHANGE_OWNER)) {
            builder.add("owner = ?", cuboid.getTeam().getOwner());
            builder.add("owner_uuid = ?", cuboid.getTeam().getOwnerUUID().toString());
        }
        if (cuboid.isDirty(Dirty.CHANGE_ALLOWED)) {
            builder.add("allowed = ?", cuboid.getTeam().getTeamAsString());
        }
        if (cuboid.isDirty(Dirty.UPDATE_PREVENTUSE)) {
            builder.add("prevent_use = ?", cuboid.getFlags().getPreventUseAsString());
        }
        if (cuboid.isDirty(Dirty.CHANGE_FLAGS)) {
            builder.add("flags = ?", cuboid.getFlags().getFlagsAsString());
        }
        if (cuboid.isDirty(Dirty.CHANGE_NAME)) {
            builder.add("name = ?", cuboid.getName());
        }

        String builderString = builder.toQueryString();
        if (builderString.isEmpty()) return;

        try {
            String query = "UPDATE `" + storage.CUBOID_TABLE + "` SET " + builderString + " "
                    + "WHERE x = ? AND y = ? AND z = ? AND world = ?";

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                int count = builder.setParameters(ps, 0);
                ps.setInt(count + 1, cuboid.getArea().getCenterLocation().getBlockX());
                ps.setInt(count + 2, cuboid.getArea().getCenterLocation().getBlockY());
                ps.setInt(count + 3, cuboid.getArea().getCenterLocation().getBlockZ());
                ps.setString(count + 4, cuboid.getArea().getCenterLocation().getWorld().getName());
                ps.execute();
            }
        } finally {
            cuboid.clearDirty();
        }

    }

    private void processSingleCuboid(Cuboid cuboid) throws SQLException {

        if (cuboid.isDirty(Dirty.DELETE)) {
            deleteCuboid(cuboid);
        } else {
            updateCuboid(cuboid);
        }

        synchronized (this) {
            pending.remove(cuboid.getIDString());
        }
    }

    public void deleteCuboid(Cuboid cuboid) throws SQLException {
    }

    public void insertField(Cuboid cuboid) throws SQLException {

        if (pending.containsKey(cuboid.getIDString())) {
            processSingleCuboid(cuboid);
        }


        String query = "INSERT INTO `" + storage.CUBOID_TABLE + "` (`x`, `y`, `z`, `world`, `minx`, `miny`, `minz`, "
                + "`maxx`, `maxy`, `maxz`, `type_id`, `name`, `owner_uuid`, `owner`, `allowed`, "
                + "`prevent_use`, `flags`) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] params = new Object[]{
                cuboid.getArea().getCenterLocation().getBlockX(),
                cuboid.getArea().getCenterLocation().getBlockY(),
                cuboid.getArea().getCenterLocation().getBlockZ(),
                cuboid.getArea().getWorld().getName(),
                cuboid.getArea().getLowPoints().getBlockX(),
                cuboid.getArea().getLowPoints().getBlockY(),
                cuboid.getArea().getLowPoints().getBlockZ(),
                cuboid.getArea().getHighPoints().getBlockX(),
                cuboid.getArea().getHighPoints().getBlockY(),
                cuboid.getArea().getHighPoints().getBlockZ(),
                0,
                cuboid.getName(),
                cuboid.getTeam().getOwnerUUID().toString(),
                cuboid.getTeam().getOwner(),
                cuboid.getTeam().getTeamAsString(),
                cuboid.getFlags().getPreventUseAsString(),
                cuboid.getFlags().getFlagsAsString()
        };


        try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            QueryBuilder.setArguments(ps, params);

            synchronized (this) {
                System.out.println("RESULT FIELD : " + ps.executeUpdate());
            }

        }

    }

    public Map<String, List<Cuboid>> loadFields(String worldName) {
        Map<String, List<Cuboid>> cuboidsWorld = new HashMap<>();
        String query = "SELECT x, y, z, world, minx, miny, minz, maxx, maxy, maxz, type_id, name, owner_uuid, owner, allowed, prevent_use, flags, created_at "
                + "FROM glencuboid_cuboids WHERE world = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, worldName);
            try (ResultSet set = ps.executeQuery()) {
                int i = 0;
                while (set.next()) {
                    try {
                        int x = set.getInt("x");
                        int y = set.getInt("y");
                        int z = set.getInt("z");
                        String world = set.getString("world");
                        String name = set.getString("name");
                        UUID uuidOwner = UUID.fromString(set.getString("owner_uuid"));
                        String owner = set.getString("owner");
                        String allowed = set.getString("allowed");
                        String preventUse = set.getString("prevent_use");
                        String flags = set.getString("flags");
                        Timestamp time = set.getTimestamp("created_at");

                        CuboidArea ca = new CuboidArea(new Location(Bukkit.getWorld(world), x, y, z), 25, 255);
                        Cuboid cub = new Cuboid(uuidOwner, owner, name, ca);

                        cub.getTeam().setTeam(allowed);
                        cub.getFlags().setPreventUse(preventUse);
                        cub.getFlags().setFlags(flags);
                        cub.setCreatedTime(time);

                        if (!cuboidsWorld.containsKey(world))
                            cuboidsWorld.put(world, new ArrayList<>());
                        cuboidsWorld.get(world).add(cub);
                        i++;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                GlenCuboid.getMessageLoaded().add("Zaladowano " + i + " działek na świecie: " + worldName);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cuboidsWorld;
    }


}
