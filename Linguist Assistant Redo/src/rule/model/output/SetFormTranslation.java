package rule.model.output;

import lexicon.model.Entry;
import lexicon.model.Form;
import ontology.model.Concept;
import semantics.model.Constituent;

public class SetFormTranslation extends Output {
    private String formName;
    
    public SetFormTranslation(String formName) {
        this.formName = formName;
    }
    
    @Override
    public void apply() {
        Constituent target = root.getStored(key);
        Concept concept = target.getConcept();
        Entry entry = concept.getFirstMapping();
        Form form = entry.getForm(formName);
        
        if (form != null) {
            target.getTarget().setTranslation( form.getValue() );
        }
    }
}
