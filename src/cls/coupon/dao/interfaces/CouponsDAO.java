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

    void deleteCouponPurchasesByCompanyId(int companyId);

    void deleteCouponPurchase(int customerID, int couponID);

    boolean deleteCouponsByCompanyId(int companyId);

    void detachAllCouponFromCustomer(int customerId);

    boolean isCouponExist(Coupon coupon);

    ArrayList<Coupon> getAllCompanyCoupons(int companyID);

    ArrayList<Coupon> getAllCompanyCoupons(int companyID, Category category);

    ArrayList<Coupon> getCompanyCouponsBelowPrice(int companyID, double price);

    ArrayList<Coupon> getAllCustomerCoupons(int customerID);

    ArrayList<Coupon> getAllCustomerCoupons(int customerID, Category category);

    ArrayList<Coupon> getCustomerCouponsBelowPrice(int customerId, double price);
}
