package ontology.view;

import grammar.model.Category;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;
import ontology.model.Tag;

import org.jdesktop.swingx.JXTable;

@SuppressWarnings("serial")
public class ConceptList extends JPanel {
    private List<Listener> listeners = new ArrayList<>();
    private List<Concept> concepts;
    private JComboBox<Tag> tagBox;
    private JTextField searchField;
    private JXTable table;
    private OntologyTableModel model;
    private JComboBox<Category> constituentBox;
    
    public interface Listener {
        public abstract void selectedConcept(Concept concept);
    }
    
    public ConceptList() {
        concepts = new ArrayList<>();
        
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(15);
        searchField.getDocument().addDocumentListener(new SearchListener());
        
        Vector<Category> categories = new Vector<>(Category.getAll());
        constituentBox = new JComboBox<>(categories);
        constituentBox.addItemListener(new ComboListener());
        
        Vector<Tag> tags = new Vector<>(Tag.getAllTags());
        tagBox = new JComboBox<>(tags);
        tagBox.addItemListener(new ComboListener());
        
        model = new OntologyTableModel();
        table = new JXTable(model);
        table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListListener());
        JScrollPane scrollPane = new JScrollPane(table);
        
        setLayout(new MigLayout());
        add(searchLabel, "span, split");
        add(searchField, "gap, wrap");
        add(constituentBox, "span, split");
        add(tagBox, "wrap");
        add(scrollPane, "wrap");
        
        refresh();
    }
    
    private void refresh() {
        String substring = searchField.getText();
        Tag tag = (Tag) tagBox.getSelectedItem();
        Category category = (Category) constituentBox.getSelectedItem();
        
        concepts = Concept.getInstances(substring, tag, category); 
        model.fireTableDataChanged();
    }
    
    public void addListener(ConceptList.Listener listener) {
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
            refresh();
        }
        
        @Override
        public void removeUpdate(DocumentEvent ev) {
            refresh();
        }        
    }
    
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent ev) {
            if (!ev.getValueIsAdjusting()) {
                for (Listener listener : listeners) {
                    listener.selectedConcept(getSelected());
                }
            }
        }
    }
    
    public Concept getSelected() {
        int index = table.getSelectedRow();
        if (index == -1) 
            return null;
        return concepts.get(index); 
    }
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                refresh();
            }
        }
    }
    
}
