package cls.facade;

import cls.coupon.beans.Coupon;
import cls.customer.beans.Customer;
import cls.enums.Category;
import cls.exceptions.UnAuthorizedException;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade() {

    }

    @Override
    public boolean login(String email, String password) {
        boolean isNotNull = nonNull(email) && nonNull(password);
        boolean isNotEmpty = !(email.isEmpty()) && !(password.isEmpty());

        if (isNotNull && isNotEmpty) {

            Customer customerByEmail = customersDao.getOneCustomerByEmail(email);
            if (nonNull(customerByEmail)) {
                customerID = customerByEmail.getId();
                return customerByEmail.getPassword().equals(password);
            }
        }
        return false;
    }

    public void purchaseCoupon(Coupon coupon) throws UnAuthorizedException {
        notLoggedIn();
        if (nonNull(coupon)) {
            couponsDao.addCouponPurchase(customerID, coupon.getId());
        }
    }

    public ArrayList<Coupon> getCustomerCoupons() throws UnAuthorizedException {
        notLoggedIn();
        return couponsDao.getAllCustomerCoupons(customerID);
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) throws UnAuthorizedException {
        notLoggedIn();
        return couponsDao.getAllCustomerCoupons(customerID, category);
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws UnAuthorizedException {
        notLoggedIn();
        return couponsDao.getCustomerCouponsBelowPrice(customerID, maxPrice);
    }

    public Customer getCustomerDetails() throws UnAuthorizedException {
        notLoggedIn();
        return customersDao.getOneCustomer(customerID);
    }

    private void notLoggedIn() throws UnAuthorizedException {
        if (customerID <= 0) {
            throw new UnAuthorizedException("Access denied, please log in first!");
        }
    }
}
