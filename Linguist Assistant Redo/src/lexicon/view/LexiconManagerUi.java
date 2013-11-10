package lexicon.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import lexicon.model.Entry;
import ontology.model.Concept;
import commons.main.MainFrame;
import commons.menu.HelpMenu;
import commons.menu.ViewMenu;

@SuppressWarnings("serial")
public class LexiconManagerUi extends JPanel {
    private MainFrame frame;
    private JButton add;
    private JButton delete;
    private List<Listener> listeners = new ArrayList<>();
    private LexiconList list;
    private EntryDetails details;
    
    interface Listener {
        void add();
        void delete(Entry entry);
        void addMapping(Entry entry);
        void delMapping(Concept concept, Entry entry);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public LexiconManagerUi(MainFrame frame) {
        this.frame = frame;

        // Menubar
        JMenuBar menubar = new JMenuBar();
        menubar.add(new ViewMenu(frame));
        menubar.add(new HelpMenu());
        frame.setJMenuBar(menubar);
        
        list = new LexiconList();
        details = new EntryDetails();
        
        add = new JButton("Add");
        delete = new JButton("Del");
        add.addActionListener(new Add());
        delete.addActionListener(new Delete());
        
        setLayout(new MigLayout());
        add(list);
        add(details, "wrap");
        add(add, "span, split");
        add(delete);
    }
    
    private class Add implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Listener listener : listeners) {
                listener.add();
            }
        }
    }
    
    private class Delete implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Entry selected = list.getSelected();
            if (selected == null)
                return;
            for (Listener listener : listeners) {
                listener.delete(selected);
            }
        }
        
    }
}
