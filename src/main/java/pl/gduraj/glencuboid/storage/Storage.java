package pl.gduraj.glencuboid.storage;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Storage {

    protected String CUBOID_TABLE;
    protected String PLAYERS_TABLE;

    protected Storage(String tableCuboidName, String tablePlayerName){
        this.CUBOID_TABLE = tableCuboidName;
        this.PLAYERS_TABLE = tablePlayerName;
    }

    protected abstract Connection getConnection() throws SQLException;

    public abstract void init() throws SQLException;

    public abstract void install();

    public abstract void installCuboids() throws SQLException;

    public abstract void installPlayers() throws SQLException;

    public abstract void close() throws SQLException;

    public abstract boolean tableExists(String table) throws SQLException;
}
