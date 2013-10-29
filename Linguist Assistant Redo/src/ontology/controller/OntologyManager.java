package ontology.controller;

import lexicon.model.Entry;
import ontology.model.Concept;
import ontology.model.Tag;
import ontology.view.OntologyManagerUi;
import commons.main.MainFrame;

public class OntologyManager {
    private static OntologyManagerUi panel;
    
    public static void run(MainFrame frame) {
        if (panel == null) {
            new OntologyManager(frame);
        }
        frame.setPanel(panel);
    }
    
    private OntologyManager(MainFrame frame) {
        panel = new OntologyManagerUi(frame);
        panel.addListener(new OntologyUiListener());
        
    }
    
    private class OntologyUiListener implements OntologyManagerUi.Listener {

        @Override
        public void add() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void delete(Concept concept) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void addTag(Concept concept) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void delTag(Concept concept, Tag tag) {
            concept.deleteTag(tag);
            panel.update(concept);
        }

        @Override
        public void addMapping(Concept concept) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void delMapping(Concept concept, Entry entry) {
            concept.addMapping(entry);
            panel.update(concept);
        }
    }
}
