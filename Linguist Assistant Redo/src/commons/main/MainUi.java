package commons.main;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import commons.menu.HelpMenu;
import commons.menu.ViewMenu;

@SuppressWarnings("serial")
public class MainUi extends JPanel {
    public MainUi(MainFrame frame) {
        setLayout(new MigLayout());
        JMenuBar menubar = new JMenuBar();
        menubar.add(new ViewMenu(frame));
        menubar.add(new HelpMenu());
        frame.setJMenuBar(menubar);
    }
    
}
