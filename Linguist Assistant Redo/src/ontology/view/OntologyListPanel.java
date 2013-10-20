package ontology.view;

import grammar.model.Constituent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;
import commons.view.MainFrame;

@SuppressWarnings("serial")
public class OntologyListPanel extends JPanel {
    private List<OntologyListPanel.Listener> listeners;
    private List<Concept> concepts;
    private JTextField searchField;
    private JTable table;
    private OntologyTableModel model;
    
    public interface Listener {
        public abstract void searchChanged(String text);
        public abstract void selected(Concept selected);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        OntologyListPanel panel = new OntologyListPanel();
        frame.setPanel(panel);
        
        Constituent constituents = Constituent.get("N");
        List<Concept> concepts = Concept.getInstances("air", constituents);
        panel.refreshConcepts(concepts);
       
    }
    
    public OntologyListPanel() {
        this.listeners = new ArrayList<>();
        this.model = new OntologyTableModel();
        this.table = new JTable();
        this.model = new OntologyTableModel();
        this.table.setModel(model);
        this.table.getSelectionModel().addListSelectionListener(new ListListener());
        this.concepts = new ArrayList<>();
        JLabel searchLabel = new JLabel("Search: ");
        
        this.searchField = new JTextField(15);
        this.searchField.getDocument().addDocumentListener(new SearchListener());
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        setLayout(new MigLayout());
        add(searchLabel, "span, split, center");
        add(searchField, "wrap");
        add(scrollPane);
    }
    
    public void refreshConcepts(List<Concept> concepts) {
        this.concepts = concepts;
        model.fireTableDataChanged();
    }
    
    public void addListener(OntologyListPanel.Listener listener) {
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
            Concept concept = concepts.get(rowIndex);
            Object value;
            switch (columnIndex) {
                case 0: value = concept.getStem();
                        break;
                case 1: value = concept.getSense();
                        break;
                case 2: value = concept.getGloss();
                        break;
                default: value = "";
            }
            
            return value;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    private class SearchListener implements DocumentListener {
        public void changedUpdate(DocumentEvent ev) {}

        @Override
        public void insertUpdate(DocumentEvent ev) {
            for (OntologyListPanel.Listener listener : listeners) {
                listener.searchChanged(searchField.getText());
            }
        }
        
        @Override
        public void removeUpdate(DocumentEvent ev) {
            for (OntologyListPanel.Listener listener : listeners) {
                listener.searchChanged("");
            }
        }        
    }
    
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent ev) {
            if (!ev.getValueIsAdjusting()) {
                ListSelectionModel lsm = (ListSelectionModel) ev.getSource();
                int index = lsm.getMinSelectionIndex();
                Concept selected = concepts.get(index);
                for (OntologyListPanel.Listener listener : listeners) {
                    listener.selected(selected);
                }
            }
        }
        
    }
}
