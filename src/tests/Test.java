package tests;

public class Test {

    private static CustomerTest customerTest = new CustomerTest();
    private static CompanyTest companyTest = new CompanyTest();
    private static AdministratorTest adminTest = new AdministratorTest();

    public static void testAll(){
        administratorTest();
        companyTests();
        customerTests();
        administratorSecondTest();

        // Wipe test data
        wipeDbTables();
    }

    private static void administratorSecondTest() {
        adminTest.deleteCompanyTest();
    }

    private static void wipeDbTables() {
        adminTest.wipeDbTables();
    }

    private static void customerTests() {

        customerTest.loginBadCredentialsEmailTest();
        customerTest.loginBadCredentialsPasswordTest();
        customerTest.loginTest();
        customerTest.purchaseCouponTest();
        customerTest.purchaseSameCouponTest();
        customerTest.getCouponsByCategoryTest();
        customerTest.getCouponsByMaxPriceTest();
    }

    private static void companyTests() {

        companyTest.loginBadCredentialsEmailTest();
        companyTest.loginBadCredentialsPasswordTest();
        companyTest.loginTest();

        companyTest.createCouponsTest();
        companyTest.getAllCouponsTest();
        companyTest.createSameCouponsTest();
        companyTest.getCompanyCouponsByCategoryTest();
        companyTest.getCompanyCouponsByMaxPriceTest();
        companyTest.getCompanyDetailsTest();
    }

    private static void administratorTest() {


        // Companies

        adminTest.loginBadCredentialsEmailTest();
        adminTest.loginBadCredentialsPasswordTest();
        adminTest.loginTest();
        adminTest.addCompanyTest();
        adminTest.getAllCompaniesTest();
        adminTest.addSameCompanyTest();
        adminTest.addSameNameCompanyTest();
        adminTest.addSameEmailCompanyTest();
        adminTest.getCompanyByIdTest();
        // Update company
        // Delete a company after coupon tests

        // Customers
        adminTest.addCustomersTest();
        adminTest.getAllCustomersTest();
        adminTest.addSameCustomersTest();
        adminTest.getCustomerByIdTest();
        // Update customer
        // Delete a customer after coupon tests
    }
}
