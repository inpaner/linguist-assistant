package rule.model.output;

import semantics.model.Constituent;
import semantics.model.Target;

public class ForceTranslation extends Output {
    private String translation;
    
    public ForceTranslation(String translation) {
        this.translation = translation;
    }
    
    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public void apply() {
        System.out.println("Key = " + key);
        Constituent constituent = root.getStored(key);
        Target target = constituent.getTarget();
        target.setTranslation(translation);
    }

}
