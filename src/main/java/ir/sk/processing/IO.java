package ir.sk.processing;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
public class IO {

    private PrintStream printStream = System.out;

    public static InputStream getInputStream(String name) throws URISyntaxException, FileNotFoundException {
        File file = getFile(name);
        return inputStream(file);
    }

    public static File getFile(String fileName) throws URISyntaxException {
        URL fileURL = IO.class.getClassLoader().getResource(fileName);
        Path path = Paths.get(Objects.requireNonNull(fileURL).toURI());
        return path.toFile();
    }

    public static InputStream inputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static void write(PrintStream writer, String str) {
        writer.println(str);
    }

}
