package cls.customer.dao.converter;

import cls.customer.beans.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Converter {
    public static ArrayList<Customer> populate(ResultSet resultSet) {
        ArrayList<Customer> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("ID"));
                customer.setFirstName(resultSet.getString("FIRST_NAME"));
                customer.setLastName(resultSet.getString("LAST_NAME"));
                customer.setEmail(resultSet.getString("EMAIL"));
                customer.setPassword(resultSet.getString("PASSWORD"));
                result.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
