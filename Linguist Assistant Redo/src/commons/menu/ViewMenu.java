package commons.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import lexicon.LexiconManager;
import lexicon.view.LexiconList;
import ontology.controller.OntologyManager;
import rule.tree.RuleTree;
import semantics.controller.SemanticEditor;
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
        JMenuItem semantic = new JMenuItem(new SemanticAction());
        JMenuItem rules = new JMenuItem(new RuleAction());
        
        add(grammar);
        add(ontology);
        add(lexicon);
        add(semantic);
        add(rules);
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
            OntologyManager.run(frame);
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
            LexiconManager.run(frame);
        }
    }
    
    
    @SuppressWarnings("serial")
    private class SemanticAction extends AbstractAction {
        private SemanticAction() {
            super("Semantics");
            putValue(SHORT_DESCRIPTION, "");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        }
        
        public void actionPerformed(ActionEvent e) {
            SemanticEditor.run(frame);
        }
    }
    

    @SuppressWarnings("serial")
    private class RuleAction extends AbstractAction {
        private RuleAction() {
            super("Rules");
            putValue(SHORT_DESCRIPTION, "");
            putValue(MNEMONIC_KEY, KeyEvent.VK_R);
        }
        
        public void actionPerformed(ActionEvent e) {
            RuleTree.run(frame);
        }
    }
}
