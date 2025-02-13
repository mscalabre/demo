package org.update4j.demo.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.update4j.AddPackage;
import org.update4j.Configuration;
import org.update4j.FileMetadata;
import org.update4j.OS;
import org.update4j.demo.starter.DirUtils;

public class CreateConfig {

    public static void main(String[] args) throws IOException {


        String configLoc = System.getProperty("config.location");

        String busniessDir = configLoc + "/business";
        String bootstrapDir = configLoc + "/bootstrap";


        String cacheLoc = System.getProperty("maven.dir") + "/fxcache";

        cacheJavafx();

        boolean local = true;

        Path cacheDir = local ? Path.of("${user.dir}") : DirUtils.resolveCacheDir();

        Configuration config = Configuration.builder()
                        .baseUri("http://localhost/demo/business")
                        .basePath(Path.of(cacheDir.toString(), "business"))
                        .file(FileMetadata.readFrom(busniessDir + "/business-1.0.0.jar")
                                .path("business-1.0.0.jar")
                                .modulepath()
                        )
                        .file(FileMetadata.readFrom(busniessDir + "/jfoenix-9.0.8.jar")
                                        .path("jfoenix-9.0.8.jar")
                                        .modulepath())
                        .file(FileMetadata.readFrom(busniessDir + "/jfxtras-common-10.0-r1.jar")
                                        .path("jfxtras-common-10.0-r1.jar")
                                        .modulepath())
                        .file(FileMetadata.readFrom(busniessDir + "/jfxtras-gauge-linear-10.0-r1.jar")
                                        .path("jfxtras-gauge-linear-10.0-r1.jar")
                                        .modulepath())
                        .property("maven.central", MAVEN_BASE)
                        .build();

        try (Writer out = Files.newBufferedWriter(Paths.get(busniessDir + "/config.xml"))) {
            config.write(out);
        }


        config = Configuration.builder()
                        .baseUri("${maven.central.javafx}")
                        .basePath(Path.of(cacheDir.toString(), "bootstrap"))
                        .file(FileMetadata.readFrom(bootstrapDir + "/../business/config.xml") // fall back if no internet
                                        .uri("http://localhost/demo/business/config.xml")
                                        .path("../business/config.xml"))
                        .file(FileMetadata.readFrom(bootstrapDir + "/bootstrap-1.0.0.jar")
                                        .classpath()
                                        .uri("http://localhost/demo/bootstrap/bootstrap-1.0.0.jar"))
                        .files(FileMetadata.streamDirectory(cacheLoc)
                                        .filter(fm -> fm.getSource().getFileName().toString().startsWith("javafx"))
                                        .peek(f -> f.classpath())
                                        .peek(f -> f.ignoreBootConflict()) // if run with JDK 9/10
                                        .peek(f -> f.osFromFilename())
                                        .peek(f -> f.uri(extractJavafxURL(f.getSource(), f.getOs())))
                        )
                        .file(FileMetadata.readFrom(busniessDir + "/controlsfx-9.0.0.jar")
                                .classpath()
                                .uri("http://localhost/demo/business/controlsfx-9.0.0.jar")
                        )
//                        .file(FileMetadata.readFrom(busniessDir + "/update4j-1.5.9.jar")
//                                .classpath()
//                                .uri("http://localhost/demo/business/update4j-1.5.9.jar")
//                        )
                        .property("default.launcher.main.class", "org.update4j.demo.bootstrap.DownloadOrLaunchBootstrap")
                        .property("maven.central", MAVEN_BASE)
                        .property("maven.central.javafx", "${maven.central}/org/openjfx/")
                        .build();

        try (Writer out = Files.newBufferedWriter(Paths.get(configLoc + "/setup.xml"))) {
            config.write(out);
        }

    }

    private static final String MAVEN_BASE = "https://repo1.maven.org/maven2";

    private static String mavenUrl(String groupId, String artifactId, String version, OS os) {
        StringBuilder builder = new StringBuilder();
        builder.append(MAVEN_BASE + '/');
        builder.append(groupId.replace('.', '/') + "/");
        builder.append(artifactId.replace('.', '-') + "/");
        builder.append(version + "/");
        builder.append(artifactId.replace('.', '-') + "-" + version);

        if (os != null) {
            builder.append('-' + os.getShortName());
        }

        builder.append(".jar");

        return builder.toString();
    }

    private static String mavenUrl(String groupId, String artifactId, String version) {
        return mavenUrl(groupId, artifactId, version, null);
    }

    private static String extractJavafxURL(Path path, OS os) {
        Pattern regex = Pattern.compile("javafx-([a-z]+)-([0-9.]+)(?:-(win|mac|linux))?\\.jar");
        Matcher match = regex.matcher(path.getFileName().toString());

        if (!match.find())
            return null;

        String module = match.group(1);
        String version = match.group(2);
        if (os == null && match.groupCount() > 2) {
            os = OS.fromShortName(match.group(3));
        }

        return mavenUrl("org.openjfx", "javafx." + module, version, os);
    }

    private static String injectOs(String file, OS os) {
        return file.replaceAll("(.+)\\.jar", "$1-" + os.getShortName() + ".jar");
    }

    private static void cacheJavafx() throws IOException {
        String names = System.getProperty("target") + "/javafx";
        Path cacheDir = Paths.get(System.getProperty("maven.dir"), "fxcache");

        try (Stream<Path> files = Files.list(Paths.get(names))) {
            files.forEach(f -> {
                try {
                    
                    if (!Files.isDirectory(cacheDir))
                        Files.createDirectory(cacheDir);
                    
                    for (OS os : EnumSet.of(OS.WINDOWS, OS.MAC, OS.LINUX)) {
                        Path file = cacheDir.resolve(injectOs(f.getFileName().toString(), os));

                        if (Files.notExists(file)) {
                            String download = extractJavafxURL(f, os);
                            URI uri = URI.create(download);
                            try (InputStream in = uri.toURL().openStream()) {
                                Files.copy(in, file);
                            }
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
