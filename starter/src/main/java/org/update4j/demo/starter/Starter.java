package org.update4j.demo.starter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class Starter {
    public static void main(String[] args) throws IOException {
        Runtime.
                getRuntime().
                exec("cmd /c start \"\" launch.bat");
    }

    public Path resolveCacheDir(Map<String, String> namedParams) {
        if(namedParams == null) {
            namedParams = Collections.emptyMap();
        }

        String cacheDir = "USERLIB";

        if(cacheDir == null || cacheDir.isEmpty()) {
            return Paths.get(".");
        }

        Path path;

        if(cacheDir.contains("USERLIB")) {
            String replacement;
            switch(OS.current) {
                case mac:
                    replacement = Paths.get(System.getProperty("user.home"))
                            .resolve("Library")
                            .resolve("Application Support")
                            .resolve(cacheDir.substring(8))
                            .toString();
                    break;
                case win:
                    replacement = Paths.get(System.getProperty("user.home"))
                            .resolve("AppData")
                            .resolve("Local")
                            .resolve(cacheDir.substring(8))
                            .toString();
                    break;
                default:
                    replacement = Paths.get(System.getProperty("user.home"))
                            .resolve("." + cacheDir.substring(8))
                            .toString();
            }
            path = Paths.get(replacement);
        } else {
            path = Paths.get(cacheDir);
        }

        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return path;
    }

    enum OS {
        win, mac, linux, other;

        public static final OS current;

        static {
            String os = System.getProperty("os.name", "generic").toLowerCase();

            if ((os.contains("mac")) || (os.contains("darwin")))
                current = mac;
            else if (os.contains("win"))
                current = win;
            else if (os.contains("nux"))
                current = linux;
            else
                current = other;
        }
    }
}
