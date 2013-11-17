package rule.classic;

import grammar.model.Feature;

import java.util.List;

import ontology.controller.ConceptSelector;
import ontology.model.Concept;
import rule.FeatureSelector;
import rule.classic.view.InputDetailsPanel;

public class InputDetailsMgr {
    private InputDetailsPanel panel;
    
    public InputDetailsMgr(InputDetailsPanel panel) {
        this.panel = panel;
        
        
    }
    
    public void setConstituent(InputCons cons) {
        panel.setConstituent(cons);
    }
    
    private class Listener implements InputDetailsPanel.Listener {

        @Override
        public void addConcept(InputCons cons) {
            Concept concept = ConceptSelector.select();
            if (concept != null) {
                cons.setConcept(concept);
            }
        }

        @Override
        public void deleteConcept(InputCons cons) {
            cons.setConcept(null);
        }

        @Override
        public void editFeatures(InputCons cons) {
            List<List<Feature>> features = cons.getFeatureInputs();
            features = FeatureSelector.select(cons.getCategory(), features);
            
        }

        @Override
        public void toggleOptional(InputCons cons, boolean optional) {
            cons.setOptional(optional);
        }
        
    }
}
