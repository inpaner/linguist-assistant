package rule;

import grammar.model.Category;
import rule.model.RuleSet;
import rule.spellout.RuleMakerDialog;

public class RuleMaker {
    private RuleSet result;
    
    public static void main(String[] args) {
        Category noun = Category.getByName("Noun");
        RuleSet set = RuleMaker.getRuleSet(noun);
        System.out.println(set.getRules().size());
    }
    
    public static RuleSet getRuleSet(Category category) {
        RuleMaker maker = new RuleMaker(category);
        return maker.getResult();
        
    }
    
    private RuleMaker(Category category) {
        new RuleMakerDialog(new Listener(), category);
    }
    
    private RuleSet getResult() {
        return result;
    }
    
    private class Listener implements RuleMakerDialog.Listener {

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
