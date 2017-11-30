package tests.lab4;

import functions.TabulatedFunction;
import functions.TabulatedFunctions;

public class Task9 {
    private static final String serializationFileName = "serialTabFunc.txt";
    private static final String serializationFileNameExtern = "serialExternTabFunc.txt";

    public static void test() {
        System.out.println("Task9: Part1: Serializable: Test tabulated function (de)serialization mechanism START");
        TestFunctions.linkedListTabulatedFunction.print();
        TabulatedFunctions.serialize(TestFunctions.linkedListTabulatedFunction, serializationFileName);
        TabulatedFunction deserializedFunc = TabulatedFunctions.deserialize(serializationFileName);
        System.out.println("Deser: ");
        deserializedFunc.print();
        System.out.println("Task9: Part1: Serializable: Test tabulated function (de)serialization mechanism END");
        System.out.println("Task9: Part2: Serializable: Test tabulated function (de)serialization mechanism START");
        TestFunctions.arrayTabulatedFunction.print();
        TabulatedFunctions.serialize(TestFunctions.arrayTabulatedFunction, serializationFileNameExtern);
        TabulatedFunction deserializedFunc2 = TabulatedFunctions.deserialize(serializationFileNameExtern);
        System.out.println("Deser: ");
        deserializedFunc2.print();
        System.out.println("Task9: Part2: Serializable: Test tabulated function (de)serialization mechanism END");
    }
}
