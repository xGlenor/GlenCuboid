package pl.gduraj.glencuboid.storage;

import pl.gduraj.glencuboid.GlenCuboid;
import pl.gduraj.glencuboid.storage.driver.MySQL;

import java.sql.Connection;
import java.sql.SQLException;

public class StorageManager {

    private Storage storage;
    private GlenCuboid plugin;


    public StorageManager(){

        this.plugin = GlenCuboid.getInstance();

    }

    private Storage init() throws SQLException{
        Storage str;
        boolean isMYSQL = true;
        if(isMYSQL){
            this.storage = new MySQL(null, null, null, null);
        }

        this.storage.init();


        return null;
    }

    public Connection getConnection() throws SQLException{
        return storage.getConnection();
    }


}
