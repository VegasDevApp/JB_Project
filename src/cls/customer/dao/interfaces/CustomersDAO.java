package cls.customer.dao.interfaces;

import cls.customer.beans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {

     boolean isCustomerExists(String email);

     void addCustomer(Customer customer);

     boolean updateCustomer(Customer customer);

     void deleteCustomer(int customerID);

     ArrayList<Customer> getAllCustomers();

     Customer getOneCustomer(int customerID);
    Customer getOneCustomerByEmail(String email);
}
