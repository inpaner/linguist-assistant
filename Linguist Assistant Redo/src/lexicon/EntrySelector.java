package lexicon;

import grammar.model.Category;
import lexicon.model.Entry;
import lexicon.view.SelectEntryDialog;

public class EntrySelector {
    private Entry selected;
    
    public static Entry select(Category category) {
        EntrySelector controller = new EntrySelector(category);
        return controller.getSelected();
    }
    
    private EntrySelector(Category category) {
        new SelectEntryDialog(new DialogListener(), category);
    }
    
    private Entry getSelected() {
        return selected;
    }
    
    private class DialogListener implements SelectEntryDialog.Listener {

        @Override
        public void select(Entry entry) {
            selected = entry;
        }

        @Override
        public void cancel(Entry entry) {
            selected = null;
        }
        
    }
}
