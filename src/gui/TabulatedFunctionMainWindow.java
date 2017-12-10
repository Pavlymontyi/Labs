package gui;

import classloader.TabulatedFunctionClassLoader;
import functions.*;
import gui.documents.DocumentTabulatedFunction;
import gui.model.TabulatedFunctionTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class TabulatedFunctionMainWindow extends JFrame {

    private JPanel rootPanel;
    private JPanel menuPanel;
    private JPanel buttonsPanel;
    private JPanel tablePanel;

    private JTextField newPointXTextField;
    private JTextField newPointYTextField;
    private JButton addPointButton;
    private JButton deletePointButton;
    private JTable currentFunctionTable;
    private JMenuBar menuBar;
    private JLabel assignedFileNameLabel;

    private TabulatedFunctionTableModel tableModel;
    private TabulatedFunctionInputDialog inputDialog;
    private DocumentTabulatedFunction documentFunction;
    private JFileChooser fileChooser;
    private JFileChooser classFileChooser;
    private TabulatedFunctionClassLoader classLoader;

    public TabulatedFunctionMainWindow() {
        setContentPane(rootPanel);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onExit();
            }
        });
        inputDialog = new TabulatedFunctionInputDialog();
        documentFunction = new DocumentTabulatedFunction(assignedFileNameLabel);
        documentFunction.newFunction(0, 2, 3);
        classLoader = new TabulatedFunctionClassLoader();
        tableModel = new TabulatedFunctionTableModel(documentFunction, this);
        currentFunctionTable.setModel(tableModel);
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f == null || f.isDirectory()) return false;
                if (f.getName().indexOf('.') == -1) return false;
                if ("tbf".equalsIgnoreCase(f.getName().substring(f.getName().indexOf('.')+1))) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return ".tbf - tabulated function files";
            }
        });
        classFileChooser = new JFileChooser();
        classFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f == null || f.isDirectory()) return false;
                if (f.getName().indexOf('.') == -1) return false;
                if ("class".equalsIgnoreCase(f.getName().substring(f.getName().indexOf('.')+1))) {
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return ".class - bytecode files";
            }
        });
        //todo: replace this
        classFileChooser.setCurrentDirectory(new File(
                "C:\\Pavel\\Views\\Kraynov\\Labs\\out\\production\\Labs\\functions\\basic\\"));
        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAddPointButton();
            }
        });
        deletePointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDeletePointButton();
            }
        });

    }

    public static void main(String[] args) {
        TabulatedFunctionMainWindow form = new TabulatedFunctionMainWindow();
        form.setVisible(true);
        form.pack();
    }


    private void createUIComponents() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newFileMenuItem = new JMenuItem("New");
        JMenuItem loadFileMenuItem = new JMenuItem("Load");
        JMenuItem saveFileMenuItem = new JMenuItem("Save");
        JMenuItem saveAsFileMenuItem = new JMenuItem("Save as");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newFileMenuItem);
        fileMenu.add(loadFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.add(saveAsFileMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);


        JMenuItem tabulateMenuItem = new JMenuItem("Tabulate");
        JMenu tabulateMenu = new JMenu("Tabulate");
        tabulateMenu.add(tabulateMenuItem);
        menuBar.add(tabulateMenu);

        newFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNewFile();
            }
        });
        saveAsFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveAs();
            }
        });
        saveFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });
        loadFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoad();
            }
        });
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });
        tabulateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onTabulate();
            }
        });

    }

    private void onNewFile() {
        int dialogStatus = inputDialog.showDialog();
        if (dialogStatus == TabulatedFunctionInputDialog.OK) {
            documentFunction.newFunction(inputDialog.getLeftBorderValue(), inputDialog.getRightBorderValue(), inputDialog.getPointsCountValue());
            currentFunctionTable.revalidate();
            currentFunctionTable.repaint();
        }
    }

    private void onSaveAs() {
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().contains(".")) {
                selectedFile = new File(selectedFile.getAbsolutePath()+".tbf");
            }
            System.out.println(selectedFile);
            try {
                documentFunction.saveFunctionAs(selectedFile.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void onSave() {
        if (documentFunction.fileNameAssigned()) {
            try {
                documentFunction.saveFunction();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else {
            onSaveAs();
        }
    }

    private void onLoad() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile);
            try {
                documentFunction.loadFunction(selectedFile.getAbsolutePath());
                currentFunctionTable.revalidate();
                currentFunctionTable.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void onExit() {
        if (documentFunction.modified()) {
            int returnVal = JOptionPane.showConfirmDialog(this,
                    "There is unsaved changes. Are you sure you want exit?", "Closing", JOptionPane.YES_NO_OPTION);
            System.out.println(returnVal);
            if (returnVal == JOptionPane.NO_OPTION ) {
                return;
            }
        }
        dispose();
        System.exit(0);
    }

    private void onAddPointButton() {
        boolean isError = false;
        String newPointX = newPointXTextField.getText();
        String newPointY = newPointYTextField.getText();
        Double x = null, y = null;
        try {
            x = Double.valueOf(newPointX);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid value of x: " + newPointX);
            isError = true;
        }
        try {
            y = Double.valueOf(newPointY);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid value of y: " + newPointY);
            isError = true;
        }

        if (!isError) {
            try {
                documentFunction.addPoint(new FunctionPoint(x, y));
            } catch (InappropriateFunctionPointException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
                e.printStackTrace();
            }
            currentFunctionTable.revalidate();
            currentFunctionTable.repaint();
        }
    }

    private void onDeletePointButton() {
        try {
            int row = currentFunctionTable.getSelectedRow();
            if (row != -1) {
                documentFunction.deletePoint(row);
                currentFunctionTable.revalidate();
                currentFunctionTable.repaint();
            }
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "2 points should be in function at least ");
        }
    }

    private void onTabulate() {
        int returnVal = classFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = classFileChooser.getSelectedFile();
            System.out.println(selectedFile);
            int dialogStatus = inputDialog.showDialog();
            if (dialogStatus == TabulatedFunctionInputDialog.OK) {
                try {
                    Class functionClass = classLoader.loadClassFromFile(selectedFile.getAbsolutePath());
                    Function function = (Function) functionClass.newInstance();
                    System.out.println(function);
                    documentFunction.tabulateFunction(function, inputDialog.getLeftBorderValue(),
                            inputDialog.getRightBorderValue(), inputDialog.getPointsCountValue());
                    currentFunctionTable.revalidate();
                    currentFunctionTable.repaint();
                }  catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this,
                            "Can't read bytecode from file: "+selectedFile);
                } catch (IllegalAccessException | InstantiationException e) {
                    JOptionPane.showMessageDialog(this,
                            "Can't instantiate function: "+ documentFunction.getClass().getName());
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    JOptionPane.showMessageDialog(this,
                            "Class is not implement interface Function: "+documentFunction.getClass().getName());
                    e.printStackTrace();
                }
            }
        }
    }

}
