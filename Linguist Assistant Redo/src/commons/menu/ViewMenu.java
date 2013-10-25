package commons.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import commons.main.MainFrame;

// Combined view-controller
@SuppressWarnings("serial")
public class ViewMenu extends JMenu {
    MainFrame frame;
    
    public ViewMenu(MainFrame frame) {
        super("View");
        this.frame = frame;
        setMnemonic(KeyEvent.VK_V);
        
        JMenuItem grammar = new JMenuItem(new GrammarAction());
        JMenuItem ontology = new JMenuItem(new OntologyAction());
        JMenuItem lexicon = new JMenuItem(new LexiconAction());
        
        add(grammar);
        add(ontology);
        add(lexicon);
    }
    
    
    @SuppressWarnings("serial")
    private class GrammarAction extends AbstractAction {
        private GrammarAction() {
            super("Grammar");
            putValue(SHORT_DESCRIPTION, "");
            putValue(MNEMONIC_KEY, KeyEvent.VK_G);
        }
        
        public void actionPerformed(ActionEvent e) {
            
        }
    }
    
    @SuppressWarnings("serial")
    private class OntologyAction extends AbstractAction {
        private OntologyAction() {
            super("Ontology");
            putValue(SHORT_DESCRIPTION, "");
            putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        }
        
        public void actionPerformed(ActionEvent e) {
            
        }
    }
    
    
    @SuppressWarnings("serial")
    private class LexiconAction extends AbstractAction {
        private LexiconAction() {
            super("Lexicon");
            putValue(SHORT_DESCRIPTION, "");
            putValue(MNEMONIC_KEY, KeyEvent.VK_L);
        }
        
        public void actionPerformed(ActionEvent e) {
            
        }
    }
}
