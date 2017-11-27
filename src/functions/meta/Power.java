package functions.meta;

import functions.Function;

public class Power implements Function{
    private Function bas;
    private Function deg;

    public Power(Function b, Function d){
        bas = b;
        deg = d;
    }

    public double getLeftDomainBorder() {
        return bas.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return bas.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return Math.pow(bas.getFunctionValue(x), deg.getFunctionValue(x));
    }
}
