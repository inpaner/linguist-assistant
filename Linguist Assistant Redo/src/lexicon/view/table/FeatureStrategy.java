package lexicon.view.table;

import java.util.List;

import javax.swing.JTable;

import lexicon.model.Entry;

public class FeatureStrategy implements TableStrategy {

    @Override
    public void update(JTable table, List<Entry> entries) {
        FeatureModel model = new FeatureModel(entries);
        table.setModel(model);
        model.fireTableDataChanged();
    }

}
