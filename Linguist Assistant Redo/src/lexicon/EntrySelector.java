package lexicon;

import lexicon.model.Entry;
import lexicon.view.SelectEntryDialog;

public class EntrySelector {
    private Entry selected;
    
    public static void main(String[] args) {
        Entry con = EntrySelector.select();
        if (con == null) {
            System.out.println("null");
        }
        else 
            System.out.println(con.getLanguage());
    }
    
    
    
    public static Entry select() {
        EntrySelector controller = new EntrySelector();
        return controller.getSelected();
    }
    
    private EntrySelector() {
        new SelectEntryDialog(new DialogListener());
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
