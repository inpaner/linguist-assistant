package ontology.controller;

import ontology.model.Concept;
import ontology.view.SelectConceptDialog;

public class ConceptSelector {
    private Concept selected;
    
    public static void main(String[] args) {
        Concept con = ConceptSelector.select();
        if (con == null) {
            System.out.println("null");
        }
        else 
            System.out.println(con.getStem());
    }
    
    public static Concept select() {
        ConceptSelector controller = new ConceptSelector();
        return controller.getSelected();
    }
    
    private Concept getSelected() {
        return selected;
    }
    
    private ConceptSelector() {
        new SelectConceptDialog(new DialogListener());
    }
    
    private class DialogListener implements SelectConceptDialog.Listener {

        @Override
        public void select(Concept concept) {
            selected = concept;
        }

        @Override
        public void cancel(Concept concept) {
            selected = null;
        }
        
    }
}
