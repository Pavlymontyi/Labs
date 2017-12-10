package gui.model;

import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TabulatedFunctionTableModel extends DefaultTableModel {
    static String[] columnNames = {"X", "Y"};
    TabulatedFunction tabulatedFunction;
    Component parentComponent;

    public TabulatedFunctionTableModel(TabulatedFunction tabulatedFunction, Component parentComponent) {
        super();
        this.tabulatedFunction = tabulatedFunction;
        this.parentComponent = parentComponent;
    }

    public int getRowCount() {
        if (tabulatedFunction == null) return 2; // to avoid NullPointerException, because it is called before set tabulatedFunction
        return tabulatedFunction.getPointsCounts();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int index) {
        return columnNames[index];
    }

    public Class getColumnClass(int index) {
        return Double.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return tabulatedFunction.getPointX(rowIndex);
        }
        else {
            return tabulatedFunction.getPointY(rowIndex);
        }
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            Double doubleValue = (Double) value;
            if (columnIndex == 0) {
                tabulatedFunction.setPointX(rowIndex, doubleValue);
            }
            else {
                tabulatedFunction.setPointY(rowIndex, doubleValue);
            }
        } catch (InappropriateFunctionPointException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(parentComponent, "Invalid value");
        }
    }
}
