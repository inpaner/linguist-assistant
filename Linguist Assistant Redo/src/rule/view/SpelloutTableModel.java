package rule.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public class SpelloutTableModel extends AbstractTableModel {
    private List<JButton> rows;
    private List<JButton> cols;
    private List<List<String>> values;
    
    SpelloutTableModel(List<JButton> rows, List<JButton> cols) {
        this.rows = rows;
        this.cols = cols;
        values = new ArrayList<>();
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
            return "";
        }
        
        return values.get(rowIndex).get(columnIndex - 1);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        String string = value.toString();
        values.get(rowIndex).set(columnIndex - 1, string);
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean result = true;
        if (columnIndex == 0)
            result = false;
        
        return result;
    }
}
