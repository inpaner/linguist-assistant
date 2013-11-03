package rule;

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
    private List<Output> outputs;
    private Switch semanticSwitch;
    private boolean localSwitch;
    
    private Rule() {
        semanticSwitch = new Switch();
        localSwitch = true;
    }
    
    public Rule copyRule() {
        Rule copy = new Rule();
        copy.semanticSwitch = this.semanticSwitch;
        return copy;
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
        input.setRoot(this); // questionable
        return input.evaluate(constituent);
    }

    private class Switch {
        boolean isOn = true;
    }
    
    public void store(String key, Constituent value) {
        constituents.put(key, value);
    }
    
    public void setInput(Input input) {
        this.input = input;
    }
    
    public void addOutput(Output output) {
        outputs.add(output);
    }
}
