package commons.ui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class UiHelper {
    public static int confirmation(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);
    }
    
    public static int confirmDelete(Component parent, String toDelete) {
        String title = "Confirm Delete";
        String message = "Delete " + "?";
        return confirmation(parent, message, title);
    }
    
}
