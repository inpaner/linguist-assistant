package rule;

import java.util.HashMap;
import java.util.Map;

import rule.input.Input;
import semantics.model.Constituent;

public class Rule {
    private Map<String, Constituent> constituents = new HashMap<>();
    private Input input;
    private Switch semanticSwitch;
    private boolean localSwitch;
    
    private Rule() {
        semanticSwitch = new Switch();
        localSwitch = true;
    }
    
    public Rule copyRule() {
        Rule copy = new Rule();
        
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
    
    private class Switch {
        boolean isOn = true;
    }
}
