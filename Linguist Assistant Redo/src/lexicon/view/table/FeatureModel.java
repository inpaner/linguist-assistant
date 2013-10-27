package lexicon.view.table;

import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import lexicon.model.Entry;

public class FeatureModel extends LexiconTableModel {
    List<List<Feature>> allFeatures;
    int columns;
    
    FeatureModel(List<Entry> entries) {
        this.entries = entries;
        allFeatures = new ArrayList<>();
        for (Entry entry : entries) {
            List<Feature> features = entry.getFeatures(); 
            allFeatures.add(features);
        }    
        columns = 2;
        if (allFeatures.size() > 0) {
            List<Feature> features = allFeatures.get(0);
            columns += features.size();
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
                 if (allFeatures.size() > 0) {
                     List<Feature> temp = allFeatures.get(0);
                     result = temp.get(columnIndex).getName();
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
                     if (allFeatures.size() > 0) {
                         List<Feature> features = allFeatures.get(rowIndex);
                         result = features.get(columnIndex).getValue();
                     }
        }
        return result;
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
