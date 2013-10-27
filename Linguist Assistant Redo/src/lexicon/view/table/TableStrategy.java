package lexicon.view.table;

import java.util.List;

import javax.swing.JTable;

import lexicon.model.Entry;

public interface TableStrategy {
    public abstract void update(JTable table, List<Entry> entries);
}
