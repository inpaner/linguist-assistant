package semantics.controller;

import semantics.view.BlockListener;
import semantics.view.SemanticEditorPanel;
import grammar.controller.SelectConstituent;
import grammar.controller.SelectConstituent.Listener;
import grammar.model.Constituent;
import grammar.model.Feature;
import grammar.view.AddConstituentListener;
import grammar.view.AddConstituentPanel;
import grammar.view.FeatureValuesListener;
import grammar.view.GenericDialog;
import commons.dao.DBUtil;
import commons.main.MainFrame;

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
        
        Constituent con = new Constituent();//Constituent.getByAbbreviation("C");
        con.setLabel("");
        /*Constituent con2 = Constituent.getByAbbreviation("N");
        Constituent con3 = Constituent.getByAbbreviation("V");
        
        con.addChild(con2);
        con.addChild(con3);*/
        
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
            SelectConstituent selectConstituent = new SelectConstituent(dropped, destination, index);
            selectConstituent.addListener(new AddConstituentListener());
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
    
    private class AddConstituentListener implements SelectConstituent.Listener {
        @Override
        public void done() {
            display.refresh();
        }
    }

}
