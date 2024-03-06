package cls.facade;

import cls.company.beans.Company;
import cls.coupon.beans.Coupon;
import cls.enums.Category;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class CompanyFacade extends ClientFacade {

    private CompanyFacade() {
    }

    private Company company;

    public void addCoupon(Coupon coupon) {
        if (!couponsDao.isCouponExist(coupon)) {
            couponsDao.addCoupon(coupon);
        } else {
            System.out.println("Coupon already with title " + coupon.getTitle() + " exist");
        }
    }


    public void updateCoupon(Coupon coupon) {
        couponsDao.updateCoupon(coupon);
    }

    public void deleteCoupon(int couponId) {
        couponsDao.deleteCoupon(couponId);
    }

    public ArrayList<Coupon> getCompanyCoupons() {
        return couponsDao.getAllCompanyCoupons(company.getId());
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) {
        return couponsDao.getAllCompanyCoupons(category);
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) {
        return couponsDao.getCompanyCouponsBelowPrice(company.getId(), maxPrice);
    }

    public Company getCompanyDetails() {
        return company;
    }


    @Override
    protected boolean login(String email, String password) {

        var company = companyDao.getOneCompanyByEmail(email);
        if (isNull(company)) {
            return false;

        }
        if (!password.equals(company.getPassword())) {
            return false;
        }

        this.company = company;
        return true;
    }
}
