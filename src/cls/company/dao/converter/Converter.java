package cls.company.dao.converter;

import cls.company.beans.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Converter {
    public static ArrayList<Company> populate(ResultSet resultSet) {
        ArrayList<Company> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Company company = new Company();

                company.setId(resultSet.getInt("ID"));
                company.setName(resultSet.getString("NAME"));
                company.setEmail(resultSet.getString("EMAIL"));
                company.setPassword(resultSet.getString("PASSWORD"));
                result.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}