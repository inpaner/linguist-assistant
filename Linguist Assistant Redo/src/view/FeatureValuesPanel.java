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
public class FeatureValuesPanel extends JPanel {
    List<TableCellEditor> editors = new ArrayList<TableCellEditor>(3);

    public FeatureValuesPanel() {
        Constituent con = new Constituent("C", null);
        for (Feature feature : con.getAllFeatures()) {
            JComboBox<String> comboBox = new JComboBox<>(new Vector<>(feature.getPossibleValues()));
            DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
            comboBox.setActionCommand(feature.getName());
            comboBox.setSelectedItem(feature.getValue());
            comboBox.addItemListener(comboListener());
            editors.add(cellEditor);
        }

        FeatureTableModel model = new FeatureTableModel(con);
        
        @SuppressWarnings("serial")
        JTable table = new JTable(model) {
            public TableCellEditor getCellEditor(int row, int column) {
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1)
                    return editors.get(row);
                else
                    return super.getCellEditor(row, column);
            }
        };
        setLayout(new MigLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
    
    @SuppressWarnings("serial")
    private class FeatureTableModel extends AbstractTableModel {
        List<Feature> features;
        public FeatureTableModel(Constituent constituent) {
            features = constituent.getAllFeatures();
        }

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
            switch (columnIndex) {
                case 0: return feature.getName();
                case 1: return feature.getValue();
                default: return feature.getValue(); // questionable
            }
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return (columnIndex == 1);
        }
    }
    
    private ItemListener comboListener() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox<String> comboBox = (JComboBox<String>) ev.getSource();
                    System.out.println(comboBox.getActionCommand() + " : " + comboBox.getSelectedItem());
                }
                
            }
        };
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        FeatureValuesPanel panel = new FeatureValuesPanel();
        frame.setPanel(panel);
    }
}