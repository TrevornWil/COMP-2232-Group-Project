import javax.swing.SwingUtilities;

// Start Of Program
public class OperateCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater((){
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                
            }
            new ScientificCalculator().setVisible(true);
        });
    }
}
