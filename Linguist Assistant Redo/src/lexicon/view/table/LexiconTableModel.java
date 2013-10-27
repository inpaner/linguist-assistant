package lexicon.view.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import lexicon.model.Entry;

@SuppressWarnings("serial")
public abstract class LexiconTableModel extends AbstractTableModel {
    protected List<Entry> entries;
    
    protected LexiconTableModel() {
        entries = new ArrayList<>();
    }
    
    public void update(List<Entry> entries) {
        this.entries = entries;
    }
    
}
