package commons.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;



@SuppressWarnings("serial")
public class HelpMenu extends JMenu {
    public HelpMenu() {
        super("Help");
        setMnemonic(KeyEvent.VK_H);
        
        JMenuItem about = new JMenuItem();
        about.setAction(new AboutAction());
        
        add(about);
    }
    
    @SuppressWarnings("serial")
    private class AboutAction extends AbstractAction {
        private AboutAction() {
            super("About");
            putValue(SHORT_DESCRIPTION, "");
            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        }
        
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}
