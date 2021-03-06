package rule;

import java.util.ArrayList;
import java.util.List;

import rule.generic.FeatureSelectorDialog;
import grammar.model.Category;
import grammar.model.Feature;

public class FeatureSelector {
    private String title;
    private List<List<Feature>> features = new ArrayList<>();
    
    public static List<List<Feature>> select(Category category, List<List<Feature>> features) {
        FeatureSelector selector;
        if (features == null || features.isEmpty()) {
            selector = getSelector(category);
        }
        else {
            selector = getSelector(category, "", features);
        }
        return selector.getFeatures();
    }
    
    public static FeatureSelector getSelector(Category category) {
        return new FeatureSelector(category);
    }
    
    public static FeatureSelector getSelector(
            Category category, String title, List<List<Feature>> featuresList) {
        
        List<Feature> flat = new ArrayList<>();
        
        if (featuresList == null) {
            return new FeatureSelector(category);
        }
        
        for (List<Feature> features : featuresList) {
            flat.addAll(features);
        }
        
        return new FeatureSelector(category, title, flat);
    }
    
    
    
    private FeatureSelector(Category category) {
        title = "";
        new FeatureSelectorDialog(new DialogListener(), category);
    }
    
    private FeatureSelector(Category category, String title, 
            List<Feature> features) {
        new FeatureSelectorDialog(new DialogListener(), category, title, features);
    }
    
    public String getTitle() {
        return title;
    }
    
    public List<List<Feature>> getFeatures() {
        return features;
    }
    
    private class DialogListener implements FeatureSelectorDialog.Listener {

        @Override
        public void select(String title, List<List<Feature>> features) {
            FeatureSelector.this.title = title;
            FeatureSelector.this.features = features;
            
        }

        @Override
        public void cancel() {
            // TODO Auto-generated method stub
            
        }
        
    }
}
