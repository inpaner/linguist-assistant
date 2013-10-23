package semantics.controller;

import grammar.controller.AddConstituent;
import grammar.controller.AddConstituent.Listener;
import grammar.model.Constituent;
import grammar.model.Feature;
import grammar.view.AddConstituentListener;
import grammar.view.AddConstituentPanel;
import grammar.view.BlockListener;
import grammar.view.FeatureValuesListener;
import grammar.view.GenericDialog;
import grammar.view.SemanticEditorPanel;
import commons.dao.DBUtil;
import commons.view.MainFrame;

public class SemanticEditor {
    private SemanticEditorPanel display;
    
    public static void main(String[] args) {
        new SemanticEditor();
    }
    
    public SemanticEditor() {
        MainFrame frame = new MainFrame();
        display = new SemanticEditorPanel();
        display.addBlockListener(new ImpBlockListener());
        display.addFeatureValuesListener(new ImpFeatureValuesListener());
        frame.setPanel(display);
        
        Constituent con = Constituent.getBySyntacticAbbr("C");
        Constituent con2 = Constituent.getBySyntacticAbbr("N");
        Constituent con3 = Constituent.getBySyntacticAbbr("V");
        
        con.addChild(con2);
        con.addChild(con3);
        
        display.updateConstituent(con);
    }
    
    private class ImpBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {}

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {
            destination.moveChild(dropped, index);
            display.refresh();
        }

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {
            AddConstituent addConstituent = new AddConstituent(dropped, destination, index);
            addConstituent.addListener(new AddConstituentListener());
        }
    }
    
    private class ImpFeatureValuesListener implements FeatureValuesListener {
        @Override
        public void featureValueChanged(Feature feature, String newValue) {
            Constituent parent = feature.getParent();
            parent.updateFeature(feature, newValue);
            display.refresh();
        }
    }
    
    private class AddConstituentListener implements AddConstituent.Listener {
        @Override
        public void done() {
            display.refresh();
        }
    }

}
