package ontology.controller;

import grammar.model.Constituent;

import java.util.List;

import ontology.model.Concept;
import ontology.view.OntologyListPanel;
import commons.view.MainFrame;

public class OntologyManager {
    Constituent noun;
    OntologyListPanel panel;
    
    public static void main(String[] args) {
        new OntologyManager().testCase();
    }
    
    private void testCase() {
        // initialize GUI
        MainFrame frame = new MainFrame();
        panel = new OntologyListPanel();
        frame.setPanel(panel);

        // add the implemented listener created just below
        panel.addListener(new OntologyListListener());    
        noun = Constituent.get("N");
        List<Concept> concepts = Concept.getInstances("", noun); // DB! gets everything
        panel.refreshConcepts(concepts);
    }
    
    // listener for the panel. interface definition is in the class itself
    private class OntologyListListener implements OntologyListPanel.Listener {
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
