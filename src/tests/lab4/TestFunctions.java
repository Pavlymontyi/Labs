package tests.lab4;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

public class TestFunctions {

    public static TabulatedFunction arrayTabulatedFunction;
    public static TabulatedFunction linkedListTabulatedFunction;

    static {
        double[] a = {16, 9, 4, 1, 0, 1, 4, 9, 16};
        arrayTabulatedFunction = new ArrayTabulatedFunction(-4, 4, a);
        linkedListTabulatedFunction = new LinkedListTabulatedFunction(-4, 4, a);
    }

}
