package lexicon.view;

import javax.swing.JMenuBar;

import commons.main.MainFrame;
import commons.menu.HelpMenu;
import commons.menu.ViewMenu;

public class LexiconManagerUi {
    public LexiconManagerUi(MainFrame frame) {
        // Init menubar
        JMenuBar menubar = new JMenuBar();
        menubar.add(new ViewMenu(frame));
        menubar.add(new HelpMenu());
        frame.setJMenuBar(menubar);
        
    }
}
