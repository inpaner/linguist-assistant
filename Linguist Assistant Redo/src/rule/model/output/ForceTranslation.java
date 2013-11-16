package rule.model.output;

import java.util.List;

import lexicon.model.Entry;
import ontology.model.Concept;
import semantics.model.Constituent;
import semantics.model.Target;

public class ForceTranslation extends Output {
    private String key = "root";
    private String translation;
    
    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public void apply() {
        Constituent constituent = root.getStored(key);        
        Target target = constituent.getTarget();
        target.setTranslation(translation);
    }

}
