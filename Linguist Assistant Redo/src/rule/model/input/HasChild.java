package rule.model.input;

import rule.model.Rule;
import semantics.model.Constituent;

public class HasChild extends Input {
    private Input condition;
    private String key;
    
    public HasChild(String key, Input condition) {
        setKey(key);
        setCondition(condition);
    }
    
    public void setCondition(Input condition) {
        this.condition = condition;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
    
    /**
     * Evaluates each constituent child with the set condition.
     * 
     * Stores the child fulfilling the condition on Rule's map
     * using a key. Returns true for the first child found.
     * 
     */
    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        for (Constituent child : constituent.getChildren()) {
            if (condition.evaluate(child)) {
                result = true;
                root.store(key, child);
                break;
            }    
        }
        
        return result;
    }

    @Override
    public void setRoot(Rule root) {
        super.setRoot(root);
        condition.setRoot(root);
    }
    
    public Input getCondition(){
    	return condition;
    }
}
