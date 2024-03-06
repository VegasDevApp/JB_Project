package cls.facade;

import cls.company.beans.Company;
import cls.coupon.beans.Coupon;
import cls.enums.Category;
import cls.exceptions.UnAuthorizedException;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class CompanyFacade extends ClientFacade {
    int companyId;

    public CompanyFacade() {
    }

    public void addCoupon(Coupon coupon) throws Exception {
        notLoggedIn();
        if (!couponsDao.isCouponExist(coupon)) {
            couponsDao.addCoupon(coupon);
        } else {
            System.out.println("Coupon already with title " + coupon.getTitle() + " exist");
        }
    }


    public void updateCoupon(Coupon coupon) throws Exception {
        notLoggedIn();
        couponsDao.updateCoupon(coupon);
    }

    public void deleteCoupon(int couponId) throws Exception {
        notLoggedIn();
        couponsDao.deleteCoupon(couponId);
    }

    public ArrayList<Coupon> getCompanyCoupons() throws Exception {
        notLoggedIn();
        return couponsDao.getAllCompanyCoupons(companyId);
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) throws Exception {
        notLoggedIn();
        return couponsDao.getAllCompanyCoupons(category);
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws Exception {
        notLoggedIn();
        return couponsDao.getCompanyCouponsBelowPrice(companyId, maxPrice);
    }

    public Company getCompanyDetails() throws Exception {
        notLoggedIn();
        return companyDao.getOneCompany(companyId);
    }


    @Override
    public boolean login(String email, String password) {
        boolean isNotNull = nonNull(email) && nonNull(password);
        boolean isNotEmpty = !(email.isEmpty()) && !(password.isEmpty());

        if (isNotNull && isNotEmpty) {

            Company companyByEmail = companyDao.getOneCompanyByEmail(email);
            if (nonNull(companyByEmail)) {
                companyId = companyByEmail.getId();
                return companyByEmail.getPassword().equals(password);

            }
        }
        return false;
    }
    private void notLoggedIn() throws Exception {
        if(this.companyId<=0){
            throw new UnAuthorizedException("Access denied, please log in!");
        }
    }
}
