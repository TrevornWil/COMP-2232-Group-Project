import javax.swing.*;

public class OperateCalculator {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                // Set system look and feel
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
                // Use default appearance
            }

            // Confirmation box
            int choice = JOptionPane.showConfirmDialog(
                null,
                "Do you want to open the Scientific Calculator?",
                "Calculator Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {

                try {
                    // Open calculator
                    new ScientificCalculator().setVisible(true);

                } catch (Exception e) {

                    // Warning/error message
                    JOptionPane.showMessageDialog(
                        null,
                        "Warning! Unable to open the calculator.\n"
                        + e.getMessage(),
                        "Calculator Error",
                        JOptionPane.WARNING_MESSAGE
                    );
                }

            } else {

                // User cancels calculator operation
                JOptionPane.showMessageDialog(
                    null,
                    "Warning! Calculator operation cancelled.",
                    "Warning Message",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }
}
