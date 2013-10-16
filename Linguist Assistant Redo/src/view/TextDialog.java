package view;

import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

public class TextDialog extends JDialog {
    private JLabel label;
    private JTextField textField;
    private JButton ok;
    private final int WIDTH = 200;
    private final int HEIGHT = 120;
    private final Dimension dimension = new Dimension(WIDTH, HEIGHT);
    
    public TextDialog(String prompt) {
        setModalityType(ModalityType.TOOLKIT_MODAL);
        
        final String look = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(look);
                } 
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        setSize(dimension);
        setLocationRelativeTo(null);
        label = new JLabel(prompt);
        textField = new JTextField(20);
        ok = new JButton("Ok");
        ok.addActionListener(new OkListener());
        JPanel panel = new JPanel();
        
        panel.setLayout(new MigLayout());
        panel.add(label,"wrap");
        panel.add(textField,"wrap");
        panel.add(ok,"wrap");
        
        getContentPane().add(panel);
        setVisible(true);
    }
    
    public String getText() {
        return textField.getText();
    }
    
    private class OkListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            setVisible(false);
            dispose();
        }
        
    }
}
    
