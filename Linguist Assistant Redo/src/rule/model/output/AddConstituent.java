package rule.model.output;

import grammar.model.Category;

import java.util.ArrayList;
import java.util.List;

import rule.model.Rule;
import semantics.model.Constituent;

public class AddConstituent extends Output {
    private Constituent toAdd;
    private List<Output> outputs = new ArrayList<>();
    
    public AddConstituent(Category category, String key) {
        toAdd = new Constituent();
        toAdd.setCategory(category);
    }
    
    public void addOutput(Output output) {
        this.outputs.add(output);
    }
    
    @Override
    public void apply() {
        Constituent target = root.getStored(key);
        
        Rule emptyRule = new Rule();
        emptyRule.store("addcons", toAdd);
        
        for (Output output : outputs) {
            output.setKey("addcons");
            emptyRule.addOutput(output);
            toAdd.evaluate(emptyRule);
            toAdd.applyRules();        
        }
        target.addChild(toAdd);
    }

}
