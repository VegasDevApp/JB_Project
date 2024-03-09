package cls.db.configuration;

import cls.exceptions.JsonConvertException;
import cls.utils.Utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Loader {
    public static Map<String, String> getConfigurationFromJSON() {
        Map<String, String> result = new HashMap<>();

        // Read content of file
        String fileContent = null;

        // Convert it from JSON to map
        try {
            fileContent = Utilities.getFileContent("src/resources/db-config.json");
            result = Utilities.jsonFileContentToMap(fileContent);
        } catch (IOException e) {
            System.out.println("Cannot read DB configuration JSON file!");
        } catch (JsonConvertException e) {
            System.out.println("Cannot parse DB configuration JSON file!");
        }
        return result;
    }
}
