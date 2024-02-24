package cls.company.dao.impl;

import cls.company.beans.Company;
import cls.company.dao.interfaces.CompanyDAO;
import cls.db.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompanyDaoImpl implements CompanyDAO {
    @Override
    public boolean isCompanyExists(String email, String password) {
        try {
            var conn = ConnectionPool.getInstance().getConnection();
            var sql = "select * from companies where email = ? and password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, password);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs != null && rs.next();
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCompany(Company company) {
        try {
            //trying to add new company
            var conn = ConnectionPool.getInstance().getConnection();
            var sql = "insert into companies (name, email, password) values (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                // we added Statement.RETURN_GENERATED_KEYS in order to obtain back a newly generated company ID

                // set actual values of parameters
                stmt.setString(1, company.getName());
                stmt.setString(2, company.getEmail());
                stmt.setString(3, company.getPassword());

                // and execute the insert
                var insertedRows = stmt.executeUpdate();

                if (insertedRows == 0) {
                    throw new SQLException("creating company has failed, no rows affected.");
                }

                // getting the inserted ID
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    // the result set shall contain the generated ID as in its first column
                    if (generatedKeys.next()) {
                        company.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("creating user failed, no ID obtained.");
                    }
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCompany(Company company) {

        try {
            var conn = ConnectionPool.getInstance().getConnection();
            var sql = "update companies set name=?, email=?, password=? where id=?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                // set actual values of parameters
                stmt.setString(1, company.getName());
                stmt.setString(2, company.getEmail());
                stmt.setString(3, company.getPassword());
                stmt.setInt(4, company.getId());

                // and execute the insert
                var updatedRows = stmt.executeUpdate();

                if (updatedRows == 0) {
                    throw new SQLException("updating company has failed, no rows affected.");
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteCompany(Company company) {
        try {
            var conn = ConnectionPool.getInstance().getConnection();
            var sql = "delete from companies where id=?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                // set actual values of parameters
                stmt.setInt(1, company.getId());

                // and execute the insert
                var deletedRows = stmt.executeUpdate();

                if (deletedRows == 0) {
                    throw new SQLException("deletion of company has failed, no rows affected.");
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Company getOneCompany(int companyId) {
        try {
            var conn = ConnectionPool.getInstance().getConnection();
            var sql = "select id, name, email, password from companies where id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, companyId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs == null || !rs.next()) {
                        return null;
                    }
                    var company = new Company();
                    company.setId(rs.getInt(1));
                    company.setName(rs.getString(2));
                    company.setEmail(rs.getString(3));
                    company.setPassword(rs.getString(4));
                    return company;
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        try {
            var conn = ConnectionPool.getInstance().getConnection();
            var sql = "select id, name, email, password from companies";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs == null) {
                        return null;
                    }
                    var result = new ArrayList<Company>();

                    while (rs.next()) {
                        var company = new Company();
                        company.setId(rs.getInt(1));
                        company.setName(rs.getString(2));
                        company.setEmail(rs.getString(3));
                        company.setPassword(rs.getString(4));
                        result.add(company);
                    }
                    return result;
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}