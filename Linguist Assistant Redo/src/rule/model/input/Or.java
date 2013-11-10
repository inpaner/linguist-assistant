package rule.model.input;

import java.util.ArrayList;
import java.util.List;

import rule.model.Rule;
import semantics.model.Constituent;

public class Or extends Input {
    private List<Input> conditions = new ArrayList<>();
    
    public void addRule(Input rule) {
        conditions.add(rule);
    }
    
    /**
     * Short circuit OR evaluation
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        for (Input rule : conditions) {
            if (rule.evaluate(constituent) == true)
                return true;
        }
        return false;
    }


    @Override
    public void setRoot(Rule root) {
        super.setRoot(root);
        for (Input rule : conditions) {
            rule.setRoot(root);
        }
    }
}
