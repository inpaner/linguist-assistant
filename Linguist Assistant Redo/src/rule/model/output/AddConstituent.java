package rule.model.output;

import grammar.model.Category;

import java.util.ArrayList;
import java.util.List;

import semantics.model.Constituent;

public class AddConstituent extends Output {
    private Constituent toAdd;
    private Constituent target;
    private String key;
    private List<Output> outputs = new ArrayList<>();
    
    public AddConstituent(Constituent target, Category category, String key) {
        this.target = target;
        toAdd = new Constituent();
        toAdd.setCategory(category);
        root.store(key, toAdd);
        
    }
    
    public void addOutput(Output output) {
        this.outputs.add(output);
    }
    
    @Override
    public void apply() {
         for (Output output : outputs) {
             output.apply();
         }
         target.addChild(toAdd);
    }

}
