package cls.facade;

import cls.company.beans.Company;
import cls.customer.beans.Customer;

import java.util.List;

public class AdminFacade extends ClientFacade {

    public AdminFacade() {
    }

    @Override
    public boolean login(String email, String password) {
        return "admin@admin.com".equalsIgnoreCase(email) && "admin".equals(password);
    }

    public void addCompany(Company company) {
        if (!this.companyDao.isCompanyExists(company.getEmail(), company.getName())) {
            this.companyDao.addCompany(company);
        } else {
            System.out.println("Cannot add company that already exists");
        }
    }

    public void updateCompany(Company company) {
        this.companyDao.updateCompany(company);
    }

    public void deleteCompany(int companyId) {

        // Delete coupon purchase history
        this.couponsDao.deleteCouponPurchasesByCompanyId(companyId);

        // Delete coupons
        this.couponsDao.deleteCouponsByCompanyId(companyId);

        // Delete company
        this.companyDao.deleteCompany(companyId);
    }

    public List<Company> getAllCompanies() {
        return this.companyDao.getAllCompanies();
    }

    public Company getOneCompany(int companyId) {
        return this.companyDao.getOneCompany(companyId);
    }

    public void addCustomer(Customer customer) {
        if(this.customersDao.isCustomerExists(customer.getEmail())) {
            this.customersDao.addCustomer(customer);
        }
        else {
            System.out.println("Customer with " + customer.getEmail() + " already exists");
        }
    }

    public void updateCustomer(Customer customer) {
        this.customersDao.updateCustomer(customer);
    }

    public void deleteCustomer(int customerId) {
        this.couponsDao.detachAllCouponFromCustomer(customerId);
        this.customersDao.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customersDao.getAllCustomers();
    }

    public Customer getOneCustomer(int customerId) {
        return this.customersDao.getOneCustomer(customerId);
    }
}
