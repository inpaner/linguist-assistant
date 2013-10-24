package commons.main;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import commons.menu.HelpMenu;
import commons.menu.ViewMenu;

@SuppressWarnings("serial")
public class MainUi extends JPanel {
    public MainUi() {
        setLayout(new MigLayout());
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new ViewMenu());
        menuBar.add(new HelpMenu());
        add(menuBar);
        
    }
    
}
