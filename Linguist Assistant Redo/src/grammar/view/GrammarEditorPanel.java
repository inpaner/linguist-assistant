package grammar.view;

import grammar.model.Constituent;
import grammar.model.Feature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.miginfocom.swing.MigLayout;
import commons.main.MainFrame;

@SuppressWarnings("serial")
public class GrammarEditorPanel extends JPanel {
    private List<Listener> listeners = new ArrayList<Listener>();
    private JComboBox<Constituent> constituents;
    private JTable featuresTable;
    private JTable valuesTable;
    private FeatureTableModel featureModel;
    private ValueTableModel valuesModel;
    
    public interface Listener {
        public abstract void addFeature(Constituent constituent);
        public abstract void editFeature(Feature feature, String name);
        public abstract void deleteFeature(Feature feature);
        public abstract void addValue(Feature feature);
        public abstract void editValue(Feature feature, String name, int index);
        public abstract void deleteValue(Feature feature, int index);
    }

    public GrammarEditorPanel() {
        setLayout(new MigLayout("wrap 2"));
        JLabel constituentLabel = new JLabel("Constituent: ");
        Vector<Constituent> vector = new Vector<>(Constituent.getAll());
        
        constituents = new JComboBox<>(vector);
        constituents.addItemListener(new ComboListener());
        
        featureModel = new FeatureTableModel();
        featuresTable = new JTable(featureModel);
        featuresTable.addMouseListener(new FeatureTableListener());
        JScrollPane featuresPane = new JScrollPane(featuresTable);
        
        valuesModel = new ValueTableModel();
        valuesTable = new JTable(valuesModel);
        JScrollPane valuesPane = new JScrollPane(valuesTable);
        
        JButton addFeature = new JButton("Add");
        JButton delFeature = new JButton("Delete");
        JButton addValue = new JButton("Add");
        JButton delValue = new JButton("Delete");
        
        addFeature.addActionListener(new AddFeatureListener());
        delFeature.addActionListener(new DeleteFeatureListener());
        addValue.addActionListener(new AddValueListener());
        delValue.addActionListener(new DeleteValueListener());
            
        add(constituentLabel, "span, split");
        add(constituents, "wrap");
        add(featuresPane);
        add(valuesPane);
        add(addFeature, "center, flowx");
        add(addValue, "center, flowx");
        add(delFeature, "cell 0 2");
        add(delValue, "cell 1 2");
    
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        GrammarEditorPanel panel = new GrammarEditorPanel();
        frame.setPanel(panel);
    }
    
    private class FeatureTableModel extends AbstractTableModel {
        public FeatureTableModel() {}
        
        public int getColumnCount() {
            return 1;
        }
    
        public int getRowCount() {
            Constituent selected = selectedConstituent();
        	return selected.getFeatures().size();
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Feature";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {

            try {
                Constituent selected = selectedConstituent();
                Feature feature = selected.getFeatures().get(rowIndex);

                return feature;
            }
            catch (IndexOutOfBoundsException ex) {
                // TODO inform user that no features exist
            }
                
            return null;
        }
        
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            for (Listener listener : listeners) {
                listener.editFeature(selectedFeature(), (String) value);
            }
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    private class ValueTableModel extends AbstractTableModel {
        public ValueTableModel() {}

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public int getRowCount() {
            Feature selected = selectedFeature();
        	if (selected != null)
        	    return selected.getPossibleValues().size();
        	else return 0;
        }

        @Override
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Value";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Feature selected = selectedFeature();
            
            List<String> possibleValues = selected.getPossibleValues();
            
            return possibleValues.get(rowIndex);
        }
        
        @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
            for (Listener listener : listeners) {
                listener.editValue(selectedFeature(), (String) value, rowIndex);
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                featureModel.fireTableDataChanged();
                valuesModel.fireTableDataChanged();
            }
        }
    }
    
    private Constituent selectedConstituent() {
        int index = constituents.getSelectedIndex();
        return constituents.getItemAt(index);
    }
    
    private Feature selectedFeature() {
        int row = featuresTable.getSelectedRow();
        int col = featuresTable.getSelectedColumn();
        return (Feature) featuresTable.getValueAt(row, col);
    }
    
    private String selectedValue() {
        int row = valuesTable.getSelectedRow();
        int col = valuesTable.getSelectedColumn();
        return (String) valuesTable.getValueAt(row, col);
    }
    
    private class FeatureTableListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent ev) {
            valuesModel.fireTableDataChanged();
        }

        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}

    }
    
    private class AddFeatureListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // TODO Auto-generated method stub
            
        }
    }
    
    private class DeleteFeatureListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (selectedFeature() == null) {
                return;
            }
            for (Listener listener : listeners) {
                listener.deleteFeature(selectedFeature());
            }
        }
    }
    
    private class AddValueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            // TODO Auto-generated method stub
            
        }
    }
    
    private class DeleteValueListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            int index = valuesTable.getSelectedRow();
            
            // None selected
            if (index == -1) {
                return;
            }
            
            for (Listener listener : listeners) {
                listener.deleteValue(selectedFeature(), index);
            }
        }
    }
}
