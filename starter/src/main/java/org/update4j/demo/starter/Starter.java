package org.update4j.demo.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class Starter {
    public static void main(String[] args) throws IOException {
        Process p = Runtime.
                getRuntime().
                exec("cmd /c launch.bat " + DirUtils.resolveCacheDir().toString());

        String line;
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((line = input.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }

}
