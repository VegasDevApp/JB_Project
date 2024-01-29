package cls.customer.interfaces;

import cls.customer.beans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {

    public boolean isCustomerExists(String email, String password) throws InterruptedException;

    public void addCustomer(Customer customer) throws InterruptedException;

    public void updateCustomer(Customer customer);

    public void deleteCustomer(int customerID);

    public ArrayList<Customer> getAllCustomers();

    public Customer getOneCustomer(int customerID);
}
