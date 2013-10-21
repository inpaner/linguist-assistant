package ontology.controller;

import grammar.model.Constituent;

import java.util.List;

import ontology.model.Concept;
import ontology.model.ConceptDAO;
import ontology.model.Tag;
import ontology.view.AddConceptDialog;
import ontology.view.OntologyList;
import ontology.view.OntologyList.Event;
import commons.dao.DAOFactory;
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
        
        List<Concept> concepts = Concept.getInstances("", Tag.getTagAll(), noun); 
        
        panel.refreshConcepts(concepts);
    }
    
    private class OntologyListListener implements OntologyList.Listener {
        @Override
        public void searchChanged(OntologyList.Event event) {
            genericRefresh(event);
        }

        @Override
        public void selectedConcept(OntologyList.Event event) {}

        @Override
        public void selectedTag(OntologyList.Event event) {
            genericRefresh(event);
        }

        @Override
        public void selectedConstituent(OntologyList.Event event) {
            genericRefresh(event);
        }

        @Override
        public void selectedAdd(OntologyList.Event event) {
            Concept toAdd = AddConceptDialog.getInstance();
            System.out.println(toAdd.getStem());
            DAOFactory factory = DAOFactory.getInstance();
            ConceptDAO dao = new ConceptDAO(factory);
            System.out.println(event.getConstituent().getLabel());
            dao.create(toAdd);
            
            genericRefresh(event);
        }
    }
    
    private void genericRefresh(OntologyList.Event event) {
        List<Concept> concepts = Concept.getInstances(
                event.getText(), event.getTag(), event.getConstituent()); 
        panel.refreshConcepts(concepts);
    }
}
