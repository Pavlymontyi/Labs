package functions.meta;

import functions.Function;

import java.util.Collection;

public class Composition implements Function{
    private Function g;
    private Function f;

    public Composition(Function a, Function b){
        g = a;
        f = b;
    }

    public double getLeftDomainBorder() {
        return g.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return g.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return g.getFunctionValue(f.getFunctionValue(x));
    }
}
