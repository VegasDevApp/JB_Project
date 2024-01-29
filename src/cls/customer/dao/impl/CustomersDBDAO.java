package cls.customer.dao.impl;

import cls.ConnectionPool;
import cls.customer.beans.Customer;
import cls.customer.interfaces.CustomersDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {
    private ConnectionPool connectionPool;


    @Override
    public boolean isCustomerExists(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty())
            return false; //invalid input

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String CHECK_CUSTOMER = "SELECT * FROM customers WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(CHECK_CUSTOMER)) {
                statement.setString(1, email);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ConnectionPool.getInstance().restoreConnection((Connection) connectionPool);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null || !isCustomerValid(customer)) {
            System.out.println("Customer's data is not valid, try again");
            return;
        }
        if (isCustomerExists(customer.getEmail(),customer.getPassword())){
            System.out.println("This email is already registered, try with a different email");
            return;
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String ADD_CUSTOMER = "INSERT INTO `site_coupons`.`customers` " +
                    "(`FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) VALUES (?, ?, ?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(ADD_CUSTOMER)) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getPassword());

                int affectedRows = statement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Added " + customer.getFirstName()+" "+customer.getLastName() + " successfully");
                } else System.out.println("Failed to add this customer");
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isCustomerValid(Customer customer) {
        // checks if the data in the customer is valid before we add them
        return customer.getEmail() != null && !customer.getEmail().isEmpty()
                && customer.getPassword() != null && !customer.getPassword().isEmpty()
                && customer.getFirstName() != null && !customer.getFirstName().isEmpty()
                && customer.getLastName() != null && !customer.getLastName().isEmpty();
    }

    @Override
    public void updateCustomer(Customer customer) {

    }

    @Override
    public void deleteCustomer(int customerID) {

    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        return null;
    }
}
