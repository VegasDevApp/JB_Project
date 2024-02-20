package cls.db.configuration;

import cls.utils.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Loader {
    public static Map<String, String> getConfigurationFromJSON() {
        Map<String, String> result = new HashMap<>();

        // Read content of file
        String fileContent = Utilities.getFileContent("src/resources/db-config.json");

        if (fileContent != null && !fileContent.isBlank()) {
            try {
                // Take a content as JSON object
                JSONObject json = new JSONObject(fileContent);

                // Populate hash map
                for (String key : json.keySet()) {
                    result.put(key, json.get(key).toString());
                }
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
}
