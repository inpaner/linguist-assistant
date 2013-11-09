package ontology.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import lexicon.model.Entry;
import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;
import ontology.model.Tag;
import commons.main.MainFrame;
import commons.menu.HelpMenu;
import commons.menu.ViewMenu;

public class OntologyManagerUi extends JPanel {
    MainFrame frame;
    private Concept selectedConcept;
    private ConceptList list;
    private ConceptDetails details;
    private JButton add;
    private JButton delete;
    private List<Listener> listeners = new ArrayList<>();
    
    interface Listener {
        void add();
        void delete(Concept constituent);
        void addTag(Concept concept);
        void delTag(Concept concept, Tag tag);
        void addMapping(Concept concept);
        void delMapping(Concept concept, Entry entry);
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    public OntologyManagerUi(MainFrame frame) {
        this.frame = frame;
        JMenuBar menubar = new JMenuBar();
        menubar.add(new ViewMenu(frame));
        menubar.add(new HelpMenu());
        frame.setJMenuBar(menubar);
        
        list = new ConceptList();
        details = new ConceptDetails();
        list.addListener(new ListListener());
        details.addListener(new DetailListener());
        
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
    
    public void refresh() {
        Concept concept = null;
        refresh(concept);
    }
    
    public void refresh(Concept concept) {
        list.refresh();
        details.refresh(concept);
    }

    private class ListListener implements ConceptList.Listener {
        @Override
        public void selectedConcept(Concept concept) {
            selectedConcept = concept;
            details.refresh(concept);
        }
    }
    
    /**
     * Serves as a 1:1 adapter for the Listener of this class
     */
    private class DetailListener implements ConceptDetails.Listener {
        @Override
        public void addTag(Concept concept) {
            for (Listener listener : listeners) {
                listener.addTag(concept);
            }
        }

        @Override
        public void delTag(Concept concept, Tag tag) {
            for (Listener listener : listeners) {
                listener.delTag(concept, tag);
            }
        }

        @Override
        public void addMapping(Concept concept) {
            for (Listener listener : listeners) {
                listener.addMapping(concept);
            }
        }

        @Override
        public void delMapping(Concept concept, Entry entry) {
            for (Listener listener : listeners) {
                listener.delMapping(concept, entry);
            }
        }
        
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
            Concept selected = list.getSelected();
            if (selected == null)
                return;
            for (Listener listener : listeners) {
                listener.delete(selected);
            }
        }
    }
}
