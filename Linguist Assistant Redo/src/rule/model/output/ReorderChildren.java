package rule.model.output;

import java.util.ArrayList;
import java.util.List;

import semantics.model.Constituent;

public class ReorderChildren extends Output {
    private List<String> childKeys = new ArrayList<>();
    
    public ReorderChildren() {
        
    }
    
    public void addChild(String childKey) {
        childKeys.add(childKey);
    }
    
    @Override
    public void apply() {
        Constituent target = root.getStored(key);
        for (String childKey : childKeys) {
            Constituent child = root.getStored(childKey);
            if (child != null) {
                target.moveToEnd(child);
            }
        }
    }

}
