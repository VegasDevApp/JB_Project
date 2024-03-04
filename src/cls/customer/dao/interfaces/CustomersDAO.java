package cls.customer.dao.interfaces;

import cls.customer.beans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {

    public boolean isCustomerExists(String email);

    public void addCustomer(Customer customer);

    public boolean updateCustomer(Customer customer);

    public void deleteCustomer(int customerID);

    public ArrayList<Customer> getAllCustomers();

    public Customer getOneCustomer(int customerID);
}
