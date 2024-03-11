package cls.job.thread;

import cls.coupon.dao.impl.CouponsDaoImpl;

import static java.util.Objects.isNull;

public class CouponExpirationDailyJob implements Runnable {

    private final CouponsDaoImpl couponsDao = new CouponsDaoImpl();
    private boolean quit;
    private static CouponExpirationDailyJob instance = null;
    private final long DAILY_24HOURS_CHECK = (1000 * 60 * 60 * 24);



    private CouponExpirationDailyJob() {

    }

    //singleton instance
    public static CouponExpirationDailyJob getInstance() {
        if (isNull(instance)) {
            synchronized (CouponExpirationDailyJob.class) {
                if (isNull(instance)) {
                    instance = new CouponExpirationDailyJob();
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        while (!quit) {
            try {
            deleteExpiredCoupons();
                Thread.sleep(DAILY_24HOURS_CHECK);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                stop();
            }
        }
    }

    //stops the thread
    public void stop() {
        quit = true;

    }


    //deletes all expired coupons
    private void deleteExpiredCoupons() {
    couponsDao.deleteExpiredCoupons();
    }
}
