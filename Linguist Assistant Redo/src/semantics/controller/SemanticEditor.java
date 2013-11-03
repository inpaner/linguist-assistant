package semantics.controller;

import javax.swing.JOptionPane;

import semantics.model.Constituent;
import semantics.view.BlockListener;
import semantics.view.SemanticEditorPanel;
import grammar.controller.SelectConstituent;
import grammar.controller.SelectConstituent.Listener;
import grammar.model.Category;
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
        
        Constituent con = new Constituent();
                
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

		@Override
		public void tryDelete(Constituent category) {
			int choice = JOptionPane.showConfirmDialog(display, "Delete this constituent?", "Confirm Delete", 0); 
			if(choice == JOptionPane.YES_OPTION) {
				if (category.getParent() != null) {
		            category.getParent().getChildren().remove(category);
		            category.setParent(null);
		            display.refresh();
		        }
			}
		}
    }
    
    private class ImpFeatureValuesListener implements FeatureValuesListener {
        @Override
        public void featureValueChanged(Constituent constituent, Feature feature, String newValue) {
            constituent.updateFeature(feature, newValue);
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
