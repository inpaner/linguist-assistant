package rule.spellout;

import grammar.model.Category;
import rule.model.Rule;
import rule.model.RuleSet;
import rule.spellout.view.SpelloutDialog;

public class SpelloutWrapper {
    private Rule result;
    
    public static void main(String[] args) {
        Category noun = Category.getByName("Noun");
        Rule set = SpelloutWrapper.getRules(noun);
        
    }
    
    public static Rule getRules(Category category) {
        SpelloutWrapper maker = new SpelloutWrapper(category);
        return maker.getResult();
        
    }
    
    private SpelloutWrapper(Category category) {
        new SpelloutDialog(new Listener(), category);
    }
    
    private Rule getResult() {
        return result;
    }
    
    private class Listener implements SpelloutDialog.Listener {

        @Override
        public void select(RuleSet ruleSet) {
            result = ruleSet;
        }

        @Override
        public void cancel() {
            result = null;
        }
        
    }
}
