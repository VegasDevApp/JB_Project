package cls.utils;

import cls.exceptions.JsonConvertException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utilities {
    public static String getFileContent(String path) throws IOException {
        String result = null;

        // Read content of file
        result = new String(Files.readAllBytes(Paths.get(path)));
        return result;
    }

    public static Map<String, String> jsonFileContentToMap(String fileContent) throws JsonConvertException {
        Map<String, String> result = new HashMap<>();

        if (fileContent != null && !fileContent.isBlank()) {
            try {
                // Take a content as JSON object
                JSONObject json = new JSONObject(fileContent);

                // Populate hash map
                for (String key : json.keySet()) {
                    result.put(key, json.get(key).toString());
                }
            } catch (JSONException e) {
                throw new JsonConvertException();
            }
        }
        return result;
    }

    public static ArrayList<Map<String, String>> jsonArrayContentToMap(String fileContent) throws JsonConvertException {
        ArrayList<Map<String, String>> result = new ArrayList<>();

        if (fileContent != null && !fileContent.isBlank()) {
            try {
                // Parse JSON array from file content
                JSONArray jsonArray = new JSONArray(fileContent);

                // Populate hash map from JSON array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Map<String, String> map = new HashMap<>();

                    // Populate hash map
                    for (String key : jsonObject.keySet()) {
                        map.put(key, jsonObject.get(key).toString());
                    }

                    result.add(map);
                }
            } catch (JSONException e) {
                throw new JsonConvertException();
            }
        }
        return result;
    }
}
