package functions;

import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions(){}

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if(leftX <= function.getLeftDomainBorder() || rightX >= function.getRightDomainBorder()){
            throw new IllegalArgumentException("interval is out of domain");
        }
        double[] values = new double[pointsCount];
        double x = 0;
        double interval = (rightX-leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            values[i] = function.getFunctionValue(x);
            x+=interval;
        }
        return new LinkedListTabulatedFunction(leftX, rightX, values);
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
            //outputStream.close(); //todo: need to close??
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
            result = new ArrayTabulatedFunction(readPoints);
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
            result = new ArrayTabulatedFunction(readPoints);
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
}
