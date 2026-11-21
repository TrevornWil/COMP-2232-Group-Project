
import javax.swing.*;
import java.awt.*;

public class ScientificCalculator extends JFrame {

    private JTextField display;

    public ScientificCalculator() {

        setTitle("Scientific Calculator");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // Calculator display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(new Color(35, 35, 35));
        display.setForeground(Color.WHITE);

        add(display, BorderLayout.NORTH);

        // Calculator buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 6, 5, 5));
        buttonPanel.setBackground(new Color(30, 30, 30));

        String[] buttons = {
            "Rad", "Deg", "Inv", "(", ")", "%", "AC",
            "sin", "ln", "7", "8", "9", "÷",
            "π", "cos", "log", "4", "5", "6", "×",
            "√", "tan", "1", "2", "3", "−",
            "Ans", "EXP", "x²", "0", ".", "=", "+",
            "Rnd", "History"
        };

        for (String text : buttons) {

            JButton button = new JButton(text);

            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(75, 75, 75));
            button.setFocusPainted(false);

            if (text.equals("=")) {
                button.setBackground(new Color(65, 140, 230));
            }

            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        getContentPane().setBackground(new Color(30, 30, 30));
    }

}
