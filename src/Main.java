import cls.SystemControl;
import tests.Test;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    /*

        BEFORE YOU RUN!!!!

        Make sure that your database configurations are correct
        DataBase Config file: src/resources/db-config.json

     */

    public static void main(String[] args) {

        // JOB is turned off in
        // SystemControl due it conflicts
        // with starting tests set!
        SystemControl.start();

        /**
         *  Tests will wipe all data stored in database!!!
         */
        Test.testAll();
        SystemControl.Stop();
    }
}
