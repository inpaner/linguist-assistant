package rule.input;

import java.util.ArrayList;
import java.util.List;

import rule.Rule;
import semantics.model.Constituent;

public class HasChild extends Input {
    private List<Input> conditions = new ArrayList<>();
    private String key;
    
    public void addRule(Input condition) {
        conditions.add(condition);
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
    
    /**
     * Evaluates each constituent child with the added conditions.
     * 
     * Stores the child fulfilling the condition on Rule's map
     * using a key. Returns true for the first child found.
     * 
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        checkNextChild:
        for (Constituent child : constituent.getChildren()) {
            for (Input condition : conditions) {
                if (!condition.evaluate(child)) {
                    continue checkNextChild;
                }    
            }
            result = true;
            root.store(key, child);
            break;
        }
        
        return result;
    }

    @Override
    public void setRoot(Rule root) {
        super.setRoot(root);
        for (Input condition : conditions) {
            condition.setRoot(root);
        }
    }
}
