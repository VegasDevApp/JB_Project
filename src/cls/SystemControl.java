package cls;

import cls.db.ConnectionPool;
import cls.db.DBManager;
import cls.db.configuration.DbConfig;
import cls.job.thread.CouponExpirationDailyJob;

public class SystemControl {

    private static final CouponExpirationDailyJob COUPON_EXPIRATION_DAILY_JOB = CouponExpirationDailyJob.getInstance();
    private static final Thread COUPON_EXPIRATION_THREAD = new Thread(COUPON_EXPIRATION_DAILY_JOB);

    public static void start() {
        // Load db configuration
        DbConfig.load();
        DBManager.initDataBase();

        //Load expired coupon deletion thread
        COUPON_EXPIRATION_THREAD.start();

    }

    public static void Stop() {
        try {
            COUPON_EXPIRATION_THREAD.interrupt();
            ConnectionPool.getInstance().closeAllConnections();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
