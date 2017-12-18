package tests.lab8;

import functions.*;
import functions.basic.Cos;
import tests.lab4.TestFunctions;

import java.util.Iterator;

public class Task2 {

    public static void test(){
        System.out.println("Task2: Test factories START");
        System.out.println("Expected: Array (default factory), LinkedList, Array");
        Function f = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new
                LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new
                ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        System.out.println("Task2: Test ArrayTabulatedFactory END");
    }
}
