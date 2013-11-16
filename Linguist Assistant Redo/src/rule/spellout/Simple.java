package rule.spellout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import commons.main.MainFrame;

import grammar.model.Category;
import grammar.model.Feature;
import rule.FeatureSelector;
import rule.model.Rule;
import rule.spellout.view.SimpleUi;

public class Simple implements Spellout {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        Simple simple = new Simple(Category.getByName("Noun"));
        frame.setPanel(simple.view);
    }
    
    Category category;
    SpelloutType type = SpelloutType.SIMPLE;
    SimpleUi view;
    ModType modType;
    List<List<Feature>> featuresList = new ArrayList<>();
    
    public Simple(Category category) {
        this.category = category;
        view = new SimpleUi(category);
        view.addListener(new ViewListener());
        modType = ModType.PREFIX;
    }
    
    @Override
    public Rule getRule() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void loadRule(Rule rule) {
        // TODO Auto-generated method stub

    }
    
    private class ViewListener implements SimpleUi.Listener {

        @Override
        public void selectedStructures() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void selectedMod(ModType type, Boolean reduplication) {
            Simple.this.modType = type;
        }

        @Override
        public void selectedTrigger() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void selectedFeatures() {
            FeatureSelector selector = FeatureSelector.getSelector(category, "", featuresList);
            featuresList = selector.getFeatures();
            view.setFeatures(featuresList);
        }
        
    }

}
