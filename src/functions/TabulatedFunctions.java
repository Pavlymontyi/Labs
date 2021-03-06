package functions;

import sun.reflect.misc.ConstructorUtil;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {
    private static TabulatedFunctionFactory tabulatedFunctionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    private TabulatedFunctions(){}

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if(leftX <= function.getLeftDomainBorder() || rightX >= function.getRightDomainBorder()){
            throw new IllegalArgumentException("interval is out of domain");
        }
        double[] values = new double[pointsCount];
        double x = leftX;
        double interval = (rightX-leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            values[i] = function.getFunctionValue(x);
            x+=interval;
        }
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> tabulatedFunctionClass,
                                             Function function, double leftX, double rightX, int pointsCount) {
        if(leftX <= function.getLeftDomainBorder() || rightX >= function.getRightDomainBorder()){
            throw new IllegalArgumentException("interval is out of domain");
        }
        double[] values = new double[pointsCount];
        double x = leftX;
        double interval = (rightX-leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            values[i] = function.getFunctionValue(x);
            x+=interval;
        }
        return createTabulatedFunction(tabulatedFunctionClass, leftX, rightX, values);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out){
        DataOutputStream outputStream = null;
        try{
            outputStream = new DataOutputStream(out);
            outputStream.writeInt(function.getPointsCounts());
            for (int i=0; i < function.getPointsCounts(); i++) {
                FunctionPoint fp = function.getPoint(i);
                outputStream.writeDouble(fp.x);
                outputStream.writeDouble(fp.y);
            }
            outputStream.flush();
            //outputStream.close();
        } catch (IOException ex) {
            throw new RuntimeException("Error during i/o function", ex);
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) {
        TabulatedFunction result = null;
        try {
            DataInputStream inputStream = new DataInputStream(in);
            int length = inputStream.readInt();
            FunctionPoint[] readPoints = new FunctionPoint[length];
            for (int i=0; i < length; i++) {
                FunctionPoint fp = new FunctionPoint(inputStream.readDouble(), inputStream.readDouble());
                readPoints[i] = fp;
            }
            result = tabulatedFunctionFactory.createTabulatedFunction(readPoints);
        } catch (IOException ex) {
            throw new RuntimeException("Error during i/o function", ex);
        }
        return result;
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        try{
            BufferedWriter writer = new BufferedWriter(out);
            writer.write(String.valueOf(function.getPointsCounts()));
            for (int i=0; i < function.getPointsCounts(); i++) {
                FunctionPoint fp = function.getPoint(i);
                writer.write(" " + fp.x + " " + fp.y);
            }
            writer.flush();
        } catch (IOException ex) {
            throw new RuntimeException("Error during i/o function", ex);
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) {
        TabulatedFunction result = null;
        try{
            StreamTokenizer tokenizer = new StreamTokenizer(in);
            tokenizer.nextToken();
            int length = (int) tokenizer.nval;
            FunctionPoint[] readPoints = new FunctionPoint[length];
            for (int i=0; i < length; i++) {
                tokenizer.nextToken();
                double x = tokenizer.nval;
                tokenizer.nextToken();
                double y = tokenizer.nval;
                FunctionPoint fp = new FunctionPoint(x, y);
                readPoints[i] = fp;
            }
            result = tabulatedFunctionFactory.createTabulatedFunction(readPoints);
        } catch (IOException ex) {
            throw new RuntimeException("Error during i/o function", ex);
        }
        return result;
    }

    public static void serialize(TabulatedFunction function, String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(function);
            out.flush();
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException("Error during serialiation function", ex);
        }
    }

    public static TabulatedFunction deserialize(String fileName) {
        TabulatedFunction func;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            func = (TabulatedFunction) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Error during deserialiation function", ex);
        }
        return func;
    }

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory){
        System.out.println("Setting factory: "+factory.getClass());
        tabulatedFunctionFactory = factory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return tabulatedFunctionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return tabulatedFunctionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> tabulatedFunctionClass,
                                                            double leftX,
                                                            double rightX,
                                                            int pointsCount) {
        TabulatedFunction newInstance = null;
        try {
            Constructor constructor = tabulatedFunctionClass.getConstructor(double.class, double.class, int.class);
            newInstance = (TabulatedFunction) constructor.newInstance(leftX, rightX, pointsCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return newInstance;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> tabulatedFunctionClass,
                                                            double leftX,
                                                            double rightX,
                                                            double[] values) {
        TabulatedFunction newInstance = null;
        try {
            Constructor constructor = tabulatedFunctionClass.getConstructor(double.class, double.class, double[].class);
            newInstance = (TabulatedFunction) constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return newInstance;
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> tabulatedFunctionClass,
                                                            FunctionPoint[] points) {
        TabulatedFunction newInstance = null;
        try {
            Constructor constructor = tabulatedFunctionClass.getConstructor(FunctionPoint[].class);
            System.out.println(constructor);
            newInstance = (TabulatedFunction) constructor.newInstance(new Object[] {points});
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return newInstance;
    }
}
