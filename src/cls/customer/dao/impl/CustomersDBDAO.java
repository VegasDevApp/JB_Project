package cls.customer.dao.impl;


import cls.customer.beans.Customer;
import cls.customer.interfaces.CustomersDAO;
import cls.db.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {
    private ConnectionPool connectionPool;

    @Override
    public boolean isCustomerExists(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty())
            return false; //invalid input

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String checkQuery = "SELECT * FROM customers WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(checkQuery)) {
                statement.setString(1, email);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null || !isCustomerValid(customer)) {
            System.out.println("Customer's data is not valid, try again");
            return;
        }

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            if (isEmailAlreadyUsed(connection, customer.getEmail())) { //does not create a customer if one with the same email exsists
                System.out.println("Customer with the same email already exists");
                return;
            }

            String addQuery = "INSERT INTO `site_coupons`.`customers` (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(addQuery, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getPassword());

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    // Retrieve the generated ID after the insertion
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            customer.setId(generatedId); // Set the generated ID in the Customer object
                            System.out.println("Customer added with ID: " + generatedId);
                        } else {
                            throw new SQLException("Failed to retrieve the generated ID.");
                        }
                    }
                } else {
                    throw new SQLException("Adding customer failed, no rows affected.");
                }
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    private boolean isEmailAlreadyUsed(Connection connection, String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM `site_coupons`.`customers` WHERE `EMAIL` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }

        return false; // Default to not used (or handle the exception accordingly)
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
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String updateQuery = "UPDATE `site_coupons`.`customers` SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, PASSWORD=? WHERE (ID = ?)";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {

                statement.setString(1, customer.getFirstName());
                statement.setString(2, customer.getLastName());
                statement.setString(3, customer.getEmail());
                statement.setString(4, customer.getPassword());
                statement.setInt(5, customer.getId());
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Customer with ID " + customer.getId() + " updated successfully");
                } else {
                    throw new SQLException("Updating customer failed, no rows affected.");
                }
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(int customerID) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String deleteQuery = "DELETE FROM `site_coupons`.`customers` WHERE (`ID` = ?);";
            try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
                statement.setInt(1, customerID);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Customer with ID " + customerID + " deleted successfully");
                } else {
                    System.out.println("No customer found with ID " + customerID);
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String getAllQuery = "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM `site_coupons`.`customers`;";
            try (PreparedStatement statement = connection.prepareStatement(getAllQuery)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet == null) {
                        return null;
                    }
                    var result = new ArrayList<Customer>();
                    while (resultSet.next()) {
                        var customer = new Customer();
                        customer.setId(resultSet.getInt(1));
                        customer.setFirstName(resultSet.getString(2));
                        customer.setLastName(resultSet.getString(3));
                        customer.setEmail(resultSet.getString(4));
                        customer.setPassword(resultSet.getString(5));
                        result.add(customer);
                    }
                    return result;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getOneCustomer(int customerID) {

        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            String getOneQuery = "SELECT ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD FROM `site_coupons`.`customers` WHERE ID = ?";
            try (PreparedStatement statement = connection.prepareStatement(getOneQuery)) {
                statement.setInt(1, customerID);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // If there is a result, create a Customer object and populate it with data
                        Customer customer = new Customer();
                        customer.setId(resultSet.getInt("ID"));
                        customer.setFirstName(resultSet.getString("FIRST_NAME"));
                        customer.setLastName(resultSet.getString("LAST_NAME"));
                        customer.setEmail(resultSet.getString("EMAIL"));
                        customer.setPassword(resultSet.getString("PASSWORD"));

                        return customer;
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
