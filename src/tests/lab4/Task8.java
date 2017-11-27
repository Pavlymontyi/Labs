package tests.lab4;

import functions.Function;
import functions.TabulatedFunction;
import functions.TabulatedFunctions;
import functions.basic.Sin;

public class Task8 {

    public static void test() {
        System.out.println("Task8: Test tabulated function i/o START");
        Function sin = new Sin();
        TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(sin, 0, 2*Math.PI, 11);
        tabulatedSin.print();
        tabulatedSin.getFunctionValue(0);
        System.out.println("Task8: Test tabulated function i/o END");
    }
}
