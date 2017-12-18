package gui.documents;

import functions.*;

import javax.swing.*;
import java.util.Iterator;

public class DocumentTabulatedFunction implements TabulatedFunction {

    private JLabel assignedFileLabel;

    private TabulatedFunction tabulatedFunction;
    private boolean modified; //changed from last document save
    private String documentName;

    public DocumentTabulatedFunction(JLabel assignedFileLabel) {
        this.assignedFileLabel = assignedFileLabel;
    }

    private void updateAssignedFileLabel() {
        String result = documentName;
        if (result != null && !result.contains("*") && modified) {
            result += " *";
        }
        assignedFileLabel.setText(result);
    }

    public void tabulateFunction(Function func, double leftBorder, double rightBorder, int pointsCount){
        tabulatedFunction = TabulatedFunctions.tabulate(func, leftBorder, rightBorder, pointsCount);
        documentName = null;
        modified = false;
        updateAssignedFileLabel();
    }

    public void newFunction(double leftX, double rightX, int pointsCount) {
        tabulatedFunction = TabulatedFunctions.createTabulatedFunction(leftX, rightX, pointsCount);
        documentName = null;
        modified = false;
        updateAssignedFileLabel();
    }

    public void saveFunction() {
        TabulatedFunctions.serialize(tabulatedFunction, documentName);
        modified = false;
        updateAssignedFileLabel();
    }

    public void saveFunctionAs(String fileName) {
        TabulatedFunctions.serialize(tabulatedFunction, fileName);
        documentName = fileName;
        modified = false;
        updateAssignedFileLabel();
    }

    public void loadFunction(String fileName) {
        tabulatedFunction = TabulatedFunctions.deserialize(fileName);
        documentName = fileName;
        modified = false;
        updateAssignedFileLabel();
    }

    public boolean modified() {
        return modified;
    }

    public boolean fileNameAssigned() {
        return documentName != null && !"".equals(documentName);
    }

    @Override
    public double getLeftDomainBorder() {
        return tabulatedFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return tabulatedFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return tabulatedFunction.getFunctionValue(x);
    }

    @Override
    public double getPointX(int index) {
        return tabulatedFunction.getPointX(index);
    }

    @Override
    public double getPointY(int index) {
        return tabulatedFunction.getPointY(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        tabulatedFunction.setPoint(index, point);
        modified = true;
        updateAssignedFileLabel();
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        tabulatedFunction.setPointX(index, x);
        modified = true;
        updateAssignedFileLabel();
    }

    @Override
    public void setPointY(int index, double y) {
        tabulatedFunction.setPointY(index, y);
        modified = true;
        updateAssignedFileLabel();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return tabulatedFunction.getPoint(index);
    }

    @Override
    public void deletePoint(int index) {
        tabulatedFunction.deletePoint(index);
        modified = true;
        updateAssignedFileLabel();
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        tabulatedFunction.addPoint(point);
        modified = true;
        updateAssignedFileLabel();
    }

    @Override
    public int getPointsCounts() {
        return tabulatedFunction.getPointsCounts();
    }

    @Override
    public void print() {
        tabulatedFunction.print();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return tabulatedFunction.iterator();
    }
}
