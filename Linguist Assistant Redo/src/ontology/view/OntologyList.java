package ontology.view;



import grammar.model.Constituent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;
import ontology.model.Tag;

@SuppressWarnings("serial")
public class OntologyList extends JPanel {
    private List<OntologyList.Listener> listeners;
    private List<Concept> concepts;
    private JComboBox<Tag> tagBox;
    private JTextField searchField;
    private JTable table;
    private OntologyTableModel model;
    private JComboBox<Constituent> constituentBox;
    private JButton add;
    
    public interface Listener {
        public abstract void searchChanged(Event event);
        public abstract void selectedConcept(Event event);
        public abstract void selectedTag(Event event);
        public abstract void selectedConstituent(Event event);
        public abstract void selectedAdd(Event event);
        
    }

    public class Event {
        private String text;
        private Concept concept;
        private Tag tag;
        private Constituent constituent;
        
        public String getText() {
            return text;
        }
        
        public Concept getConcept() {
            return concept;
        }
        
        public Tag getTag() {
            return tag;
        }
        
        public Constituent getConstituent() {
            return constituent;
        }
    }
    
    public OntologyList() {
        listeners = new ArrayList<>();
        model = new OntologyTableModel();
        table = new JTable();
        model = new OntologyTableModel();
        table.setModel(model);
        table.getSelectionModel().addListSelectionListener(new ListListener());
        concepts = new ArrayList<>();
        
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new SearchListener());
        
        Vector<Constituent> constituents = new Vector<>(Constituent.getAllConstituents());
        constituentBox = new JComboBox<>(constituents);
        constituentBox.addItemListener(new ConstituentListener());
        
        Vector<Tag> tags = new Vector<>(Tag.getAllTags());
        tagBox = new JComboBox<>(tags);
        tagBox.addItemListener(new TagListener());
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Event event = prepareEvent();
                
                for (Listener listener : listeners) {
                    listener.selectedAdd(event);
                }
            }
        });
        
        setLayout(new MigLayout());
        add(searchLabel, "span, split, center");
        add(searchField, "gap");
        add(constituentBox);
        add(tagBox, "wrap");
        add(scrollPane, "wrap");
        add(add, "center");
    }
    
    public void refreshConcepts(List<Concept> concepts) {
        this.concepts = concepts;
        model.fireTableDataChanged();
    }
    
    public void addListener(OntologyList.Listener listener) {
        this.listeners.add(listener);
    }
    
    private class OntologyTableModel extends AbstractTableModel {
        private OntologyTableModel() {}
    
        public int getColumnCount() {
            return 3;
        }
    
        public int getRowCount() {
            return concepts.size();
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Stem";
                    break;
            case 1: colName = "Sense";
                    break;
            case 2: colName = "Gloss";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            selectedConcept = concepts.get(rowIndex);
            Object value;
            switch (columnIndex) {
                case 0: value = selectedConcept.getStem();
                        break;
                case 1: value = selectedConcept.getSense();
                        break;
                case 2: value = selectedConcept.getGloss();
                        break;
                default: value = "";
            }
            
            return value;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    private Concept selectedConcept;
    
    private class SearchListener implements DocumentListener {
        public void changedUpdate(DocumentEvent ev) {}

        @Override
        public void insertUpdate(DocumentEvent ev) {
            Event event = prepareEvent();
            for (Listener listener : listeners) {
                listener.searchChanged(event);
            }
        }
        
        @Override
        public void removeUpdate(DocumentEvent ev) {
            Event event = prepareEvent();
            for (Listener listener : listeners) {
                listener.searchChanged(event);
            }
        }        
    }
    
    private Event prepareEvent() {
        OntologyList.Event event = new OntologyList.Event();
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Concept concept = selectedConcept;
        Tag tag = (Tag) tagBox.getSelectedItem();
        Constituent constituent = (Constituent) constituentBox.getSelectedItem();
        
        event.text = searchField.getText();
        event.concept = concept;
        event.tag = tag;
        event.constituent = constituent;
        
        return event;
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent ev) {
            if (!ev.getValueIsAdjusting()) {
                /*ListSelectionModel lsm = (ListSelectionModel) ev.getSource();
                int index = lsm.getMinSelectionIndex();
                Concept selected = concepts.get(index);
                */
                Event event = prepareEvent();
                for (Listener listener : listeners) {
                    listener.selectedConcept(event);
                }
            }
        }
        
    }
    
    private class ConstituentListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                Event event = prepareEvent();
                for (OntologyList.Listener listener : listeners) {
                    listener.selectedConstituent(event);
                }
            }
        }
    }
    
    private class TagListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                Event event = prepareEvent();
                for (OntologyList.Listener listener : listeners) {
                    listener.selectedTag(event);
                }
            }
        }
    }
}
