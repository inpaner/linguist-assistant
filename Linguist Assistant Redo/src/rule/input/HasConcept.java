package rule.input;

import ontology.model.Concept;
import semantics.model.Constituent;

public class HasConcept extends Input {
    private Concept concept;
    
    public HasConcept(Concept concept) {
        setConcept(concept);
    }
    
    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        if (concept.equals(constituent.getConcept())) {
            result = true;
        }    
    
        return result;
    }
}
