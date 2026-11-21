import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorExtras {

    private JFrame parent;
    private JTextField txtDisplay;
    private HistoryDialog historyDialog;

    private String lastAnswer = "0";
    private int acPresses = 0;
    private boolean operationCleared = false;

    public CalculatorExtras(JFrame parent, JTextField txtDisplay) {
        this.parent = parent;
        this.txtDisplay = txtDisplay;
        this.historyDialog = new HistoryDialog(parent, txtDisplay);
    }


    // call after every successful "="
    public void recordCalculation(String expression, String result) {
        lastAnswer = result;
        historyDialog.addEntry(expression + " = " + result);
    }

    // call from every non-AC button so AC only counts consecutive presses
    public void resetAC() {
        acPresses = 0;
    }

    // true when AC was pressed twice in a row (clear the pending operation)
    public boolean isOperationCleared() {
        return operationCleared;
    }


    public void btnAnsClick(ActionEvent evt) {
        resetAC();
        txtDisplay.setText(lastAnswer);
    }

    public void btnRndClick(ActionEvent evt) {
        resetAC();
        txtDisplay.setText(format(Math.random()));
    }

    public void btnACClick(ActionEvent evt) {
        acPresses++;
        txtDisplay.setText("0");
        if (acPresses >= 2) {
            operationCleared = true;
            acPresses = 0;
        }
        else {
            operationCleared = false;
        }
    }

    public void btnHistoryClick(ActionEvent evt) {
        resetAC();
        if (historyDialog.isVisible()) {
            historyDialog.setVisible(false);
        }
        else {
            historyDialog.setLocation(parent.getX() + parent.getWidth() + 5, parent.getY());
            historyDialog.setVisible(true);
        }
    }


    // shows 5.0 as "5"
    public static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

}
