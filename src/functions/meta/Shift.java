package functions.meta;

import functions.Function;

/**
 * Created by Laptop on 23.11.2017.
 */
public class Shift implements Function{
    private double sx;
    private double sy;
    private Function f;

    public Shift(double x, double y, Function f){
        sx = x;
        sy = y;
        this.f = f;
    }

    public double getLeftDomainBorder() {
        return f.getLeftDomainBorder() + sx;
    }

    public double getRightDomainBorder() {
        return f.getRightDomainBorder() + sx;
    }

    public double getFunctionValue(double x) {
        return f.getFunctionValue(x + sx) + sy;
    }
}
