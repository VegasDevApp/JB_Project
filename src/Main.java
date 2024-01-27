import dao.entities.Company;
import dao.operations.CompaniesDBDAO;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {

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
    }
}