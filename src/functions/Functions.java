package functions;

import functions.meta.*;

public class Functions {
    private Functions(){}

    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(shiftX, shiftY, f);
    }
    public static Function scale(Function f, double scaleX, double scaleY){
        return new Shift(scaleX, scaleY, f);
    }
    public static Function power(Function b, Function d){
        return new Power(b, d);
    }
    public static Function sum(Function a, Function b){
        return new Sum(a, b);
    }
    public static Function mult(Function a, Function b){
        return new Mult(a, b);
    }
    public static Function composition(Function a, Function b){
        return new Composition(a, b);
    }
}
