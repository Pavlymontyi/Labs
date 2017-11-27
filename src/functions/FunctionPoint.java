package functions;

public class FunctionPoint {
    public double x;
    public double y;
    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    public FunctionPoint(FunctionPoint point){
        x = point.x;
        y = point.y;
    }
    public FunctionPoint(){
        x = 0;
        y = 0;
    }
}

