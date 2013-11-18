package rule.spellout.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import rule.generic.FeatureButton;

public class SpelloutTableModel extends AbstractTableModel {
    private List<FeatureButton> rows;
    private List<FeatureButton> cols;
    private List<List<String>> values = new ArrayList<>();
    
    public SpelloutTableModel(List<FeatureButton> rows, List<FeatureButton> cols) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows.size(); i++) {
            List<String> colList = new ArrayList<>();
            
            for (int j = 0; j < cols.size(); j++) {
                colList.add("");
            }
            values.add(colList);
        }
    }
    
    @Override
    public int getColumnCount() {
        return cols.size() + 1;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return rows.get(rowIndex).getTitle();
        }
        else {
            return values.get(rowIndex).get(columnIndex - 1);
        }
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return;
        }
        String string = value.toString();
        values.get(rowIndex).set(columnIndex - 1, string);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean result = true;

        return result;
    }
    
    public void addRow() {
        List<String> newRow = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            newRow.add("");
        }
        
        values.add(newRow);
    }
    
    public void addColumn() {
        for (List<String> row : values) {
            row.add("");
        }
    }
}
