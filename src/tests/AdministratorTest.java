package tests;

import cls.LoginManager;
import cls.company.beans.Company;
import cls.customer.beans.Customer;
import cls.enums.ClientType;
import cls.exceptions.UnAuthorizedException;
import cls.facade.AdminFacade;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class AdministratorTest extends CommonTest {

    public static final int EXPECTED_COMPANIES_AMOUNT = 5;
    public static final int EXPECTED_CUSTOMERS_AMOUNT = 10;
    private AdminFacade facade;

    public AdministratorTest() {
        // Clear all data from DB
        this.wipeDbTables();
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
        ArrayList<Company> companiesToAdd = getEntitiesFromResource(EntityType.Company);
        for (Company company : companiesToAdd) {
            try {
                facade.addCompany(company);
            } catch (UnAuthorizedException e) {
                testFailed("addCompany() test " + e.getMessage());
            } catch (Exception e) {
                testFailed("addCompany() test");
            }
        }
    }

    public void getAllCompaniesTest() {
        try {
            var companies = facade.getAllCompanies();
            if (companies.size() == EXPECTED_COMPANIES_AMOUNT) {
                testPassed("All Companies are added!");
                testPassed("getAllCompanies() got " + EXPECTED_COMPANIES_AMOUNT + " companies as expected!");
            } else {
                testFailed("getAllCompanies() got " + companies.size() + " instead of " + EXPECTED_COMPANIES_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed("getAllCompanies() test" + e.getMessage());
        } catch (Exception e) {
            testFailed("getAllCompanies() test");
        }
    }

    public void addSameCompanyTest() {
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
            testFailed("addCompany() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("addCompany() test");
        }
    }

    public void addSameNameCompanyTest() {
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
            testFailed("addSameNameCompanyTest() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("addSameNameCompanyTest() test");
        }
    }

    public void addSameEmailCompanyTest() {
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
            testFailed("addSameEmailCompanyTest() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("addSameEmailCompanyTest() test");
        }
    }

    public void getCompanyByIdTest() {
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
            testFailed("getCompanyByIdTest() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("getCompanyByIdTest() test");
        }
    }

    // TODO - update


    // CUSTOMERS

    public void addCustomersTest() {
        ArrayList<Customer> customersToAdd = getEntitiesFromResource(EntityType.Customer);
        for (Customer customer : customersToAdd) {
            try {
                facade.addCustomer(customer);
            } catch (UnAuthorizedException e) {
                testFailed("UnAuthorizedException addCustomersTest() test");
            } catch (Exception e) {
                testFailed("addCustomersTest() test");
            }
        }
    }

    public void getAllCustomersTest() {
        try {
            var customers = facade.getAllCustomers();
            if (customers.size() == EXPECTED_CUSTOMERS_AMOUNT) {
                testPassed("All Companies are added!");
                testPassed("getAllCustomersTest() got " + EXPECTED_CUSTOMERS_AMOUNT + " customers as expected!");
            } else {
                testFailed("getAllCustomersTest() got " + customers.size() + " instead of " + EXPECTED_CUSTOMERS_AMOUNT + "!");
            }
        } catch (UnAuthorizedException e) {
            testFailed("getAllCustomersTest() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("getAllCustomersTest() test");
        }
    }

    public void addSameCustomersTest() {
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
            testFailed("addSameCustomersTest() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("addSameCustomersTest() test");
        }
    }

    public void getCustomerByIdTest() {
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
            testFailed("getCustomerByIdTest() test " + e.getMessage());
        } catch (Exception e) {
            testFailed("getCustomerByIdTest() test");
        }
    }
}
