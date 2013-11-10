package rule.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import grammar.model.Category;
import grammar.model.Feature;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import commons.main.MainFrame;
import net.miginfocom.swing.MigLayout;

/*
 * http://www.coderanch.com/t/343795/GUI/java/Check-Box-JTable-header
 */
public class FeatureSelectorTable extends JPanel {
    public static void main(String[] args) {
        
        MainFrame frame = new MainFrame();
        Category category = Category.getByName("Noun");
        FeatureSelectorTable panel = new FeatureSelectorTable(category);
        frame.setPanel(panel);
    }
    
    private List<List<TableCellRenderer>> editorsList = new ArrayList<>();
    private JTable table;
    private List<Feature> features;
    public FeatureSelectorTable(Category category) {
        features = category.getFeatures();
        
        setLayout(new MigLayout());

        
        for (Feature feature : features) {
            List<TableCellRenderer> editors = new ArrayList<>();
            for (String value : feature.getPossibleValues()) {
                Feature empty = Feature.getEmpty(category);
                empty.setValue(value);
                final FeatureCheckbox checkbox = new FeatureCheckbox(feature);
                System.out.println(checkbox.getValue());
                
                TableCellRenderer cellEditor = new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        return checkbox;
                    }
                };
                editors.add(cellEditor);
            }
            editorsList.add(editors);
        }
        
        
        table = new JTable() {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                List<TableCellRenderer> editors = editorsList.get(column);
                if (row < editors.size()) {
                    return editors.get(row);
                }
                else {
                    return super.getCellRenderer(row, column);
                }
            }
        };
        
        table.setModel(new FeatureTableModel());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private class FeatureTableModel extends AbstractTableModel {
        public FeatureTableModel() {}
    
        public int getColumnCount() {
            return features.size();
        }
    
        public int getRowCount() {
            int max = 0;
            for (Feature feature : features) {
                int size = feature.getPossibleValues().size();
                if (size > max) {
                    max = size;
                }
            }
            
            return max;
        }
        
        public String getColumnName(int columnIndex) {
            
            return features.get(columnIndex).getName();
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
}
