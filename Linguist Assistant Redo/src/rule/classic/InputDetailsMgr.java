package rule.classic;

import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import ontology.controller.ConceptSelector;
import ontology.model.Concept;
import rule.FeatureSelector;
import rule.classic.view.InputDetailsPanel;

public class InputDetailsMgr {
    private InputDetailsPanel panel;
    private List<Listener> listeners = new ArrayList<>();
    
    public interface Listener {
        void done();
    }
    
    public InputDetailsMgr(InputDetailsPanel panel) {
        this.panel = panel;
        panel.addListener(new PanelListener());
    }
    
    public void setConstituent(InputCons cons) {
        panel.setConstituent(cons);
        
    }
    
    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    
    private class PanelListener implements InputDetailsPanel.Listener {

        @Override
        public void addConcept(InputCons cons) {
            Concept concept = ConceptSelector.select();
            if (concept != null) {
                cons.setConcept(concept);
                panel.setConstituent(cons);
                for (Listener listener : listeners) {
                    listener.done();
                }
            }
        }

        @Override
        public void deleteConcept(InputCons cons) {
            cons.setConcept(null);
            panel.setConstituent(cons);
            for (Listener listener : listeners) {
                listener.done();
            }
        }

        @Override
        public void editFeatures(InputCons cons) {
            List<List<Feature>> features = cons.getFeatureInputs();
            features = FeatureSelector.select(cons.getCategory(), features);
            cons.setFeatureInputs(features);
            panel.setConstituent(cons);
            for (Listener listener : listeners) {
                listener.done();
            }
        }

        @Override
        public void toggleOptional(InputCons cons, boolean optional) {
            cons.setOptional(optional);
            panel.setConstituent(cons);
            for (Listener listener : listeners) {
                listener.done();
            }
        }
        
    }
}
