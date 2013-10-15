package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import net.miginfocom.swing.MigLayout;
import model.Constituent;
import model.Feature;

// http://stackoverflow.com/a/4211552
@SuppressWarnings("serial")
public class FeatureValuesPanel extends JPanel {
    private List<TableCellEditor> editors;
    private JTable table;
    private List<FeatureValuesListener> listeners;
    private List<Feature> features;
    
    public FeatureValuesPanel() {
        listeners = new ArrayList<>();
        table = new JTable() {
            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1) {
                    return editors.get(row);
                }
                else {
                    return super.getCellEditor(row, column);
                }
            }
        };
        setLayout(new MigLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    
    public void setConstituent(Constituent constituent) {
        editors = new ArrayList<>();
        features = constituent.getAllFeatures();
        for (Feature feature : features) {
            FeatureComboBox comboBox = new FeatureComboBox(feature);
            DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
            editors.add(cellEditor);
        }

        FeatureTableModel model = new FeatureTableModel();
        table.setModel(model);
    }
    
    private class FeatureComboBox extends JComboBox<String> {
        private Feature feature;
        private FeatureComboBox(Feature feature) {
            super(new Vector<String>(feature.getPossibleValues()));
            this.feature = feature;
            setSelectedItem(feature.getValue());
            addItemListener(new ComboListener());
        }
        
        private Feature getFeature() {
            return feature;
        }
        
        private String getValue() {
            return (String) getSelectedItem();
        }
    }

    private class FeatureTableModel extends AbstractTableModel {
        public FeatureTableModel() {}
    
        public int getColumnCount() {
            return 2;
        }
    
        public int getRowCount() {
            return features.size();
        }
        
        public String getColumnName(int columnIndex) {
            String colName;
            switch (columnIndex) {
            case 0: colName = "Name";
                    break;
            case 1: colName = "Value";
                    break;
            default: colName = ""; 
            }
            return colName;
        }
    
        public Object getValueAt(int rowIndex, int columnIndex) {
            Feature feature = features.get(rowIndex);
            Object value;
            switch (columnIndex) {
                case 0: value = feature.getName();
                        break;
                case 1: value = feature.getValue();
                        break;
                default: value = feature.getValue(); // questionable
            }
            
            return value;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 1);
        }
    }
    
    public void addFeatureValuesListener(FeatureValuesListener listener) {
        listeners.add(listener);
    }
    
    
    private class ComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                FeatureComboBox comboBox = (FeatureComboBox) ev.getSource();
                for (FeatureValuesListener listener : listeners) {
                    listener.featureValueChanged(comboBox.getFeature(), comboBox.getValue());
                }
            }
        }
    }
}