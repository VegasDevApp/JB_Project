package cls.customer.dao.impl;


import cls.customer.beans.Customer;
import cls.customer.dao.interfaces.CustomersDAO;
import cls.db.DBManager;
import cls.customer.dao.converter.Converter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class CustomersDaoImpl implements CustomersDAO {

    @Override
    public boolean isCustomerExists(String email) {
        String sql = "SELECT * FROM customers " +
                "WHERE email = ?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, email);

        try(ResultSet dbResult = DBManager.runQueryForResult(sql, params)) {
            return (
                    nonNull(dbResult)
                            && dbResult.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO `customers` " +
                "(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";

        Map<Integer, Object> params = populateParamsFull(customer);

        if (DBManager.runQuery(sql, params)) {
            System.out.println("Customer is added successfully: \n" + customer);
        }

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        boolean result = false;
        Customer target = getOneCustomer(customer.getId());
        if (target != null) {
            //CUSTOMER_ID`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD
            String sql = "UPDATE CUSTOMERS " +
                    "SET FIRST_NAME = ?, " +
                    "LAST_NAME=?, EMAIL=?, PASSWORD=? WHERE ID = ?";

            Map<Integer, Object> params = populateParamsFull(customer);

            params.put(params.size()+1 , target.getId());
            result = DBManager.runQuery(sql, params);
            if (result) {
                System.out.println("Customer is updated: \n" + customer);
            }
        }
        return result;
    }

    @Override
    public void deleteCustomer(int customerID) {
        String sql = "DELETE FROM `customers` WHERE (`ID` = ?);";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        if (DBManager.runQuery(sql, params)){
            System.out.println("Customer with id "+customerID+" was deleted!\n");
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> result = new ArrayList<>();

        String sql = "SELECT * FROM `customers`;";

        ResultSet dbResult = DBManager.runQueryForResult(sql);
        if (nonNull(dbResult)) {
            result = Converter.populate(dbResult);
        }
        return result;
    }

    @Override
    public Customer getOneCustomer(int customerID) {
        Customer result = null;

        String sql = "SELECT * FROM `customers` WHERE ID = ?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);

        ResultSet dbResult = DBManager.runQueryForResult(sql, params);
        if (nonNull(dbResult)) {
            ArrayList<Customer> customers = Converter.populate(dbResult);
            if (!customers.isEmpty()) result = customers.get(0);
        }
        return result;
    }

    /**
     * Take customer and returns hash map with index=value for sql prepared statement
     *
     * @param customer instance of Customer
     * @return hashmap for sql prepared statement
     */
    private Map<Integer, Object> populateParamsFull(Customer customer) {

        Map<Integer, Object> params = new HashMap<>();

        // FIRST_NAME
        params.put(1, customer.getFirstName());
        // LAST_NAME
        params.put(2, customer.getLastName());
        // EMAIL
        params.put(3, customer.getEmail());
        // PASSWORD
        params.put(4, customer.getPassword());

        return params;
    }
}
