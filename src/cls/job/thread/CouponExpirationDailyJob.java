package cls.job.thread;

import cls.coupon.dao.impl.CouponsDaoImpl;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public class CouponExpirationDailyJob implements Runnable {

    private final CouponsDaoImpl couponsDao = new CouponsDaoImpl();
    private boolean quit;
    private static CouponExpirationDailyJob instance = null;


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
            deleteExpiredCoupons();
            try {
                long dailyCheck = (1000 * 60 * 60 * 24);
                Thread.sleep(dailyCheck);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //stops the thread
    public void stop() {
        quit = true;

    }


    //deletes all expired coupons
    private void deleteExpiredCoupons() {
        LocalDate currentTime = LocalDate.now();
        couponsDao.deleteAllExpiredCoupons(currentTime);
    }
}
