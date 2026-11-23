// Students
// Daniel Phillips - 500002913
// Kessia Paul - 500002400
// Reanna Christopher - 500002956
// Tarique K. Browne - 500001112
// Trevorn Williams - 500002032

package Final_Project;

import javax.swing.SwingUtilities;

// Start Of Program
public class OperateCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                
            }
            new ScientificCalculator().setVisible(true);
        });
    }
}