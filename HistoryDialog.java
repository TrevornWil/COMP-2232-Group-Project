import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
