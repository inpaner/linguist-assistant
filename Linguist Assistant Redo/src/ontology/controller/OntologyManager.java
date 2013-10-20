package ontology.controller;

import grammar.model.Constituent;

import java.util.List;

import ontology.model.Concept;
import ontology.view.OntologyList;
import commons.view.MainFrame;

public class OntologyManager {
    Constituent noun;
    OntologyList panel;
    
    public static void main(String[] args) {
        new OntologyManager().testCase();
    }
    
    private void testCase() {
        MainFrame frame = new MainFrame();
        panel = new OntologyList();
        frame.setPanel(panel);

        panel.addListener(new OntologyListListener());    
        noun = Constituent.getBySyntacticAbbr("N");
        
        List<Concept> concepts = Concept.getInstances("", noun); 
        
        panel.refreshConcepts(concepts);
    }
    
    private class OntologyListListener implements OntologyList.Listener {
        @Override
        public void searchChanged(String text) {
            
            // DB! gets all concepts with stem containing the text
            List<Concept> concepts = Concept.getInstances(text, noun); 
            panel.refreshConcepts(concepts);
        }

        @Override
        public void selected(Concept selected) {
            // does nothing for now
            // will be helpful for displaying details
            System.out.println(selected);
        }
    }
}
