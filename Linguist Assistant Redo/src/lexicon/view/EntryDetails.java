package lexicon.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.jdesktop.swingx.JXTable;

import net.miginfocom.swing.MigLayout;
import lexicon.model.Entry;
import ontology.model.Concept;

public class EntryDetails extends JPanel {
    private final int FIELD_WIDTH = 20;
    private final int AREA_HEIGHT = 3;
    private JTextField stemField;
    private JTextArea glossArea;
    private ConceptTableModel mappingsModel;
    private JXTable mappingsTable;
    private Entry entry;
    private List<Listener> listeners = new ArrayList<>();
    
    
    public interface Listener {
        public abstract void addMapping(Entry entry);
        public abstract void delMapping(Concept concept, Entry entry);
    }
    
    public EntryDetails() {
        // Details
        JLabel detailsLabel = new JLabel("Details");
        
        JLabel stem = new JLabel("Stem: ");
        stemField = new JTextField(FIELD_WIDTH);
        
        JLabel gloss = new JLabel("Gloss: ");
        glossArea = new JTextArea(AREA_HEIGHT, FIELD_WIDTH);
        glossArea.setWrapStyleWord(true);
        glossArea.setLineWrap(true);
        JScrollPane glossPane = new JScrollPane(glossArea);
        
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new MigLayout("wrap 2"));
        detailsPanel.add(stem);
        detailsPanel.add(stemField);
        detailsPanel.add(gloss);
        detailsPanel.add(glossPane);
        
        // Lexicon Mappings
        JLabel mappingsLabel = new JLabel("Mappings");
        mappingsModel = new ConceptTableModel();
        mappingsTable = new JXTable(mappingsModel);
        mappingsTable.setVisibleRowCount(3);
        JScrollPane mappingsPane = new JScrollPane(mappingsTable);
        JButton addMapping = new JButton("+");
        JButton delMapping = new JButton("–");
        Box mappingBox = new Box(BoxLayout.Y_AXIS);
        mappingBox.add(addMapping);
        mappingBox.add(delMapping);
        addMapping.addActionListener(new AddMapping());
        delMapping.addActionListener(new DelMapping());
        
    }
    
    @SuppressWarnings("serial")
    private class ConceptTableModel extends AbstractTableModel {
        List<Concept> concepts;
        
        private ConceptTableModel() {
            concepts = new ArrayList<>();
        }
    
        public int getColumnCount() {
            return 1;
        }
    
        public int getRowCount() {
            return concepts.size();
        }
        
        public void updateEntries(List<Concept> concepts) {
            this.concepts = concepts;
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            
            switch (columnIndex) {
            case 0: colName = "Concept";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Concept entry = concepts.get(rowIndex);
            Object value;
            switch (columnIndex) {
                case 0: value = entry;
                        break;
                default: value = "";
            }
            
            return value;
        }
        
        public Concept getConcept(int index) {
            if (index < 0 || index >= concepts.size())
                return null;
            return concepts.get(index);
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }
    
    public void refresh(Entry entry) {
        if (entry == null)
            return;
        
        this.entry = entry;
        stemField.setText(entry.getStem());
        glossArea.setText(entry.getGloss());
        
        mappingsModel.updateEntries(entry.getMappings());
        mappingsModel.fireTableDataChanged();
    }
    
    public Concept getSelectedConcept() {
        int index = mappingsTable.getSelectedRow();
        return mappingsModel.getConcept(index);
    }
    
    private class AddMapping implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (entry == null)
                return;
            
            for (Listener listener : listeners) {
                listener.addMapping(entry);
            }            
        }   
    }
    
    private class DelMapping implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Concept selectedConcept = getSelectedConcept();
            if (entry == null || selectedConcept == null)
                return;
            
            for (Listener listener : listeners) {
                listener.delMapping(getSelectedConcept(), entry);
            }            
        }   
    }
}
