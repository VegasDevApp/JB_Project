package cls.company.dao.interfaces;

import cls.company.beans.Company;

import java.util.ArrayList;

public interface CompanyDAO {
    boolean isCompanyExists(String email, String password);
    void addCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(Company company);
    Company getOneCompany(int companyId);
    ArrayList<Company> getAllCompanies();

}
