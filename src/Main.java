import cls.SystemStartUp;
import dao.entities.Company;
import dao.operations.CompaniesDBDAO;

import java.util.Objects;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        SystemStartUp.start();
        var dao = new CompaniesDBDAO();
        var company = new Company("test", "test@a.com", "123456");

        dao.addCompany(company);

        if (company.Id <= 0) {
            throw new Exception("pizdetz");
        }

        var ret = dao.isCompanyExists(company.email, company.password);
        if (ret) {
            System.out.println("company exists");
        } else {
            System.out.println("company does not exists");
        }

        var company1 = dao.getOneCompany(company.Id);
        if (company1 == null) {
            throw new Exception("pizdetz one comany");
        }


        company1.name = "new name";
        company1.email = "new email";
        company1.password = "new password";

        dao.updateCompany(company1);
        var updatedCompany = dao.getOneCompany(company1.Id);

        if (!Objects.equals(company1.name, updatedCompany.name) ||
                !Objects.equals(company1.email, updatedCompany.email) ||
                !Objects.equals(company1.password, updatedCompany.password)) {
            throw new Exception("update failed");
        }

        var allCompanies = dao.getAllCompanies();
        if (allCompanies.isEmpty()) {
            throw new Exception("pizdets - no companies");
        }
    }
}