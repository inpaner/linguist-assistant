package grammar.view;

import grammar.model.Constituent;
import grammar.model.Feature;

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

// http://stackoverflow.com/a/4211552
@SuppressWarnings("serial")
public class FeatureValuesPanel extends JPanel {
    private List<TableCellEditor> editors;
    private JTable table;
    private List<FeatureValuesListener> listeners;
    private List<Feature> features;
    private ComboListener comboListener;
    
    public FeatureValuesPanel() {
        comboListener = new ComboListener();
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
        features = constituent.getFeatures();
        for (Feature feature : features) {
        	System.out.println("Adding feature");
            FeatureComboBox comboBox = new FeatureComboBox(feature);
            comboBox.addListener(comboListener);
            DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
            editors.add(cellEditor);
        }

        FeatureTableModel model = new FeatureTableModel();
        table.setModel(model);
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
                default: value = "";
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
    
    private class ComboListener implements FeatureComboBox.Listener {
        @Override
        public void featureValueChanged(Feature feature, String value) {
            for (FeatureValuesListener listener : listeners) {
                listener.featureValueChanged(feature, value);
            }
        }
    }
}