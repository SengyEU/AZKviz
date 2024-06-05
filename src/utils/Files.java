package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Files {

    public static List<String> getLinesFromFile(String path) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().toList();
        }
    }

}
