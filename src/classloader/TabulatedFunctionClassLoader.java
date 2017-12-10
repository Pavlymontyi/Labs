package classloader;

import java.io.FileInputStream;
import java.io.IOException;

public class TabulatedFunctionClassLoader extends ClassLoader {

    public Class loadClassFromFile(String fileName) throws IOException {
        byte[] b = loadClassData(fileName);
        return defineClass(null, b, 0, b.length);
    }

    private byte[] loadClassData(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);
        byte[] fileContent = new byte[in.available()];
        in.read(fileContent);
        in.close();
        return fileContent;
    }
}
