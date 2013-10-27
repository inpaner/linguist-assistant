package lexicon.view.table;

import java.util.ArrayList;
import java.util.List;

import lexicon.model.Entry;

public class StemModel extends LexiconTableModel {

    StemModel() {
        super();
    }

    public int getColumnCount() {
        return 4;
    }
    
    public int getRowCount() {
        return entries.size();
    }
    
    public String getColumnName(int columnIndex) {
        String result;
        switch (columnIndex) {
        case 0: result = "Stem";
                break;
        case 1: result = "Gloss";
                break;
        case 2: result = "Comment";
                break;
        case 3: result = "Sample Sentence";
                break;
        default: result = ""; 
        }
        
        return result;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Entry entry = entries.get(rowIndex);
        Object result;
        switch (columnIndex) {
            case 0: result = entry.getStem();
                    break;
            case 1: result = entry.getGloss();
                    break;
            case 2: result = entry.getComment();
                    break;
            case 3: result = entry.getSampleSentence();
                    break;
            default: result = "";
        }
        
        return result;
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}
