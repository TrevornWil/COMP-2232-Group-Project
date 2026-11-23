
import javax.swing.*;
import java.awt.*;

public class ScientificCalculator extends JFrame {

    private JTextField display;

    public ScientificCalculator() {

        setTitle("Scientific Calculator");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // Calculator display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setOpaque(true);
        display.setBackground(new Color(45, 48, 58));
        display.setForeground(Color.WHITE);

        add(display, BorderLayout.NORTH);

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

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(30, 32, 38));
    }

}
