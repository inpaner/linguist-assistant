package ontology.view;

import grammar.model.Constituent;
import grammar.model.Feature;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import commons.view.MainFrame;
import net.miginfocom.swing.MigLayout;
import ontology.model.Concept;

public class OntologyListPanel extends JPanel {
    private List<Concept> concepts;
    private JTable table;
    private OntologyTableModel model;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        OntologyListPanel panel = new OntologyListPanel();
        frame.setPanel(panel);
        Constituent constituents = Constituent.get("N");
        List<Concept> concepts = Concept.getInstances("air", constituents);
        panel.refreshConcepts(concepts);
        System.out.println(concepts.size());
    }
    
    public OntologyListPanel() {
        setLayout(new MigLayout());
        model = new OntologyTableModel();
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    
    public void refreshConcepts(List<Concept> concepts) {
        this.concepts = concepts;
        System.out.println("here");
        model = new OntologyTableModel();
        table.setModel(model);
        
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
}
