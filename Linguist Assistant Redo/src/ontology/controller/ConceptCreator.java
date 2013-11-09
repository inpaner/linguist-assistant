package ontology.controller;

import grammar.model.Category;
import ontology.model.Concept;
import ontology.view.AddConceptDialog;
import ontology.view.AddConceptDialog.Event;

public class ConceptCreator {
    private Concept created;
    
    public static Concept create() {
        ConceptCreator controller = new ConceptCreator();
        return controller.getCreated();
    }
    
    private Concept getCreated() {
        return created;
    }
    
    private ConceptCreator() {
        new AddConceptDialog(new DialogListener());
    }
    
    private class DialogListener implements AddConceptDialog.Listener {

        @Override
        public void add(Event event) {
            String stem = event.getStem();
            String gloss = event.getGloss();
            Category category = event.getCategory();
            
            created = Concept.getEmpty(category);
            created.setStem(stem);
            created.setGloss(gloss);
        }

        @Override
        public void cancel() {
            created = null;
        }
        
    }
}
