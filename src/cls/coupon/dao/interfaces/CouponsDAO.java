package cls.coupon.dao.interfaces;

import cls.coupon.beans.Coupon;
import cls.enums.Category;

import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon);
    boolean updateCoupon(Coupon coupon);
    void deleteCoupon(int couponID);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponID);
    void addCouponPurchase(int customerID, int couponID);
    void deleteCouponPurchase(int customerID, int couponID);
    boolean isCouponExist(Coupon coupon);
    ArrayList<Coupon> getAllCompanyCoupons(int companyID);

    ArrayList<Coupon> getAllCompanyCoupons(Category category);

    ArrayList<Coupon> getCompanyCouponsBelowPrice(int companyID, double price);

}
