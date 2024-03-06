package cls.facade;

import cls.company.beans.Company;
import cls.customer.beans.Customer;
import cls.exceptions.UnAuthorizedException;

import java.util.List;

public class AdminFacade extends ClientFacade {

    private boolean isLoggedIn = false;

    public AdminFacade() {
    }

    @Override
    public boolean login(String email, String password) {
        this.isLoggedIn = "admin@admin.com".equalsIgnoreCase(email) && "admin".equals(password);
        if(this.isLoggedIn){
            System.out.println("You are logged in");
        } else {
            System.out.println("Email or password incorrect, try again");
        }
        return this.isLoggedIn;
    }

    public void addCompany(Company company) throws Exception {

        notLoggedIn();

        if (!this.companyDao.isCompanyExists(company.getEmail(), company.getName())) {
            this.companyDao.addCompany(company);
        } else {
            System.out.println("Cannot add company that already exists");
        }
    }

    public void updateCompany(Company company) {
        this.companyDao.updateCompany(company);
    }

    public void deleteCompany(int companyId) throws Exception {

        notLoggedIn();

        // Delete coupon purchase history
        this.couponsDao.deleteCouponPurchasesByCompanyId(companyId);

        // Delete coupons
        this.couponsDao.deleteCouponsByCompanyId(companyId);

        // Delete company
        this.companyDao.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() throws Exception {
        notLoggedIn();
        return this.companyDao.getAllCompanies();
    }

    public Company getOneCompany(int companyId) throws Exception {
        notLoggedIn();
        return this.companyDao.getOneCompany(companyId);
    }

    public void addCustomer(Customer customer) throws Exception {
        notLoggedIn();
        if(this.customersDao.isCustomerExists(customer.getEmail())) {
            this.customersDao.addCustomer(customer);
        }
        else {
            System.out.println("Customer with " + customer.getEmail() + " already exists");
        }
    }

    public void updateCustomer(Customer customer) throws Exception {
        notLoggedIn();
        this.customersDao.updateCustomer(customer);
    }

    public void deleteCustomer(int customerId) throws Exception {
        notLoggedIn();
        this.couponsDao.detachAllCouponFromCustomer(customerId);
        this.customersDao.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() throws Exception {
        notLoggedIn();
        return customersDao.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) throws Exception {
        notLoggedIn();
        return this.customersDao.getOneCustomer(customerId);
    }

    private void notLoggedIn() throws Exception {
        if(!this.isLoggedIn){
            throw new UnAuthorizedException("Access denied, please log in!");
        }
    }
}
