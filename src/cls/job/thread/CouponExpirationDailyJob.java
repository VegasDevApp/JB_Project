package cls.job.thread;

import cls.coupon.dao.impl.CouponsDaoImpl;

public class CouponExpirationDailyJob implements Runnable {

    private CouponsDaoImpl couponsDao;
    private boolean quit;
    private static CouponExpirationDailyJob instance = null;

    private CouponExpirationDailyJob() {

    }

    //singleton instance
    public static CouponExpirationDailyJob getInstance() {
        if (instance == null) {
            synchronized (CouponExpirationDailyJob.class) {
                if (instance == null) {
                    instance = new CouponExpirationDailyJob();
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {

    }

    public void stop() {

    }
}
