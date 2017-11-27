package functions;


public class ArrayTabulatedFunction implements TabulatedFunction{

    private FunctionPoint[] pointsArray;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if(leftX > rightX) throw new IllegalArgumentException("leftX > rightX!");
        if(pointsCount < 2) throw new IllegalArgumentException("uncorrect count of points");
        pointsArray = new FunctionPoint[pointsCount*2];
        double interval = (rightX - leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            pointsArray[i] = new FunctionPoint(leftX+i*interval, 0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        int pointsCount = values.length;
        if(leftX > rightX) throw new IllegalArgumentException("leftX > rightX");
        if(pointsCount < 2) throw new IllegalArgumentException("points count < 2");
        pointsArray = new FunctionPoint[pointsCount*5];
        double interval = (rightX - leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            pointsArray[i] = new FunctionPoint(leftX+i*interval, values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] points){
        if(points.length < 2){
            throw new IllegalArgumentException("points count < 2");
        }
        double curX = points[0].x;
        for(int i = 1; i < points.length; i++){
            if(points[i].x < curX) throw new IllegalArgumentException("points in array unordered");
            curX = points[i].x;
        }
        pointsArray = points;
    }

    public double getLeftDomainBorder(){
        return getPointX(0);
    }

    public double getRightDomainBorder(){ return getPointX(getPointsCounts()-1); }

    public double getPointX(int index){
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        return pointsArray[index].x;
    }

    public double getPointY(int index){
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        return pointsArray[index].y;
    }

    public FunctionPoint getPoint(int index){
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        return pointsArray[index];
    }

    public double getFunctionValue(double x){
        if(x < getLeftDomainBorder() || x > getRightDomainBorder()){ return Double.NaN;}
        if(x == getLeftDomainBorder()){ return getPointY(0);}
        if(x == getRightDomainBorder()){ return getPointY(getPointsCounts()-1);}
        int i = 1;
        while(x > getPointX(i)){
            i++;
        }
        FunctionPoint right = getPoint(i);
        FunctionPoint left = getPoint(i-1);
        double k = (right.y - left.y)/(right.x - left.x);
        return k * (x - left.x) + left.y;
    }

    public int getPointsCounts(){
        int i = 0;
        if(pointsArray[pointsArray.length-1] != null){
            return pointsArray.length;
        }
        while(pointsArray[i] != null){
            i++;
        }
        return i;
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        if(index == 0){
            if(point.x > getPointX(index)){
                throw new InappropriateFunctionPointException();
            }
        }
        else {
            if (index == getPointsCounts()-1) {
                if (point.x < getPointX(index - 1)) {
                    throw new InappropriateFunctionPointException();
                }
            }
            else {
                if (point.x < getPointX(index-1) || point.x > getPointX(index+1)){
                    throw new InappropriateFunctionPointException();
                }
            }
        }
        pointsArray[index] = point;
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        if(index == 0){
            if(x > getPointX(index+1)){
                throw new InappropriateFunctionPointException();
            }
        }
        else {
            if (index == getPointsCounts()- 1) {
                if (x < getPointX(index-1)) {
                    throw new InappropriateFunctionPointException();
                }
            }
            else {
                if (x < getPointX(index-1) || x > getPointX(index+1)){
                    throw new InappropriateFunctionPointException();
                }
            }
        }
        pointsArray[index].x = x;
    }

    public void setPointY(int index, double y){
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        pointsArray[index].y = y;
    }

    public void deletePoint(int index){
        if(getPointsCounts() < 3) throw new IllegalStateException();
        if(index < 0 || index > getPointsCounts()-1) throw new FunctionPointIndexOutOfBoundsException();
        int i = 0;
        int count = getPointsCounts() - index - 1;
        //int count = pointsArray.length - getPointsCounts();
        while(i < count) {
            pointsArray[index + i] = getPoint(index + i + 1);
            i++;
        }
        pointsArray[index + i] = null;
    }

    public void addPoint(FunctionPoint point){
        int a = 0;
        if(pointsArray.length == getPointsCounts()){
            FunctionPoint[] newArray = new FunctionPoint[getPointsCounts()*2];
            System.arraycopy(pointsArray,0, newArray, 0, getPointsCounts());
            pointsArray = newArray;
            System.out.println("Rebuild array.");
        }
        int index = 0;
        if (point.x > getPointX(getPointsCounts() - 1)) {
            pointsArray[getPointsCounts()] = point;
        } else {
            while (getPointX(index) < point.x) {
                index++;
            }
            int count = getPointsCounts() - index;
            int i = 0;
            while (i < count) {
                pointsArray[getPointsCounts() - i] = pointsArray[getPointsCounts() - 1 - i];
                i++;
            }
            pointsArray[index] = point;
        }
    }

    public void print(){
        int i = 0;
        while(i < getPointsCounts()){
            System.out.println(i + ") x: " + getPointX(i) + "; y: " + getPointY(i));
            i++;
        }
        System.out.println();
    }

}
