package pl.gduraj.glencuboid.storage.driver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL extends Storage {

    private String host;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private String connParams;
    private String POOL_NAME = "GlenCuboidHikariPool";
    private HikariDataSource connectionField;

    public MySQL(String host, int port, String databaseName, String username, String password, String connParams) {
        super("glencuboid_cuboids", "glencuboid_players");
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.connParams = connParams;

    }

    public void init() throws SQLException {
        install();
        installCuboids();
        installPlayers();
    }

    @Override
    protected Connection getConnection() throws SQLException{
        Connection conn = connectionField.getConnection();
        if(conn == null || conn.isClosed()){
            install();
        }

        return conn;
    }

    @Override
    public void install() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + databaseName + connParams);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(10);
        config.setMaxLifetime(1800000);
        config.setKeepaliveTime(0);
        config.setConnectionTimeout(5000);
        config.setPoolName(POOL_NAME);

        this.connectionField = new HikariDataSource(config);
    }

    @Override
    public void installCuboids() throws SQLException {
        exec("CREATE TABLE IF NOT EXISTS `" + CUBOID_TABLE + "` ("
                + "`id` bigint(20) AUTO_INCREMENT PRIMARY KEY NOT NULL , "
                + "`x` int(11) default NULL, "
                + "`y` int(11) default NULL, "
                + "`z` int(11) default NULL, "
                + "`world` varchar(25) default NULL, "
                + "`minx` int(11) default NULL, "
                + "`maxx` int(11) default NULL, "
                + "`miny` int(11) default NULL, "
                + "`maxy` int(11) default NULL, "
                + "`minz` int(11) default NULL, "
                + "`maxz` int(11) default NULL, "
                + "`type_id` int(11) DEFAULT NULL, "
                + "`name` varchar(37) DEFAULT NULL, "
                + "`owner_uuid` varchar(37) NOT NULL, "
                + "`owner` varchar(16) NOT NULL, "
                + "`allowed` text default NULL, "
                + "`prevent_use` text default NULL, "
                + "`flags` text DEFAULT NULL, "
                + "`created_at` timestamp default CURRENT_TIMESTAMP)");

        if(!tableExists(CUBOID_TABLE)){
            GlenCuboid.getInstance().getLogger().warning("SQL -> ERROR CREATE CUBOIDS TABLE");
            GlenCuboid.getInstance().disablePlugin();
        }
    }

    @Override
    public void installPlayers() throws SQLException {
        exec("CREATE TABLE IF NOT EXISTS `" + PLAYERS_TABLE + "` ("
                + "`id` bigint(20) NOT NULL auto_increment PRIMARY KEY, "
                + "`uuid` varchar(37) default NULL, "
                + "`player_name` varchar(16) NOT NULL, "
                + "`last_seen` bigint(20) default NULL, "
                + "`flags` TEXT default NULL)");

        if(!tableExists(PLAYERS_TABLE)){
            GlenCuboid.getInstance().getLogger().warning("SQL -> ERROR CREATE PLAYERS TABLE");
            GlenCuboid.getInstance().disablePlugin();
        }
    }

    @Override
    public void close() throws SQLException {
        getConnection().close();
    }



    private void exec(String state) throws SQLException{
        try(PreparedStatement ps = getConnection().prepareStatement(state)){
            ps.execute();
        }
    }

    @Override
    public boolean tableExists(String table) throws SQLException {
        try(ResultSet set = getConnection().getMetaData().getTables(null, null, table, null)){
            return set.next();
        }
    }
}
