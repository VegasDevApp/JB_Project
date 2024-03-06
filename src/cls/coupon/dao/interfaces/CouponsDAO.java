package cls.coupon.dao.interfaces;

import cls.coupon.beans.Coupon;

import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon);
    boolean updateCoupon(Coupon coupon);
    void deleteCoupon(int couponID);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponID);
    void addCouponPurchase(int customerID, int couponID);
    void deleteCouponPurchasesByCompanyId(int companyId);
    void deleteCouponPurchase(int customerID, int couponID);
    boolean deleteCouponsByCompanyId(int companyId);

    void detachAllCouponFromCustomer(int customerId);
}
