
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScientificCalculator extends JFrame {

    //CALCULATOR GUI SET UP
    // true = Rad, false = Deg
    private boolean radiansMode = true;
    // false = normal, true = Inv
    private boolean inverseMode = false;

    private JTextField display;
    private Evaluations evaluator;
    private CalculatorExtras extras;

    private String expression = "";

    public ScientificCalculator() {

        setTitle("Scientific Calculator");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        evaluator = new Evaluations();

        // Calculator display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(35, 35, 35));
        display.setForeground(Color.WHITE);

        add(display, BorderLayout.NORTH);

        // Extras and history
        extras = new CalculatorExtras(this, display);

        // Calculator buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 6, 5, 5));
        buttonPanel.setBackground(new Color(30, 30, 30));

        String[] buttons = {
            "Rad", "Deg", "Inv", "(", ")", "%",
            "AC", "sin", "ln", "7", "8", "9",
            "÷", "π", "cos", "log", "4", "5",
            "6", "×", "√", "tan", "1", "2",
            "3", "−", "Ans", "EXP", "x²", "0",
            ".", "=", "+", "Rnd", "History"
        };

        for (String text : buttons) {

            JButton button = new JButton(text);

            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(75, 75, 75));
            button.setFocusPainted(false);

            if (text.equals("=")) {button.setBackground(new Color(65, 140, 230));}

            button.addActionListener(e -> buttonClick(text)); //Recheck

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(30, 30, 30));

        setVisible(true); //Recheck
    }

    // RETURN THIGNS
     // true = Rad, false = Deg
    private boolean radiansMode = true; 

    // false = normal, true = Inv
    private boolean inverseMode = false; 

    public void setRadiansMode(boolean radiansMode) {
        this.radiansMode = radiansMode;
    }

    public boolean isRadiansMode() {
        return radiansMode;
    }

    public void setInverseMode(boolean inverseMode) {
        this.inverseMode = inverseMode;
    }

    public boolean isInverseMode() {
        return inverseMode;
    }

    public boolean toggleInverseMode() {
        inverseMode = !inverseMode;
        return inverseMode;
    }

    public double pi() {
        return Math.PI;
    }

    // Operations based on current Inv state
    public double trigSin(double value) {
        if (inverseMode) {
            return asin(value);
        }
        return sin(value);
    }

    public double trigCos(double value) {
        if (inverseMode) {
            return acos(value);
        }
        return cos(value);
    }

    public double trigTan(double value) {
        if (inverseMode) {
            return atan(value);
        }
        return tan(value);
    }

    public double rootOrSquare(double value) {
        if (inverseMode) {
            return sqr(value);
        }
        return sqrt(value);
    }

    // Operations is radians or degrees based on mode
    public double sin(double angle) {
        double rad;
        if (radiansMode) {
            rad = angle;
        } else {
            rad = Math.toRadians(angle);
        }
        return cleanNearZero(Math.sin(rad));
    }

    public double cos(double angle) {
        double rad;
        if (radiansMode) {
            rad = angle;
        } else {
            rad = Math.toRadians(angle);
        }
        return cleanNearZero(Math.cos(rad));
    }

    public double tan(double angle) {
        if (!radiansMode && Math.abs(angle % 180.0) == 90.0) {
            return Double.NaN;
        }

        double rad;
        if (radiansMode) {
            rad = angle;
        } else {
            rad = Math.toRadians(angle);
        }

        double result = Math.tan(rad);
        if (Math.abs(result) > 1e12) {
            return Double.NaN;
        }
        return cleanNearZero(result);
    }

    // Inverse operations radians or degrees based on mode
    public double asin(double x) {
        if (x < -1.0 || x > 1.0 || Double.isNaN(x)) {
            return Double.NaN;
        }

        double rad = Math.asin(x);
        double output;
        if (radiansMode) {
            output = rad;
        } else {
            output = Math.toDegrees(rad);
        }
        return cleanNearZero(output);
    }

    public double acos(double x) {
        if (x < -1.0 || x > 1.0 || Double.isNaN(x)) {
            return Double.NaN;
        }

        double rad = Math.acos(x);
        double output;
        if (radiansMode) {
            output = rad;
        } else {
            output = Math.toDegrees(rad);
        }
        return cleanNearZero(output);
    }

    public double atan(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }

        double rad = Math.atan(x);
        double output;
        if (radiansMode) {
            output = rad;
        } else {
            output = Math.toDegrees(rad);
        }
        return cleanNearZero(output);
    }

    public double sqrt(double x) {
        if (x < 0.0 || Double.isNaN(x)) {
            return Double.NaN;
        }
        return Math.sqrt(x);
    }

    public double sqr(double x) {
        if (Double.isNaN(x)) {
            return Double.NaN;
        }
        return x * x;
    }

    // Prevents floating point errors for sin(PI) or cos(90 deg)
    private double cleanNearZero(double value) {
        if (Math.abs(value) < 1e-12) {
            return 0.0;
        }
        return value;
    }


    
    // CALCULATOR EXTRAS
    private class CalculatorExtras {

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

        // Ans button
        public void btnAnsClick(ActionEvent evt) {
            resetAC();
            txtDisplay.setText(lastAnswer);
        }

        // Random number button
        public void btnRndClick(ActionEvent evt) {

            resetAC();
            txtDisplay.setText(format(Math.random()));
        }

        // All Clear button
        public void btnACClick(ActionEvent evt) {
            acPresses++;
            txtDisplay.setText("0");

            if (acPresses >= 2) {
                operationCleared = true;
                acPresses = 0;

            } else {operationCleared = false;}
        }

        // History button
        public void btnHistoryClick(ActionEvent evt) {

            resetAC();

            if (historyDialog.isVisible()) {
                historyDialog.setVisible(false);
            }
            else {
                historyDialog.setLocation(parent.getX() + parent.getWidth() + 5,parent.getY());
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

    // HISTORY DIALOG
    private class HistoryDialog extends JDialog {

        private JLabel lblTitle = new JLabel("History of operations", JLabel.CENTER);
        private DefaultListModel<String> historyModel = new DefaultListModel<>();
        private JList<String> lstHistory = new JList<>(historyModel);
        private JScrollPane scrollHistory = new JScrollPane(lstHistory);
        private JButton btnClearHistory = new JButton("Clear History");
        private JTextField txtDisplay;

        public HistoryDialog(JFrame parent, JTextField txtDisplay) {

            super(parent, "History", false);
            this.txtDisplay = txtDisplay;

            setSize(260, 340);
            setLayout(null);
            setResizable(false);

            initComponents();
            initEvents();
        }

        // Adds components
        public void initComponents() {

            lblTitle.setBounds(10, 10, 225, 25);
            scrollHistory.setBounds(10, 40, 225, 210);
            btnClearHistory.setBounds(10, 260, 225, 35);

            add(lblTitle);
            add(scrollHistory);
            add(btnClearHistory);
        }

        // Sets events
        public void initEvents() {

            btnClearHistory.addActionListener(evt -> { //Check

                int value = JOptionPane.showConfirmDialog( 
                    this,
                    "Are you sure you want to clear all history?",
                    "Clear History",
                    JOptionPane.YES_NO_OPTION
                );

                if (value == JOptionPane.YES_OPTION) {
                    historyModel.clear();
                }
            });

            // Double-click an entry to retrieve its answer
            lstHistory.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent evt) {

                    if (evt.getClickCount() == 2) {

                        String entry = lstHistory.getSelectedValue();
                        if (entry != null) {
                            String answer = entry.substring( entry.lastIndexOf('=') + 1).trim();
                            txtDisplay.setText(answer);
                        }
                    }
                }
            });
        }

        // Adds calculation to history
        public void addEntry(String entry) {
            historyModel.addElement(entry);
        }

        public int getEntryCount() {
            return historyModel.getSize();
        }
    }
