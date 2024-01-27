package dao.operations;

import dao.entities.Company;

import java.util.ArrayList;

public interface CompaniesDAO {
    boolean isCompanyExists(String email, String password);
    void addCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Company company);
    Company getOneCompany(int companyId);
    ArrayList<Company> getAllCompanies();

}
