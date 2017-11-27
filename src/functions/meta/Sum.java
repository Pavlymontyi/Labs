package functions.meta;

import functions.Function;

public class Sum implements Function{
    private Function a;
    private Function b;

    public Sum(Function a, Function b){
        this.a = a;
        this.b = b;
    }

    public double getLeftDomainBorder() {
        if(a.getLeftDomainBorder() < b.getLeftDomainBorder()) return b.getLeftDomainBorder();
        else return a.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        if(a.getRightDomainBorder() < b.getRightDomainBorder()) return a.getRightDomainBorder();
        else return b.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return a.getFunctionValue(x)+b.getFunctionValue(x);
    }
}
