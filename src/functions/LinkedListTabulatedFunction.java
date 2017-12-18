package functions;

import java.io.*;
import java.util.Iterator;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {

    private class FunctionNode implements Serializable {

        FunctionPoint point;
        FunctionNode previous;
        FunctionNode next;

        FunctionNode(){
            point = new FunctionPoint();
            previous = this;
            next = this;
        }

        FunctionNode(FunctionPoint point){
            this.point = point;
        }
    }

    private FunctionNode head;
    private int length;
    private FunctionNode lastPoint;
    private int indexOfLastPoint;

    public LinkedListTabulatedFunction() {
        head = new FunctionNode();
        length = 0;
        indexOfLastPoint = 0;
        lastPoint = head;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if(leftX > rightX) throw new IllegalArgumentException("leftX > rightX!");
        if(pointsCount < 2) throw new IllegalArgumentException("uncorrect count of points");
        head = new FunctionNode();
        length = 0;
        indexOfLastPoint = 0;
        lastPoint = head;
        double interval = (rightX - leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            addNodeToTail(new FunctionPoint(leftX+i*interval, 0));
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        int pointsCount = values.length;
        if(leftX > rightX) throw new IllegalArgumentException("leftX > rightX!");
        if(pointsCount < 2) throw new IllegalArgumentException("uncorrect count of points");
        head = new FunctionNode();
        length = 0;
        indexOfLastPoint = 0;
        lastPoint = head;
        double interval = (rightX - leftX)/(pointsCount-1);
        for(int i = 0; i < pointsCount; i++){
            addNodeToTail(new FunctionPoint(leftX+i*interval, values[i]));
        }
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points){
        if(points.length < 2){
            throw new IllegalArgumentException("points count < 2");
        }
        head = new FunctionNode();
        length = 0;
        indexOfLastPoint = 0;
        lastPoint = head;
        double curX = points[0].x;
        addNodeToTail(points[0]);
        for(int i = 1; i < points.length; i++){
            if(points[i].x < curX) throw new IllegalArgumentException("points in array unordered");
            else {
                curX = points[i].x;
                addNodeToTail(points[i]);
            }
        }
    }

    public double getLeftDomainBorder(){
        return head.next.point.x;
    }

    public double getPointX(int index){
        if(index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).point.x;
    }

    public double getRightDomainBorder(){ return head.previous.point.x; }

    private FunctionNode addNodeToTail(FunctionPoint point) {
        FunctionNode node = new FunctionNode(point);
        node.next = head;
        node.previous = head.previous;
        head.previous.next = node;
        head.previous = node;
        length++;
        return node;
    }

    private FunctionNode addNodeByIndex(int index, FunctionPoint point){
        if(index < 0 || index > length) throw new FunctionPointIndexOutOfBoundsException();
        FunctionNode node = new FunctionNode(point);
        FunctionNode tmp = getNodeByIndex(index);
        node.previous = tmp.previous;
        tmp.previous.next = node;
        node.next = tmp;
        tmp.previous = node;
        length++;
        return node;
    }

    public double getPointY(int index){
        if(index < 0 || index > length-1) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).point.y;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
       checkExistingPointsX(point.x);
        if(point.x > getRightDomainBorder()){
            addNodeToTail(point);
        }
        else
        if(point.x < getLeftDomainBorder()){
            System.out.println("LeftBorder: " + getLeftDomainBorder());
            FunctionNode node = new FunctionNode(point);
            node.next = head.next;
            getNodeByIndex(0).previous = node;
            head.next = node;
            node.previous = head;
            length++;
        }
        else {
            int index = 0;
            while (point.x > getPointX(index)) {
                index++;
            }
            addNodeByIndex(index, point);
        }
    }

    private void checkExistingPointsX(double x) throws InappropriateFunctionPointException {
        int i = 0;
        while (i < length) {
            if(x == getPointX(i)){
                throw new InappropriateFunctionPointException();
            }
            i++;
        }
    }

    @Override
    public int getPointsCounts() {
        return length;
    }

    public FunctionNode getNodeByIndex(int i){
        FunctionNode cur;
        if(indexOfLastPoint == 0){
            cur = head.next;
            for(int j = 0; j < i; j++){
                cur = cur.next;
            }
        }
        else {
            if (i < indexOfLastPoint) {
                if (i < indexOfLastPoint - i) {
                    cur = head.next;
                    for (int j = 0; j < i; j++) {
                        cur = cur.next;
                    }
                } else {
                    cur = lastPoint;
                    for (int j = indexOfLastPoint; j > i; j--) {
                        cur = cur.previous;
                    }
                }
            } else {
                if (length - i < i - indexOfLastPoint) {
                    cur = head.next;
                    for (int j = length; j > i; j--) {
                        cur = cur.previous;
                    }
                } else {
                    cur = lastPoint;
                    for (int j = indexOfLastPoint; j < i; j++) {
                        cur = cur.next;
                    }
                }
            }
        }
        indexOfLastPoint = i;
        lastPoint = cur;
        return cur;
    }

    public void setPointY(int index, double y){
        if(index < 0 || index > length - 1) throw new FunctionPointIndexOutOfBoundsException();
        getNodeByIndex(index).point.y = y;
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if(index < 0 || index > length-1) throw new FunctionPointIndexOutOfBoundsException();
        if(index == 0){
            if(x > getPointX(index+1)){
                throw new InappropriateFunctionPointException();
            }
        }
        else {
            if (index == length - 1) {
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
        getNodeByIndex(index).point.x = x;
    }

    public FunctionPoint getPoint(int index){
        if(index < 0 || index > length-1) throw new FunctionPointIndexOutOfBoundsException();
        return getNodeByIndex(index).point;
    }

    public double getFunctionValue(double x){
        if(x < getLeftDomainBorder() || x > getRightDomainBorder()){ return Double.NaN;}
        if(x == getLeftDomainBorder()){ return getPointY(0);}
        if(x == getRightDomainBorder()){ return getPointY(length-1);}
        int i = 1;
        while(x > getPointX(i)){
            i++;
        }
        FunctionPoint right = getPoint(i);
        FunctionPoint left = getPoint(i-1);
        double k = (right.y - left.y)/(right.x - left.x);
        return k * (x - left.x) + left.y;
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if(index < 0 || index > length-1) throw new FunctionPointIndexOutOfBoundsException();
        if(index == 0){
            if(point.x > getPointX(index+1)){
                throw new InappropriateFunctionPointException();
            }
        }
        else {
            if (index == length-1) {
                if (point.x < getPointX(index - 1)) {
                    throw new InappropriateFunctionPointException();
                }
                //addNodeToTail(point);
            }
            else {
                if (point.x < getPointX(index-1) || point.x > getPointX(index + 1)){
                    throw new InappropriateFunctionPointException();
                }
            }
        }
        FunctionNode node = new FunctionNode(point);
        node.next = getNodeByIndex(index);
        node.previous = getNodeByIndex(index).previous;
        getNodeByIndex(index).previous = node;
        getNodeByIndex(index-1).next = node;
    }

    public void deletePoint(int index){
        if(length < 3) throw new IllegalStateException();
        FunctionNode prev = getNodeByIndex(index-1);
        FunctionNode next = getNodeByIndex(index+1);
        prev.next = next;
        next.previous = prev;
        length--;
    }

    public void print(){
        for(int i = 0; i < length; i++){
            System.out.println(i + ") x: " + getPointX(i) + "; y: " + getPointY(i));
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            FunctionNode currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode.next != head;
            }

            @Override
            public Object next() {
                currentNode = currentNode.next;
                return currentNode.point;
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException("remove");
            }
        };
    }

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
            return new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
            return new LinkedListTabulatedFunction(leftX, rightX, values);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }
    }
}
