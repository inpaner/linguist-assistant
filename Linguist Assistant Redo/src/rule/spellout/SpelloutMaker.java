package rule.spellout;

import grammar.model.Category;
import rule.model.Rule;
import rule.model.RuleSet;
import rule.spellout.view.SpelloutDialog;

public class SpelloutMaker {
    private Rule result;
    
    public static void main(String[] args) {
        Category noun = Category.getByName("Noun");
        Rule set = SpelloutMaker.getRules(noun);
        
    }
    
    public static Rule getRules(Category category) {
        SpelloutMaker maker = new SpelloutMaker(category);
        return maker.getResult();
        
    }
    
    private SpelloutMaker(Category category) {
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
            // TODO Auto-generated method stub
        }
        
    }
}
