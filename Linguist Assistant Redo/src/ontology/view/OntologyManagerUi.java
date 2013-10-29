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
    private OntologyList list;
    private OntologyDetails details;
    private JButton add;
    private JButton del;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        public void add();
        public void delete(Concept constituent);
        public abstract void addTag(Concept concept);
        public abstract void delTag(Concept concept, Tag tag);
        public abstract void addMapping(Concept concept);
        public abstract void delMapping(Concept concept, Entry entry);
    }
    
    public OntologyManagerUi(MainFrame frame) {
        this.frame = frame;
        JMenuBar menubar = new JMenuBar();
        menubar.add(new ViewMenu(frame));
        menubar.add(new HelpMenu());
        frame.setJMenuBar(menubar);
        
        list = new OntologyList();
        details = new OntologyDetails();
        list.addListener(new ListListener());
        
        add = new JButton("Add");
        del = new JButton("Del");
        add.addActionListener(new Add());
        del.addActionListener(new Delete());
        
        setLayout(new MigLayout());
        add(list);
        add(details, "wrap");
        add(add);
        add(del);
    }
    
    private class ListListener implements OntologyList.Listener {
        @Override
        public void selectedConcept(Concept concept) {
            details.update(concept);
        }
    }
    
    private class DetailListener implements OntologyDetails.Listener {

        @Override
        public void addTag(Concept concept) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void delTag(Concept concept, Tag tag) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void addMapping(Concept concept) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void delMapping(Concept concept, Entry entry) {
            // TODO Auto-generated method stub
            
        }
        
    }
    
    private class Add implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
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
