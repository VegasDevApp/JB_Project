package cls;

import cls.db.DBManager;
import cls.db.configuration.DbConfig;

public class SystemStartUp {
    public static void start(){
        // Load db configuration
        DbConfig.load();
        DBManager.initDataBase();
    }
}
