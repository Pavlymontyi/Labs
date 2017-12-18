package tests.lab8;

import functions.FunctionPoint;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import tests.lab4.TestFunctions;

import java.util.Iterator;

public class Task1 {

    public static void test() {
        System.out.println("Task1: Test iterator for Array START");
        TabulatedFunction testFunction = TestFunctions.arrayTabulatedFunction;
        for (FunctionPoint fp : testFunction){
            System.out.println(fp.x+"; "+fp.y);
        }

        System.out.println("Task1: Test iterator for Array: Modify Function and print function using iterator:");
        modifyTestFunction(testFunction);
        Iterator<FunctionPoint> iterator = testFunction.iterator();
        while (iterator.hasNext()){
            FunctionPoint fp = iterator.next();
            System.out.println(fp.x+"; "+fp.y);
        }
        System.out.println("Task1: Test iterator for Array END");

        System.out.println("Task1: Test iterator for LinkedList START");
        testFunction = TestFunctions.linkedListTabulatedFunction;
        for (FunctionPoint fp : testFunction){
            System.out.println(fp.x+"; "+fp.y);
        }

        System.out.println("Task1: Test iterator for LinkedList: Modify Function and print function using iterator:");
        modifyTestFunction(testFunction);
        iterator = testFunction.iterator();
        while (iterator.hasNext()){
            FunctionPoint fp = iterator.next();
            System.out.println(fp.x+"; "+fp.y);
        }
        System.out.println("Task1: Test iterator for LinkedList END\n");
    }

    private static void modifyTestFunction(TabulatedFunction testFunction) {
        try {
            testFunction.addPoint(new FunctionPoint(12, 13));
            testFunction.deletePoint(0);
            testFunction.deletePoint(4);
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        }
    }
}
