package tests.lab4;

import functions.TabulatedFunction;
import functions.TabulatedFunctions;

import java.io.*;

public class Task7 {
    private static final String byteStreamFileName = "byteStream.txt";
    private static final String symbolStreamFileName = "symbolStream.txt";

    private static OutputStream testOutputStream;
    private static InputStream testInputStream;
    private static Writer testWriter;
    private static Reader testReader;

    static {
        try {
            testOutputStream = new FileOutputStream(byteStreamFileName);
            testInputStream = new FileInputStream(byteStreamFileName);
            testWriter = new FileWriter(symbolStreamFileName);
            testReader = new FileReader(symbolStreamFileName);
        } catch (IOException ex) {
            throw new RuntimeException("Error during creation test streams", ex);
        }
    }

    public static void test() {
        System.out.println("Task7: Test linked list tabulated function i/o START");
        TabulatedFunction sourceFunction = TestFunctions.linkedListTabulatedFunction;
        TabulatedFunctions.outputTabulatedFunction(sourceFunction, testOutputStream);
        TabulatedFunction readFunction = TabulatedFunctions.inputTabulatedFunction(testInputStream);
        System.out.println("Read function from byte stream:");
        readFunction.print();

        TabulatedFunctions.writeTabulatedFunction(sourceFunction, testWriter);
        readFunction = TabulatedFunctions.readTabulatedFunction(testReader);
        System.out.println("Read function from symbol stream:");
        readFunction.print();
        System.out.println("Tast7: Test linked list tabulated function i/o END");
    }
}
