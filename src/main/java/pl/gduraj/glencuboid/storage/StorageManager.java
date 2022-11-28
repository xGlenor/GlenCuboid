package pl.gduraj.glencuboid.storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.cuboid.CuboidArea;
import pl.gduraj.glencuboid.storage.driver.MySQL;

import java.sql.*;
import java.util.*;

public class StorageManager {

    private Storage storage;
    private final GlenCuboid plugin;
    private final FileConfiguration config;
    private final String method;
    private final String hostname;
    private final int port;
    private final String dbName;
    private final String username;
    private final String password;
    private final String params;
    boolean isMYSQL;

    public StorageManager(){
        this.plugin = GlenCuboid.getInstance();
        config = plugin.getConfigManager().getConfig("config");

        method = config.getString("connections.method");
        hostname = config.getString("connections.hostname");
        port = config.getInt("connections.port");
        dbName = config.getString("connections.dbName");
        username = config.getString("connections.username");
        password = config.getString("connections.password");
        params = config.getString("connections.params");

        isMYSQL = method.equalsIgnoreCase("mysql");

        try {
            init();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private Storage init() throws SQLException{

        if(isMYSQL){
            this.storage = new MySQL(hostname, port, dbName, username, password, params);
            this.storage.init();
        }


        return null;
    }

    public void insertField(Cuboid cuboid) throws SQLException{
        insertField(getConnection(), cuboid);
    }


    public Map<String, List<Cuboid>> loadFields(String worldName){
        Map<String, List<Cuboid>> cuboidsWorld = new HashMap<>();
        String query = "SELECT x, y, z, world, minx, miny, minz, maxx, maxy, maxz, type_id, name, owner_uuid, owner, allowed, flags "
                + "FROM glencuboid_cuboids WHERE world = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(query)){
            ps.setString(1, worldName);
            try (ResultSet set = ps.executeQuery()){
                int i = 0;
                while (set.next()){
                    try {
                        int x = set.getInt("x");
                        int y = set.getInt("y");
                        int z = set.getInt("z");
                        String world = set.getString("world");
                        String name = set.getString("name");
                        UUID uuidOwner = UUID.fromString(set.getString("owner_uuid"));
                        String owner = set.getString("owner");
                        String flags = set.getString("flags");

                        CuboidArea ca = new CuboidArea(new Location(Bukkit.getWorld(world), x, y, z), 25, 255);
                        Cuboid cub = new Cuboid(uuidOwner, owner, name, ca);

                        System.out.println("FLAGI SM: " + flags);
                        cub.getFlags().setFlags(flags);

                        if(!cuboidsWorld.containsKey(world))
                            cuboidsWorld.put(world, new ArrayList<>());
                        cuboidsWorld.get(world).add(cub);
                        i++;
                    }catch (Exception ex){
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

    public void insertField(Connection conn, Cuboid cuboid) throws SQLException {

        String query = "INSERT INTO `glencuboid_cuboids` (`x`, `y`, `z`, `world`, `minx`, `miny`, `minz`, "
                + "`maxx`, `maxy`, `maxz`, `type_id`, `name`, `owner_uuid`, `owner`, `allowed`, "
                + "`flags`) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                cuboid.getOwnerUUID().toString(),
                cuboid.getOwner(),
                null,
                cuboid.getFlags().getFlagsAsString()
        };


        try(PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            QueryBuilder.setArguments(ps, params);

            synchronized (this){
                System.out.println("RESULT FIELD : " + ps.executeUpdate());
            }

        }

    }

    public Connection getConnection() throws SQLException{
        return storage.getConnection();
    }

    public void close() throws SQLException{
        storage.close();
    }
}
