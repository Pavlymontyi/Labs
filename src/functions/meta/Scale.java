package functions.meta;

import functions.Function;

public class Scale implements Function{
    private double kx;
    private double ky;
    private Function f;

    public Scale(double kx, double ky, Function f){
        this.kx = kx;
        this.ky = ky;
        this.f = f;
    }

    public double getLeftDomainBorder() {
        return f.getLeftDomainBorder()/kx;
    }

    public double getRightDomainBorder() {
        return f.getRightDomainBorder()/ky;
    }

    public double getFunctionValue(double x) {
        return f.getFunctionValue(x)/ky;
    }
}
