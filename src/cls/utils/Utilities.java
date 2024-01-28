package cls.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utilities {
    public static String getFileContent(String path){
        String result = null;
        try {
            // Read content of file
            result = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return result;
    }
}
