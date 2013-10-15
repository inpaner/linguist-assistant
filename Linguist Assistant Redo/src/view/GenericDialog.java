package view;

import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

public class GenericDialog extends JDialog {
    private final int WIDTH = 400;
    private final int HEIGHT = 600;
    
    
    public GenericDialog() {
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
           
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
    }
    
    public void setPanel(JPanel panel) {
        setContentPane(panel);
        setVisible(true);
    }
    
    public void closeDialog() {
        WindowEvent event = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
    }
}
