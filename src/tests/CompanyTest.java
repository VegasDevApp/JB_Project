package tests;

import cls.LoginManager;
import cls.coupon.beans.Coupon;
import cls.enums.Category;
import cls.enums.ClientType;
import cls.exceptions.UnAuthorizedException;
import cls.facade.CompanyFacade;

import java.time.LocalDate;

public class CompanyTest extends CommonTest {

    public static final int COUPONS_TOTAL = 4;
    // Must be from companies.json file
    private final String COMPANY_TEST_NAME = "company2";
    private final String COMPANY_TEST_EMAIL = "company2@example.com";
    private final String COMPANY_TEST_PASSWORD = "1234";

    private CompanyFacade facade;

    public void loginBadCredentialsEmailTest() {
        facade = (CompanyFacade) LoginManager.getInstance().login("admin1@admin.com", COMPANY_TEST_PASSWORD, ClientType.Company);
        if (facade == null) {
            testPassed("loginBadCredentialsEmailTest() succeed for Company!!!");
        } else {
            testFailed("loginBadCredentialsEmailTest() NOT succeed for Company!!!");
        }
    }

    public void loginBadCredentialsPasswordTest() {
        facade = (CompanyFacade) LoginManager.getInstance().login(COMPANY_TEST_EMAIL, "adminos", ClientType.Company);
        if (facade == null) {
            testPassed("loginBadCredentialsPasswordTest() succeed for Company!!!");
        } else {
            testFailed("loginBadCredentialsPasswordTest() NOT succeed for Company!!!");
        }
    }

    public void loginTest() {
        facade = (CompanyFacade) LoginManager.getInstance().login(COMPANY_TEST_EMAIL, COMPANY_TEST_PASSWORD, ClientType.Company);
        if (facade instanceof CompanyFacade) {
            testPassed("Login Manager succeed for Company!!!");
        } else {
            testFailed("Login Manager NOT succeed for Company!!!");
        }
    }

    public void createCouponsTest() {
        try {
            generateCoupons();
        } catch (UnAuthorizedException e) {
            testFailed("createCoupons() test" + e.getMessage());
        } catch (Exception e){
            testFailed("createCoupons() test");

        }
    }

    public void getAllCouponsTest() {
        try {
            var coupons = facade.getCompanyCoupons();
            String msgAdd = "createCouponsTest() adding new coupons for company";
            String msgGet = "getAllCouponsTest() got " + coupons.size() + " expected " + COUPONS_TOTAL;
            if(coupons.size() == COUPONS_TOTAL){
                testPassed(msgAdd);
                testPassed(msgGet);
            } else {
                testFailed(msgAdd);
                testFailed(msgGet);
            }
        } catch (UnAuthorizedException e) {
            testFailed("createCoupons() test" + e.getMessage());
        } catch (Exception e){
            testFailed("createCoupons() test");

        }
    }

    private void generateCoupons() throws UnAuthorizedException {
        for (int i = 0; i < COUPONS_TOTAL; i++) {
            Coupon coupon = new Coupon();
            coupon.setCategory(Category.values()[i]);
            coupon.setTitle("Coupon title " + i);
            coupon.setDescription("Coupon description " + i);
            coupon.setPrice(i + 100d);
            coupon.setImage("image " + i);
            coupon.setAmount(i);
            coupon.setStartDate(LocalDate.now().plusDays(i - 1));
            coupon.setEndDate(LocalDate.now().plusDays(i));
            facade.addCoupon(coupon);
        }
    }

    public void createSameCouponsTest() {
        try {
            generateCoupons();
            var coupons = facade.getCompanyCoupons();
            String msgAdd = "createSameCouponsTest() adding same coupons for company";
            String msgGet = "createSameCouponsTest() got " + coupons.size() + " expected " + COUPONS_TOTAL;
            if(coupons.size() == COUPONS_TOTAL){
                testPassed(msgAdd);
                testPassed(msgGet);
            } else {
                testFailed(msgAdd);
                testFailed(msgGet);
            }
        } catch (UnAuthorizedException e) {
            testFailed("createSameCouponsTest() test" + e.getMessage());
        } catch (Exception e){
            testFailed("createSameCouponsTest() test");

        }
    }


}
