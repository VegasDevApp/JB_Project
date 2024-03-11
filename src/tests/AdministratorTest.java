package tests;

import cls.LoginManager;
import cls.company.beans.Company;
import cls.coupon.dao.impl.CouponsDaoImpl;
import cls.coupon.dao.interfaces.CouponsDAO;
import cls.customer.beans.Customer;
import cls.customer.dao.interfaces.CustomersDAO;
import cls.enums.ClientType;
import cls.exceptions.UnAuthorizedException;
import cls.facade.AdminFacade;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class AdministratorTest extends CommonTest {

    public static final int EXPECTED_COMPANIES_AMOUNT = 5;
    public static final int EXPECTED_CUSTOMERS_AMOUNT = 10;
    private AdminFacade facade;

    private CouponsDAO couponsDAO;
    private CustomersDAO customersDAO;

    public AdministratorTest() {
        // Clear all data from DB
        this.wipeDbTables();
        this.couponsDAO = new CouponsDaoImpl();
    }

    public void loginBadCredentialsEmailTest(){
        facade = (AdminFacade) LoginManager.getInstance().login("admin1@admin.com", "admin", ClientType.Administrator);
        if (facade == null) {
            testPassed("loginBadCredentialsEmailTest() succeed for Administrator!!!");
        } else {
            testFailed("loginBadCredentialsEmailTest() NOT succeed for Administrator!!!");
        }
    }

    public void loginBadCredentialsPasswordTest(){
        facade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "adminos", ClientType.Administrator);
        if (facade == null) {
            testPassed("loginBadCredentialsPasswordTest() succeed for Administrator!!!");
        } else {
            testFailed("loginBadCredentialsPasswordTest() NOT succeed for Administrator!!!");
        }
    }

    public void loginTest(){
        facade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.Administrator);
        if (facade instanceof AdminFacade) {
            testPassed("Login Manager succeed for Administrator!!!");
        } else {
            testFailed("Login Manager NOT succeed for Administrator!!!");
        }
    }

    public void addCompanyTest() {
        String exceptionTitle = "addCompanyTest() test ";
        ArrayList<Company> companiesToAdd = getEntitiesFromResource(EntityType.Company);
        for (Company company : companiesToAdd) {
            try {
                facade.addCompany(company);
            } catch (UnAuthorizedException e) {
                testFailed(exceptionTitle + e.getMessage());
            } catch (Exception e) {
                testFailed(exceptionTitle);
            }
        }
    }

    public void getAllCompaniesTest() {
        String exceptionTitle = "getAllCompaniesTest() test ";
        try {
            var companies = facade.getAllCompanies();
            if (companies.size() == EXPECTED_COMPANIES_AMOUNT) {
                testPassed("All Companies are added!");
                testPassed("getAllCompanies() got " + EXPECTED_COMPANIES_AMOUNT + " companies as expected!");
            } else {
                testFailed("getAllCompanies() got " + companies.size() + " instead of " + EXPECTED_COMPANIES_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void addSameCompanyTest() {
        String exceptionTitle = "addSameCompanyTest() test ";
        ArrayList<Company> companiesToAdd = getEntitiesFromResource(EntityType.Company);
        try {
            for (Company company : companiesToAdd) {
                facade.addCompany(company);
            }
            var companies = facade.getAllCompanies();
            if (companies.size() == EXPECTED_COMPANIES_AMOUNT) {
                testPassed("addSameCompanyTest() Equals companies aren't added!");
            } else {
                testFailed("addSameCompanyTest() Equals companies aren't added! Got " + companies.size() +
                        " instead of " + EXPECTED_COMPANIES_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void addSameNameCompanyTest() {
        String exceptionTitle = "addSameNameCompanyTest() test ";
        ArrayList<Company> companiesToAdd = getEntitiesFromResource(EntityType.Company);
        try {
            for (Company company : companiesToAdd) {
                company.setEmail("123456");
                facade.addCompany(company);
            }
            var companies = facade.getAllCompanies();
            if (companies.size() == EXPECTED_COMPANIES_AMOUNT) {
                testPassed("addSameNameCompanyTest() Equals names companies aren't added!");
            } else {
                testFailed("addSameNameCompanyTest() Equals names aren't added! Got " + companies.size() +
                        " instead of " + EXPECTED_COMPANIES_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void addSameEmailCompanyTest() {
        String exceptionTitle = "addSameEmailCompanyTest() test ";
        ArrayList<Company> companiesToAdd = getEntitiesFromResource(EntityType.Company);
        try {
            for (Company company : companiesToAdd) {
                company.setName("123456");
                facade.addCompany(company);
            }
            var companies = facade.getAllCompanies();
            if (companies.size() == EXPECTED_COMPANIES_AMOUNT) {
                testPassed("addSameEmailCompanyTest() Equals e-mail companies aren't added!");
            } else {
                testFailed("addSameEmailCompanyTest() Equals e-mail aren't added! Got " + companies.size() +
                        " instead of 5!");
            }
        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void getCompanyByIdTest() {
        String exceptionTitle = "getCompanyByIdTest() test ";
        try {
            Company company = facade.getAllCompanies().stream().findFirst().orElse(null);
            if(nonNull(company)){
                Company company2 = facade.getOneCompany(company.getId());
                if(nonNull(company2) && company2.equals(company)){
                    testPassed("getCompanyByIdTest() test succeed");
                } else {
                    testFailed("getCompanyByIdTest() test failed");
                }
            }

        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    // TODO - update


    // CUSTOMERS

    public void addCustomersTest() {
        String exceptionTitle = "addCustomersTest() test ";
        ArrayList<Customer> customersToAdd = getEntitiesFromResource(EntityType.Customer);
        for (Customer customer : customersToAdd) {
            try {
                facade.addCustomer(customer);
            } catch (UnAuthorizedException e) {
                testFailed(exceptionTitle + e.getMessage());
            } catch (Exception e) {
                testFailed(exceptionTitle);
            }
        }
    }

    public void getAllCustomersTest() {
        String exceptionTitle = "getAllCustomersTest() test ";
        try {
            var customers = facade.getAllCustomers();
            if (customers.size() == EXPECTED_CUSTOMERS_AMOUNT) {
                testPassed("All Companies are added!");
                testPassed("getAllCustomersTest() got " + EXPECTED_CUSTOMERS_AMOUNT + " customers as expected!");
            } else {
                testFailed("getAllCustomersTest() got " + customers.size() + " instead of " + EXPECTED_CUSTOMERS_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void addSameCustomersTest() {
        String exceptionTitle = "addSameCustomersTest() test ";
        ArrayList<Customer> customersToAdd = getEntitiesFromResource(EntityType.Customer);
        try {
            for (Customer customer : customersToAdd) {
                facade.addCustomer(customer);
            }
            var customers = facade.getAllCustomers();
            if (customers.size() == EXPECTED_CUSTOMERS_AMOUNT) {
                testPassed("addSameCustomersTest() Equals customers aren't added!");
            } else {
                testFailed("addSameCustomersTest() Equals customers aren't added! Got " + customers.size() +
                        " instead of " + EXPECTED_CUSTOMERS_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void getCustomerByIdTest() {
        String exceptionTitle = "getCustomerByIdTest() test ";
        try {
            Customer customer = facade.getAllCustomers().stream().findFirst().orElse(null);
            if(nonNull(customer)){
                Customer customer2 = facade.getOneCustomer(customer.getId());
                if(nonNull(customer2) && customer2.equals(customer)){
                    testPassed("getCustomerByIdTest() test succeed");
                } else {
                    testFailed("getCustomerByIdTest() test failed");
                }
            }

        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }

    public void deleteCompanyTest() {
        String exceptionTitle = "deleteCompanyTest() test ";
        try {
            int testCompanyId = 2;
            // Check if there is coupons left
            var coupons = this.couponsDAO.getAllCompanyCoupons(testCompanyId);

            Company company = facade.getOneCompany(testCompanyId);
            if(isNull(company) || coupons.isEmpty() || company.getId() != testCompanyId){
                testFailed("deleteCompanyTest() no conditions for test ID: " + testCompanyId);
            }
            facade.deleteCompany(testCompanyId);
            company = facade.getOneCompany(testCompanyId);
            if(isNull(company)){
                testPassed("deleteCompanyTest() Company with test ID: " + testCompanyId + " deleted!");
            } else {
                testFailed("deleteCompanyTest() Company with test ID: " + testCompanyId + " NOT deleted!");
            }

            var coupon = this.couponsDAO.getOneCoupon(coupons.get(0).getId());
            if(isNull(coupon)){
                testPassed("deleteCompanyTest() no coupon remain of deleted company");
            } else {
                testFailed("deleteCompanyTest() HAVE coupon remain of deleted company");
            }


        } catch (UnAuthorizedException e) {
            testFailed(exceptionTitle + e.getMessage());
        } catch (Exception e) {
            testFailed(exceptionTitle);
        }
    }
}
