package cls.facade.customer;

import cls.coupon.beans.Coupon;
import cls.customer.beans.Customer;
import cls.enums.Category;
import cls.facade.ClientFacade;
import java.util.ArrayList;
import static java.util.Objects.nonNull;

public class CustomerFacade extends ClientFacade {
    private int customerID;

    public CustomerFacade(int customerID) {
        this.customerID=customerID;
    }

    @Override
    public boolean login(String email, String password) {
        boolean isNotNull= nonNull(email) && nonNull(password);
        boolean isNotEmpty= !(email.isEmpty()) && !(password.isEmpty());

        if (isNotNull && isNotEmpty) {
            Customer customerByEmail = customersDao.getOneCustomerByEmail(email);
            if (nonNull(customerByEmail)){
                return customerByEmail.getPassword().equals(password);
            }
        }
        return false;
    }

    public void purchaseCoupon(Coupon coupon) {
        if(nonNull(coupon)) {
            couponsDao.addCouponPurchase(customerID, coupon.getId());
        }
    }

    public ArrayList<Coupon> getCustomerCoupons() {
        return couponsDao.getAllCustomerCoupons(customerID);
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) {
        return couponsDao.getAllCustomerCoupons(category);
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) {

        return couponsDao.getCustomerCouponsBelowPrice(customerID,maxPrice);
    }

    public Customer getCustomerDetails() {
        return customersDao.getOneCustomer(customerID);
    }

}
