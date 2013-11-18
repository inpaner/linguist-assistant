package rule.model.output;

import semantics.model.Affix;
import semantics.model.Constituent;
import semantics.model.Target;

public class SetAffix extends Output {
    
    private Affix affix;
    private String value;
    
    private String key = "root";
    
    public SetAffix(Affix affix, String value) {
        setAffix(affix);
        setValue(value);
    }
    
    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setAffix(Affix affix) {
        this.affix = affix;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Affix getAffix() {
        return affix;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void apply() {
        Constituent constituent = root.getStored(key);        
        Target target = constituent.getTarget();
        target.setAffix(affix, value);
    }

}
