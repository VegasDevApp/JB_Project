package cls;

import cls.db.ConnectionPool;
import cls.db.DBManager;
import cls.db.configuration.DbConfig;
import cls.job.thread.CouponExpirationDailyJob;

public class SystemControl {

    private static CouponExpirationDailyJob couponExpirationDailyJob = CouponExpirationDailyJob.getInstance();

    public static void start(){
        // Load db configuration
        DbConfig.load();

        // Wait response from init DB
        // Init may take some time
        // we won't run a job before it
        if(DBManager.initDataBase())
            couponExpirationDailyJob.run();
    }

    public static void Stop(){
        try {
            couponExpirationDailyJob.stop();
            ConnectionPool.getInstance().closeAllConnections();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
