package cls;

import cls.db.ConnectionPool;
import cls.db.DBManager;
import cls.db.configuration.DbConfig;

public class SystemControl {
    public static void start(){
        // Load db configuration
        DbConfig.load();
        DBManager.initDataBase();
    }

    public static void Stop(){
        try {
            ConnectionPool.getInstance().closeAllConnections();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
