package tests;

public class Test {
    public static void testAll(){
        administratorTest();
        companyTests();
    }

    private static void companyTests() {
        CompanyTest companyTest = new CompanyTest();
        companyTest.loginBadCredentialsEmailTest();
        companyTest.loginBadCredentialsPasswordTest();
        companyTest.loginTest();

        companyTest.createCouponsTest();
        companyTest.getAllCouponsTest();
        companyTest.createSameCouponsTest();
    }

    private static void administratorTest() {
        AdministratorTest adminTest = new AdministratorTest();

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
