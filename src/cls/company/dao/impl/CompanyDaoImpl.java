package cls.company.dao.impl;

import cls.company.beans.Company;
import cls.company.dao.converter.Converter;
import cls.company.dao.interfaces.CompanyDAO;
import cls.db.DBManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class CompanyDaoImpl implements CompanyDAO {

    @Override
    public boolean isCompanyExists(String email, String name) {
        boolean result = false;
        String sql = "SELECT * FROM COMPANIES WHERE EMAIL = ? OR NAME = ?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1,email);
        params.put(2,name);
        ResultSet dbResult = DBManager.runQueryForResult(sql, params);
        if (nonNull(dbResult)) {
            ArrayList<Company> companies = Converter.populate(dbResult);
            return !companies.isEmpty();
        }
        return result;
    }

    @Override
    public void addCompany(Company company) {
        var sql = "insert into companies (name, email, password) values (?, ?, ?)";
        Map<Integer, Object> params = populateParamsFull(company);

        if (DBManager.runQuery(sql, params)) {
            System.out.println("Company is Added: \n" + company);
        }
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean result = false;
        Company target = getOneCompany(company.getId());
        if (target != null) {
            var sql = "UPDATE COMPANIES SET EMAIL=?, PASSWORD=? WHERE ID=?";

            Map<Integer, Object> params = new HashMap<>();
            params.put(1, target.getEmail());
            params.put(2, target.getPassword());
            params.put(3, target.getId());
            result = DBManager.runQuery(sql, params);
            if (result) {
                System.out.println("Company is updated: \n" + company);
            }
        }
        return result;

    }

    @Override
    public boolean deleteCompany(int companyId) {
        boolean result = false;
        Company company1 = getOneCompany(companyId);
        if (nonNull(company1)) {

            var sql = "DELETE FROM COMPANIES WHERE ID=?";
            Map<Integer, Object> params = new HashMap<>();
            params.put(1, companyId);
            return DBManager.runQuery(sql, params);
        }
        return result;
    }

    @Override
    public Company getOneCompany(int companyId) {

        Company result = null;
        var sql = "SELECT ID, NAME, EMAIL, PASSWORD FROM COMPANIES WHERE ID = ?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, companyId);
        ResultSet dbResult = DBManager.runQueryForResult(sql, params);
        if (nonNull(dbResult)) {
            ArrayList<Company> companies = Converter.populate(dbResult);
            if (!companies.isEmpty()) result = companies.get(0);
        }
        return result;
    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> result = new ArrayList<>();

        var sql = "SELECT ID, NAME, EMAIL, PASSWORD FROM COMPANIES";

        ResultSet dbResult = DBManager.runQueryForResult(sql);
        if (nonNull(dbResult)) {
            result = Converter.populate(dbResult);
        }
        return result;
    }

    /**
     * Take company and returns hash map with index=value for sql prepared statement
     *
     * @param company instance of Company
     * @return hashmap for sql prepared statement
     */
    private Map<Integer, Object> populateParamsFull(Company company) {

        Map<Integer, Object> params = new HashMap<>();

        // COMPANY_NAME
        params.put(1, company.getName());
        // COMPANY_EMAIL
        params.put(2, company.getEmail());
        // COMPANY_PASS
        params.put(3, company.getPassword());

        return params;
    }
}