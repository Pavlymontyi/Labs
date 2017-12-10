package gui;

import javax.swing.*;
import java.awt.event.*;

public class TabulatedFunctionInputDialog extends JDialog {
    public static final int OK = 1;
    public static final int CANCEL = 2;
    private Validator validator = new Validator();

    private int status;
    private Double leftBorderValue;
    private Double rightBorderValue;
    private Integer pointsCountValue;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField leftBorderTextField;
    private JTextField rightBorderTextField;
    private JSpinner pointsCountSpinner;
    private JLabel pointsCountLabel;
    private JLabel rightDomainBorderLabel;
    private JLabel leftDomainBorderLabel;

    public TabulatedFunctionInputDialog() {
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        setTitle("Function parameters");
        getRootPane().setDefaultButton(buttonOK);
        System.out.println(pointsCountSpinner.getModel().getClass());
        SpinnerNumberModel model = (SpinnerNumberModel) pointsCountSpinner.getModel();
        model.setValue(11);
        model.setMinimum(2);
        model.setStepSize(1);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                status = CANCEL;
                //onCancel(); this is not needed because defaultCloseOperation was set to HIDE_ON_CLOSE
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
    }

    private void onOK() {
        String errorMessage = validator.validateAndGetErrorMessage();
        if (errorMessage != null && !"".equals(errorMessage)) {
            JOptionPane.showMessageDialog(this, errorMessage);
            status = CANCEL;
            return;
        }
        setVisible(false);
        status = OK;
    }

    private void onCancel() {
        setVisible(false);
        status = CANCEL;
    }

    public int showDialog() {
        this.setVisible(true);
        return status;
    }

    public Double getLeftBorderValue() {
        return leftBorderValue;
    }

    public Double getRightBorderValue() {
        return rightBorderValue;
    }

    public Integer getPointsCountValue() {
        return pointsCountValue;
    }

    private class Validator{

        public String validateAndGetErrorMessage() {
            StringBuilder sb = new StringBuilder();
            validateLeftBorder(sb);
            validateRightBorder(sb);
            if (sb.length() == 0) {
                //if values were parsed successfully
                validateCompareLeftAndRight(sb);
            }
            validatePointsCount(sb);
            if (sb.length() > 0) sb.delete(0, 1); //delete first '\n'
            return sb.toString();
        }

        private void validateCompareLeftAndRight(StringBuilder sb) {
            if (rightBorderValue.compareTo(leftBorderValue) <= 0) {
                sb.append("\nLeft border value should be less than right border value");
            }
        }

        private void validateLeftBorder(StringBuilder sb) {
            String text = leftBorderTextField.getText();
            try {
                Double value = Double.parseDouble(text);
                leftBorderValue = value;
            } catch (NumberFormatException ex) {
                sb.append("\nPlease change value for left border: '").append(text).append("' is not valid value");
                System.err.println(ex);
            }
        }

        private void validateRightBorder(StringBuilder sb) {
            String text = rightBorderTextField.getText();
            try {
                Double value = Double.parseDouble(text);
                rightBorderValue = value;
            } catch (NumberFormatException ex) {
                sb.append("\nPlease change value for right border: '").append(text).append("' is not valid value");
                System.err.println(ex);
            }
        }

        private void validatePointsCount(StringBuilder sb) {
            Object value = pointsCountSpinner.getValue();
            if (!(value instanceof Integer)) {
                sb.append("\nPlease change value for points count: '").append(value).append("' is not valid value");
            } else {
                pointsCountValue = (Integer) value;
            }
        }
    }

    public static void main(String[] args) {
        TabulatedFunctionInputDialog dialog = new TabulatedFunctionInputDialog();
        dialog.showDialog();
        System.exit(0);
    }
}
