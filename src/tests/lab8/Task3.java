package tests.lab8;

import functions.*;
import functions.basic.Sin;

import java.util.Scanner;

public class Task3 {
    public static void test() {
        System.out.println("Task3: Test reflection START");
        TabulatedFunction f;

        f = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class, 0, 10, 3);
        System.out.println(f.getClass());
        System.out.println(f);
        f.print();

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
        System.out.println(f.getClass());
        System.out.println(f);
        f.print();

        FunctionPoint[] points = new FunctionPoint[] {
                new FunctionPoint(0, 0),
                new FunctionPoint(10, 10)
        };
        f = TabulatedFunctions.createTabulatedFunction(LinkedListTabulatedFunction.class, points);
        System.out.println(f.getClass());
        System.out.println(f);
        f.print();

        f = TabulatedFunctions.tabulate(ArrayTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f.getClass());
        System.out.println(f);
        f.print();

        System.out.println("Task3: Test reflection END\n");
    }
}
