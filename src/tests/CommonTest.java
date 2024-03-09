package tests;

import cls.company.beans.Company;
import cls.customer.beans.Customer;
import cls.db.DBManager;
import cls.exceptions.JsonConvertException;
import cls.utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CommonTest {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    protected static final String TESTS_RESOURCE_FOLDER = "src/tests/resources/";

    protected enum EntityType{
        Company,
        Customer
    }

    /**
     * DANGEROUS ACTION!!!!
     * ALL DB INFORMATION WILL BE LOST!!!
     */
    protected void wipeDbTables() {
        String sql =
                "SET FOREIGN_KEY_CHECKS = 0;" +
                "TRUNCATE `site_coupons`.`COMPANIES`;" +
                "TRUNCATE `site_coupons`.`COSTUMERS_VS_COUPONS`;" +
                "TRUNCATE `site_coupons`.`COUPONS`;" +
                "TRUNCATE `site_coupons`.`CUSTOMERS`;" +
                "SET FOREIGN_KEY_CHECKS = 1;";

        DBManager.runQuery(sql);
    }

    protected static <T> ArrayList<T> getEntitiesFromResource(EntityType entityType){

        ArrayList<T> result = new ArrayList<>();

        String filePath = TESTS_RESOURCE_FOLDER + (entityType == EntityType.Company ? "companies.json" : "customers.json");

        // Get resource content
        try {
            String resourceContent = Utilities.getFileContent(filePath);
            var entitiesMapList = Utilities.jsonArrayContentToMap(resourceContent);
            for (var map: entitiesMapList) {
                var entity = entityType == EntityType.Company ? companyFromMap(map) : customerFromMap(map);
                result.add((T)entity);
            }
        } catch (IOException e) {
            System.out.println("Cannot read file: " + filePath);
        } catch (JsonConvertException e){
            System.out.println("Cannot parse JSON " + filePath);
        }

        return result;
    }

    private static Customer customerFromMap(Map<String, String> map){
        Customer customer = new Customer();
        customer.setFirstName(map.get("firstName"));
        customer.setLastName(map.get("lastName"));
        customer.setEmail(map.get("email"));
        customer.setPassword(map.get("password"));
        return customer;
    }

    private static Company companyFromMap(Map<String, String> map){
        Company company = new Company();
        company.setName(map.get("name"));
        company.setEmail(map.get("email"));
        company.setPassword(map.get("password"));
        return company;
    }

    protected static void testFailed(String msg){

        // Red message
        System.out.println(ANSI_RED + "FAILED!!! " + msg + ANSI_RESET);

        // Abort app running with code 1
        System.exit(1);
    }

    protected static void testPassed(String msg){
        // Red message
        System.out.println(ANSI_GREEN + "PASSED!!! " + msg + ANSI_RESET);

    }
}
