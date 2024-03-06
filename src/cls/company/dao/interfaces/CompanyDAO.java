package cls.company.dao.interfaces;

import cls.company.beans.Company;

import java.util.ArrayList;

public interface CompanyDAO {
    boolean isCompanyExists(String email, String password);
    void addCompany(Company company);
    boolean updateCompany(Company company);
    boolean deleteCompany(int companyId);
    Company getOneCompany(int companyId);
    ArrayList<Company> getAllCompanies();
    Company getOneCompanyByEmail(String email);
}
