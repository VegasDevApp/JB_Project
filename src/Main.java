import cls.SystemStartUp;
import cls.coupon.beans.Coupon;
import cls.coupon.dao.impl.CouponsDaoImpl;
import cls.enums.Category;

import java.time.LocalDate;
import java.util.ArrayList;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        SystemStartUp.start();

        CouponsDaoImpl couponsDao = new CouponsDaoImpl();
        for (int i = 0; i < 3; i++) {
            Coupon coupon = new Coupon();
            coupon.setCompanyID(1);
            coupon.setCategory(Category.VACATION);
            coupon.setTitle("Title " + i);
            coupon.setDescription("Description " + i);
            coupon.setStartDate(LocalDate.now().plusDays(i - 1));
            coupon.setEndDate(LocalDate.now().plusDays(i));
            coupon.setAmount(i);
            coupon.setPrice(100);
            coupon.setImage("");
            couponsDao.addCoupon(coupon);
        }

        // Get all coupons
        ArrayList<Coupon> coupons = couponsDao.getAllCoupons();
        for (Coupon coupon:coupons) {
            System.out.println("C O U P O N!");
            System.out.println(coupon.toString());
        }
    }
}