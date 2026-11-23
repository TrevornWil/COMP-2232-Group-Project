
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ScientificCalculator extends JFrame {

    //CALCULATOR GUI SET UP
    private JTextField display;
    private Evaluations evaluator;
    private CalculatorExtras extras;

    private String expression = "";

    public ScientificCalculator() {

        setTitle("Scientific Calculator");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        evaluator = new Evaluations();

        // Calculator display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setOpaque(true);
        display.setBackground(new Color(45, 48, 58));
        display.setForeground(Color.WHITE);

        // Extras and history
        extras = new CalculatorExtras(this, display);

        JPanel displayPanel = new JPanel(new BorderLayout(5, 0));
        displayPanel.setBackground(new Color(45, 48, 58));

        ImageIcon historyIcon = new ImageIcon("Final_Project/icon/history_icon.png");
        if (historyIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image scaledHistoryIcon = historyIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            historyIcon = new ImageIcon(scaledHistoryIcon);
        }

        JButton historyButton = new JButton(historyIcon);
        historyButton.setToolTipText("History");
        historyButton.setPreferredSize(new Dimension(45, 45));
        historyButton.setForeground(Color.WHITE);
        historyButton.setBackground(new Color(45, 48, 58));
        historyButton.setOpaque(false);
        historyButton.setContentAreaFilled(false);
        historyButton.setBorderPainted(false);
        historyButton.setFocusPainted(false);
        historyButton.addActionListener(e -> extras.btnHistoryClick(e));

        displayPanel.add(historyButton, BorderLayout.WEST);
        displayPanel.add(display, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.NORTH);

        // Calculator buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 7, 5, 5));
        buttonPanel.setBackground(new Color(30, 32, 38));

        String[] buttons = {
            "Rad", "Deg", "x!", "(", ")", "%", "AC",
            "Inv", "sin", "ln", "7", "8", "9", "÷",
            "π", "cos", "log", "4", "5", "6", "×",
            "e", "tan", "√", "1", "2", "3", "−",
            "Ans", "EXP", "x²", "0", ".", "=", "+"
        };

        for (String text : buttons) {

            JButton button = new JButton(text);

            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(130, 136, 145));
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            if (text.equals("=")) {
                button.setBackground(new Color(74, 154, 236));
            } else if (text.matches("[0-9.]")) {
                button.setBackground(new Color(74, 79, 88));
            }

            button.addActionListener(e -> buttonClick(text)); //Recheck

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(30, 32, 38));

        setVisible(true); //Recheck
    }
    // WARNING MESSAGE
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Calculator Warning",
            JOptionPane.WARNING_MESSAGE
        );
    }
    // CALCULATION METHODS/FORMULAS
    
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
    public class HistoryDialog extends JDialog {

        private JLabel lblTitle = new JLabel("History of operations", JLabel.CENTER);
        private DefaultListModel<String> historyModel = new DefaultListModel<String>();
        private JList<String> lstHistory = new JList<String>(historyModel);
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


        public void initComponents() {
            lblTitle.setBounds(10, 10, 225, 25);
            scrollHistory.setBounds(10, 40, 225, 210);
            btnClearHistory.setBounds(10, 260, 225, 35);

            add(lblTitle);
            add(scrollHistory);
            add(btnClearHistory);
        }


        public void initEvents() {

            btnClearHistory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btnClearHistoryClick(e);
                }
            });

            lstHistory.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    lstHistoryClick(e);
                }
            });

        }


        private void btnClearHistoryClick(ActionEvent evt) {
            int value;
            value = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to clear all history?",
                    "Clear History",
                    JOptionPane.YES_NO_OPTION);
            if (value == JOptionPane.YES_OPTION) {
                historyModel.clear();
            }
        }


        // double-click an entry to put its answer back on the screen
        private void lstHistoryClick(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                String entry = lstHistory.getSelectedValue();
                if (entry != null) {
                    String answer = entry.substring(entry.lastIndexOf('=') + 1).trim();
                    txtDisplay.setText(answer);
                }
            }
        }


        public void addEntry(String entry) {
            historyModel.addElement(entry);
        }

        public int getEntryCount() {
            return historyModel.getSize();
        }

    }

    public class Evaluations {

        public double evaluate(String expression) {
            expression = expression.replace("x", "*");
            expression = expression.replace("÷", "/");
            expression = expression.replace("-", "-");

            ArrayList<String> element = splitIntoElement(expression);
            return solveWithBrackets(element);
        }

        private ArrayList<String> splitIntoElement(String expression) {
            ArrayList<String> element = new ArrayList<String>();
            String currentNumber = "";

            for (int i = 0; i < expression.length(); i++) {
                char currentChar = expression.charAt(i);

                boolean isNegativeSign = false;
                if (currentChar == '-' && currentNumber.equals("")) {
                    if (element.size() == 0) {
                        isNegativeSign = true;
                    } else {
                        String previousToken = element.get(element.size() - 1);
                        if (isOperator(previousToken) || previousToken.equals("(")) {
                            isNegativeSign = true;
                        }
                    }
                }

                if (Character.isDigit(currentChar) || currentChar == '.' || isNegativeSign) {
                    currentNumber = currentNumber + currentChar;
                } else {
                    if (!currentNumber.equals("")) {
                        element.add(currentNumber);
                        currentNumber = "";
                    }
                    element.add(String.valueOf(currentChar));
                }
            }

            if (!currentNumber.equals("")) {
                element.add(currentNumber);
            }
            return element;
        }

        private boolean isOperator(String token) {
            return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
        }

        private double solveWithBrackets(ArrayList<String> element) {
            while (element.contains("(")) {
                int openIndex = element.lastIndexOf("(");

                int closeIndex = -1;
                for (int i = openIndex; i < element.size(); i++) {
                    if (element.get(i).equals(")")) {
                        closeIndex = i;
                        break;
                    }
                }
                if (closeIndex == -1) {
                    throw new IllegalArgumentException("Missing closing bracket");
                }

                ArrayList<String> insideBrackets = new ArrayList<String>(element.subList(openIndex + 1, closeIndex));
                double bracketAnswer = solveWithoutBrackets(insideBrackets);

                for (int i = closeIndex; i >= openIndex; i--) {
                    element.remove(i);
                }
                element.add(openIndex, String.valueOf(bracketAnswer));
            }
            return solveWithoutBrackets(element);
        }

        private double solveWithoutBrackets(ArrayList<String> element) {
            if (element.size() == 0) {
                throw new IllegalArgumentException("Empty expression");
            }

            int position = 0;
            while (position < element.size()) {
                String token = element.get(position);

                if (token.equals("*") || token.equals("/")) {
                    if (position == 0 || position == element.size() - 1) {
                        throw new IllegalArgumentException("Incomplete expression");
                    }
                    double leftNumber = Double.parseDouble(element.get(position - 1));
                    double rightNumber = Double.parseDouble(element.get(position + 1));
                    double answer;

                    if (token.equals("*")) {
                        answer = leftNumber * rightNumber;
                    } else {
                        if (rightNumber == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        answer = leftNumber / rightNumber;
                    }

                    element.set(position - 1, String.valueOf(answer));
                    element.remove(position + 1);
                    element.remove(position);
                } else {
                    position++;
                }
            }

            double total = Double.parseDouble(element.get(0));
            int index = 1;
            while (index < element.size()) {
                if (index + 1 >= element.size()) {
                    throw new IllegalArgumentException("Incomplete expression");
                }
                String operator = element.get(index);
                double nextNumber = Double.parseDouble(element.get(index + 1));

                if (operator.equals("+")) {
                    total = total + nextNumber;
                } else if (operator.equals("-")) {
                    total = total - nextNumber;
                } else {
                    throw new IllegalArgumentException("Invalid expression");
                }
                index = index + 2;
            }
            return total;
        }
    }

    // OPERATE CALCULATOR
    public void buttonClick(String button) {

        try {

            // Angle mode
            if (button.equals("Rad")) {
                radiansMode = true;
                display.setText("Rad Mode");
                return;
            }

            if (button.equals("Deg")) {
                radiansMode = false;
                display.setText("Deg Mode");
                return;
            }

            // Inverse mode
            if (button.equals("Inv")) {
                inverseMode = !inverseMode;
                display.setText(inverseMode ? "Inv Mode" : "Normal Mode");
                return;
            }

            // All Clear
            if (button.equals("AC")) {
                extras.btnACClick(null);
                expression = "";
                return;
            }

            extras.resetAC();

            // History
            if (button.equals("History")) {
                extras.btnHistoryClick(null);
                return;
            }

            // Previous answer
            if (button.equals("Ans")) {
                extras.btnAnsClick(null);
                expression = display.getText();
                return;
            }

            // Random number
            if (button.equals("Rnd")) {
                extras.btnRndClick(null);
                expression = display.getText();
                return;
            }

            // Pi
            if (button.equals("π")) {
                expression += String.valueOf(Math.PI);
                display.setText(expression);
                return;
            }

            // Equals button
            if (button.equals("=")) {

                if (expression.isEmpty()) {
                    showWarning("Please enter a calculation.");
                    return;
                }

                double result = evaluator.evaluate(expression);

                if (Double.isNaN(result) ||
                    Double.isInfinite(result)) {

                    showWarning("Invalid mathematical result.");
                    return;
                }

                String answer = CalculatorExtras.format(result);

                extras.recordCalculation(expression, answer);

                display.setText(answer);
                expression = answer;

                return;
            }

            // Unsupported operations
            if (button.equals("sin") ||
                button.equals("cos") ||
                button.equals("tan") ||
                button.equals("ln") ||
                button.equals("log") ||
                button.equals("√") ||
                button.equals("x²") ||
                button.equals("%") ||
                button.equals("EXP")) {

                showWarning(
                    "This mathematical operation has not yet " +
                    "been implemented."
                );

                return;
            }

            // Numerical and arithmetic buttons
            if (button.matches("[0-9.]") ||
                button.equals("+") ||
                button.equals("−") ||
                button.equals("×") ||
                button.equals("÷") ||
                button.equals("(") ||
                button.equals(")")) {

                if (button.equals("−")) {
                    expression += "-";
                }
                else if (button.equals("×")) {
                    expression += "*";
                }
                else if (button.equals("÷")) {
                    expression += "/";
                }
                else {
                    expression += button;
                }

                display.setText(expression);
            }

        } catch (ArithmeticException e) {

            showWarning("Mathematical error: " + e.getMessage());
            display.setText("NaN");
            expression = "";

        } catch (NumberFormatException e) {

            showWarning("Please enter a valid numerical expression.");

        } catch (IllegalArgumentException e) {

            showWarning("Invalid calculation: " + e.getMessage());

        } catch (Exception e) {

            showWarning("An unexpected error occurred.");
        }
    }

}
