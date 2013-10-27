package lexicon.view.table;

import java.util.List;

import javax.swing.JTable;

import lexicon.model.Entry;

public class FormStrategy implements TableStrategy {
    
    @Override
    public void update(JTable table, List<Entry> entries) {
        FormModel model = new FormModel(entries);
        table.setModel(model);
        model.fireTableDataChanged();
    }

}
