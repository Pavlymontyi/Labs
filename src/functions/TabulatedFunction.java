package functions;

public interface TabulatedFunction extends Function, Iterable<FunctionPoint>{
    double getPointX(int index);
    double getPointY(int index);
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
    public void setPointX(int index, double x) throws InappropriateFunctionPointException;
    public void setPointY(int index, double y);
    FunctionPoint getPoint(int index);
    void deletePoint(int index);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
    int getPointsCounts();
    void print();
}
