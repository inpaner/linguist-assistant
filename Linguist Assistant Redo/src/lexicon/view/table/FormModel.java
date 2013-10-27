package lexicon.view.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import lexicon.model.Entry;
import lexicon.model.Form;

public class FormModel extends LexiconTableModel {
    List<Entry> entries;
    List<List<Form>> allForms;
    int columns;
    
    FormModel(List<Entry> entries) {
        this.entries = entries;
        allForms = new ArrayList<>();
        for (Entry entry : entries) {
            List<Form> forms = entry.getForms(); 
            allForms.add(forms);
        }
        columns = 2;
        if (allForms.size() > 0) {
            List<Form> forms = allForms.get(0);
            columns += forms.size();
        }
    }
    
    public int getColumnCount() {
        return columns;
    }
    
    public int getRowCount() {
        return entries.size();
    }
    
    public String getColumnName(int columnIndex) {
        String result = "";
        switch (columnIndex) {
        case 0: result = "Stem";
                break;
        case 1: result = "Gloss";
                break;
        default: columnIndex -= 2;
                 if (allForms.size() > 0) {
                     List<Form> forms = allForms.get(0);
                     result = forms.get(columnIndex).getName();
                 } 
        }
        
        return result;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Entry entry = entries.get(rowIndex);
        Object result = "";
        switch (columnIndex) {
            case 0: result = entry.getStem();
                    break;
            case 1: result = entry.getGloss();
                    break;
            default: columnIndex -= 2; 
                     if (allForms.size() > 0) {
                         List<Form> forms = allForms.get(rowIndex);
                         result = forms.get(columnIndex).getValue();
                     }
        }
        return result;
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
