package ontology.controller;

import ontology.model.Concept;
import ontology.view.SelectConceptDialog;

public class OntologySelector {
    
    public static void main(String[] args) {
        Concept con = OntologySelector.select();
        if (con == null) {
            System.out.println("null");
        }
        else 
            System.out.println(con.getStem());
    }
    
    public static Concept select() {
        OntologySelector controller = new OntologySelector();
        return controller.getSelected();
    }
    
    private Concept selected;
    private SelectConceptDialog dialog;
    
    private Concept getSelected() {
        return selected;
    }
    
    private OntologySelector() {
        dialog = new SelectConceptDialog(new DialogListener());
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
