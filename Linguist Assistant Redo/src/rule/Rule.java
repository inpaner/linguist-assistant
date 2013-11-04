package rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexicon.model.Language;
import rule.input.Input;
import rule.output.Output;
import semantics.model.Constituent;

public class Rule {
    private Map<String, Constituent> constituents = new HashMap<>();
    private Language language;
    private Input input;
    private List<Output> outputs = new ArrayList<>();
    private Switch semanticSwitch;
    private boolean localSwitch;
    
    public Rule() {
        semanticSwitch = new Switch();
        localSwitch = true;
    }
    
    public Rule createLocalRule() {
        Rule result = new Rule();
        result.constituents = this.constituents;
        result.input = this.input;
        result.outputs = this.outputs;
        result.semanticSwitch = this.semanticSwitch;
        result.input.setRoot(this.input.getRoot());
        return result;
    }
    
    public Rule createPassedRule() {
        Rule result = createLocalRule();
        return result;
    }
    
    public void toggleSemanticSwitch() {
        semanticSwitch.isOn = !semanticSwitch.isOn;
    }
    
    public void toggleLocalSwitch() {
        localSwitch = !localSwitch;
    }
    
    public boolean getSemanticSwitch() {
        return semanticSwitch.isOn;
    }
    
    public boolean getLocalSwitch() {
        return localSwitch;
    }
    
    public boolean evaluate(Constituent constituent) {
        constituents = new HashMap<>();
        input.setRoot(this); // questionable
        return input.evaluate(constituent);
    }

    public void apply() {
        if (semanticSwitch.isOn && localSwitch) {
            for (Output output : outputs) {
                output.setRoot(this);
                output.apply();
            }
        }
    }
    
    private class Switch {
        boolean isOn = true;
    }
    
    public void store(String key, Constituent value) {
        constituents.put(key, value);
    }
    
    public Constituent getStored(String key) {
        return constituents.get(key);
    }
    
    public void setInput(Input input) {
        this.input = input;
    }
    
    public void addOutput(Output output) {
        outputs.add(output);
    }
}
