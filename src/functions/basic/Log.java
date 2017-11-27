package functions.basic;

import functions.Function;

public class Log implements Function{
    private double bas;

    public Log(double a){
        bas = a;
    }
    public double getLeftDomainBorder() {
        return Double.MIN_VALUE;
    }

    public double getRightDomainBorder() {
        return Double.MAX_VALUE;
    }

    public double getFunctionValue(double x) {
        return Math.log(x)/Math.log(bas);
    }
}
