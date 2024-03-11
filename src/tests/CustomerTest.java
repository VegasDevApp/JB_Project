package tests;

import cls.LoginManager;
import cls.coupon.beans.Coupon;
import cls.coupon.dao.impl.CouponsDaoImpl;
import cls.enums.Category;
import cls.enums.ClientType;
import cls.exceptions.UnAuthorizedException;
import cls.facade.CustomerFacade;

import static java.util.Objects.nonNull;

public class CustomerTest extends CommonTest {
    // Must be from companies.json file
    private final String CUSTOMER_TEST_FIRST_NAME = "Olivia";
    private final String CUSTOMER_TEST_LAST_NAME = "Martinez";
    private final String CUSTOMER_TEST_EMAIL = "olivia@example.com";
    private final String CUSTOMER_TEST_PASSWORD = "12345";

    private final CouponsDaoImpl couponsDao = new CouponsDaoImpl();

    private CustomerFacade facade;


    public void loginBadCredentialsEmailTest() {
        facade = (CustomerFacade) LoginManager.getInstance().login("admin1@admin.com", CUSTOMER_TEST_PASSWORD, ClientType.Customer);
        if (facade == null) {
            testPassed("loginBadCredentialsEmailTest() succeed for Customer!!!");
        } else {
            testFailed("loginBadCredentialsEmailTest() NOT succeed for Customer!!!");
        }
    }

    public void loginBadCredentialsPasswordTest() {
        facade = (CustomerFacade) LoginManager.getInstance().login(CUSTOMER_TEST_EMAIL, "adminos", ClientType.Customer);
        if (facade == null) {
            testPassed("loginBadCredentialsPasswordTest() succeed for Customer!!!");
        } else {
            testFailed("loginBadCredentialsPasswordTest() NOT succeed for Customer!!!");
        }
    }

    public void loginTest() {
        facade = (CustomerFacade) LoginManager.getInstance().login(CUSTOMER_TEST_EMAIL, CUSTOMER_TEST_PASSWORD, ClientType.Customer);
        if (facade instanceof CustomerFacade) {
            testPassed("Login Manager succeed for Customer!!!");
        } else {
            testFailed("Login Manager NOT succeed for Customer!!!");
        }
    }

    public void purchaseCouponTest() {
        String exceptionTitle = "purchaseCouponTest() test ";
        try {
            int couponId = 2;
            Coupon coupon = getCoupon(couponId);

            // Purchase coupon
            facade.purchaseCoupon(coupon);

            var customerCoupons = facade.getCustomerCoupons();
            if(customerCoupons.size() != 1){
                testFailed("purchaseCouponTest() can't get purchased coupon!");
            } else {
                var coup = customerCoupons.get(0);

                String msg = "purchaseCouponTest() purchased coupon Id is correct!";
                if (coupon.getId() == coup.getId()) testPassed(msg);
                else testFailed(msg);

                msg = "purchaseCouponTest() purchased coupon amount decreased by one!";
                if (coup.getAmount() == coupon.getAmount() - 1) testPassed(msg);
                else testFailed(msg);
            }

        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e){
            testFailed(exceptionTitle);
        }
    }

    public void purchaseSameCouponTest() {
        String exceptionTitle = "purchaseSameCouponTest() test ";
        try {
            int couponId = 2;
            Coupon coupon = getCoupon(couponId);

            // Purchase coupon
            facade.purchaseCoupon(coupon);

            var customerCoupons = facade.getCustomerCoupons();
            if(customerCoupons.size() != 1){
                testFailed("purchaseSameCouponTest() can't get purchased coupon!");
            } else if (customerCoupons.size() > 1) {
                testFailed("purchaseSameCouponTest() has more than one coupon!");
            } else {
                var coup = customerCoupons.get(0);

                String msg = "purchaseSameCouponTest() purchased coupon Id is correct!";
                if (coupon.getId() == coup.getId()) testPassed(msg);
                else testFailed(msg);

                msg = "purchaseSameCouponTest() purchased coupon amount not decreased by one!";
                if (coup.getAmount() == coupon.getAmount()) testPassed(msg);
                else testFailed(msg);
            }

        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e){
            testFailed(exceptionTitle);
        }
    }

    public void getCouponsByCategoryTest() {
        String exceptionTitle = "getCouponsByCategoryTest() test ";
        try {

            var coupons = facade.getCustomerCoupons(Category.ELECTRICITY);
            String msg = "getCouponsByCategoryTest() " + Category.ELECTRICITY.name();
            if(coupons.size() == 1 && coupons.get(0).getCategory() == Category.ELECTRICITY){
                testPassed(msg);
            } else {
                testFailed(msg);
            }

            coupons = facade.getCustomerCoupons(Category.VACATION);
            msg = "getCouponsByCategoryTest() " + Category.VACATION.name();
            if(coupons.isEmpty()){
                testPassed(msg);
            } else {
                testFailed(msg);
            }

        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e){
            testFailed(exceptionTitle);
        }
    }

    public void getCouponsByMaxPriceTest() {
        String exceptionTitle = "getCouponsByMaxPriceTest() test ";
        try {

            var coupons = facade.getCustomerCoupons(100D);
            String msg="getCouponsByMaxPriceTest() by max price 100 is zero!";
            if(coupons.isEmpty()){
                testPassed(msg);
            } else {
                testFailed(msg);
            }

            coupons = facade.getCustomerCoupons(110D);
            msg="getCouponsByMaxPriceTest() by max price 110 is one!";
            if(coupons.size() == 1){
                testPassed(msg);
            } else {
                testFailed(msg);
            }

        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e){
            testFailed(exceptionTitle);
        }
    }


    private Coupon getCoupon(int couponId) {
        Coupon res = couponsDao.getOneCoupon(couponId);
        if(nonNull(res)){
            return res;
        } else {
            testFailed("getCoupon(int ind) in Customers!!!");
        }
        return null;
    }
}
