package pl.gduraj.glencuboid.storage;

import org.bukkit.configuration.file.FileConfiguration;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.cuboid.Cuboid;
import pl.gduraj.glencuboid.enums.Dirty;
import pl.gduraj.glencuboid.storage.driver.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StorageManager {

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
    private Storage storage;
    private CuboidStorage cuboidStorage;

    public StorageManager() {
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
            this.cuboidStorage = new CuboidStorage(plugin, storage, getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Storage init() throws SQLException {
        if (isMYSQL) {
            this.storage = new MySQL(hostname, port, dbName, username, password, params);
            this.storage.init();
        }

        return null;
    }

    public void insertField(Cuboid cuboid) throws SQLException {
        cuboidStorage.insertField(cuboid);
    }

    public Map<String, List<Cuboid>> loadFields(String worldName) {
        return cuboidStorage.loadFields(worldName);
    }

    public void offerCuboid(Cuboid cuboid) {
        try {
            updateCuboid(cuboid);
        } catch (SQLException e) {
            e.printStackTrace();
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

            try (PreparedStatement ps = getConnection().prepareStatement(query)) {
                int count = builder.setParameters(ps, 0);
                ps.setInt(count + 1, cuboid.getArea().getCenterLocation().getBlockX());
                ps.setInt(count + 2, cuboid.getArea().getCenterLocation().getBlockY());
                ps.setInt(count + 3, cuboid.getArea().getCenterLocation().getBlockZ());
                ps.setString(count + 4, cuboid.getArea().getCenterLocation().getWorld().getName());
                if (!ps.execute())
                    System.out.println("GIT");
                else
                    System.out.println("NIE GIT");
            }
        } finally {
            cuboid.clearDirty();
        }


    }

    public Connection getConnection() throws SQLException {
        return storage.getConnection();
    }

    public void close() throws SQLException {
        storage.close();
    }
}
