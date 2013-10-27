package lexicon.view.table;

import java.util.List;

import javax.swing.JTable;

import lexicon.model.Entry;

public class StemStrategy implements TableStrategy {
    LexiconTableModel stemModel;
    
    public StemStrategy() {
        stemModel = new StemModel();
    }
    
    @Override
    public void update(JTable table, List<Entry> entries) {
        stemModel.update(entries);
        table.setModel(stemModel);
        stemModel.fireTableDataChanged();
    }

}
