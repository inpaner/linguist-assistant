package rule;

import java.util.ArrayList;
import java.util.List;

import rule.view.FeatureSelectorDialog;
import grammar.model.Category;
import grammar.model.Feature;

public class FeatureSelector {
    private String title;
    private List<List<Feature>> features = new ArrayList<>();
    
    public static void main(String[] args) {
        Category noun = Category.getByName("Noun");
        FeatureSelector selector = FeatureSelector.getSelector(noun);
        System.out.println(selector.getTitle());
        System.out.println(selector.getFeatures().size());
    }
    
    public static FeatureSelector getSelector(Category category) {
        return new FeatureSelector(category);
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
