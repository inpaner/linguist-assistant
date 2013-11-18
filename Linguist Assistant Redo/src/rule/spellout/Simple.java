package rule.spellout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import commons.main.MainFrame;
import grammar.model.Category;
import grammar.model.Feature;
import rule.FeatureSelector;
import rule.RuleUtils;
import rule.model.Rule;
import rule.model.input.And;
import rule.model.input.HasCategory;
import rule.model.input.Input;
import rule.model.output.Output;
import rule.model.output.SetAffix;
import rule.spellout.view.SimpleUi;
import semantics.model.Affix;

public class Simple implements Spellout {
    
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
        
        
        Input featuresInput = RuleUtils.getInput(featuresList);
        Input categoryInput = new HasCategory(category);
        
        And rootInput = new And();
        rootInput.addRule(categoryInput);
        // Trigger Word
        
        // Forms
        
        // Features
        if (featuresInput != null) {
            rootInput.addRule(featuresInput);
        }
        
        
        Output modOutput = null;
        switch(modType) {
            case ADD_WORD:
                break;
            
            case CIRUMFIX:
                break;
            
            case INFIX:
                break;
            
            case NEW_TRANSLATION:
                break;
            
            case PREFIX:
            case SUFFIX:
                Affix affix = Affix.get(modType.toString());
                modOutput = new SetAffix(affix, view.getAffixText());
                break;
            
            default:
                break;    
        }
        
        
        
        Rule result = new Rule();
        
        result.setInput(rootInput);
        result.addOutput(modOutput);
        return result;
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
            view.setModType(type);
        }

        @Override
        public void selectedWord() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void selectedFeatures() {
            FeatureSelector selector = FeatureSelector.getSelector(category, "", featuresList);
            featuresList = selector.getFeatures();
            view.setFeatures(featuresList);
        }
        
    }

    @Override
    public JPanel getView() {
        return view;
    }

}
