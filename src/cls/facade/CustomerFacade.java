package cls.facade;

import cls.coupon.beans.Coupon;
import cls.customer.beans.Customer;
import cls.enums.Category;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class CustomerFacade extends ClientFacade {
    private int customerID;
    private boolean isLoggedIn = false;

    public CustomerFacade() {

    }

    @Override
    public boolean login(String email, String password) {
        boolean isNotNull = nonNull(email) && nonNull(password);
        boolean isNotEmpty = !(email.isEmpty()) && !(password.isEmpty());

        if (isNotNull && isNotEmpty) {

            Customer customerByEmail = customersDao.getOneCustomerByEmail(email);
            boolean customerCheckPassword = customerByEmail.getPassword().equals(password);
            boolean customerCheckEmail = customerByEmail.getEmail().equals(email);
            this.isLoggedIn = customerCheckPassword && customerCheckEmail;

            if (this.isLoggedIn) {
                customerID = customerByEmail.getId();
                System.out.println("You are logged in!");
            }
        }else {
            System.out.println("Email or password are incorrect, try again");
        }

        return this.isLoggedIn;
    }

    public void purchaseCoupon(Coupon coupon) throws Exception {
        notLoggedIn();
        if (nonNull(coupon)) {
            couponsDao.addCouponPurchase(customerID, coupon.getId());
        }
    }

    public ArrayList<Coupon> getCustomerCoupons() throws Exception {
        notLoggedIn();
        return couponsDao.getAllCustomerCoupons(customerID);
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) throws Exception {
        notLoggedIn();
        return couponsDao.getAllCustomerCoupons(category);
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws Exception {
        notLoggedIn();
        return couponsDao.getCustomerCouponsBelowPrice(customerID, maxPrice);
    }

    public Customer getCustomerDetails() throws Exception {
        notLoggedIn();
        return customersDao.getOneCustomer(customerID);
    }

    private void notLoggedIn() throws Exception {
        if (!this.isLoggedIn) {
            throw new Exception("Access denied, please log in first!");
        }
    }
}
