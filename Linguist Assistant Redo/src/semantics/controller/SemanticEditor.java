package semantics.controller;

import grammar.controller.SelectConstituent;
import grammar.model.Category;
import grammar.model.Feature;
import grammar.view.FeatureValuesListener;

import javax.swing.JOptionPane;

import ontology.controller.ConceptSelector;
import ontology.model.Concept;
import rule.classic.InputCons;
import rule.generic.RuleEngine;
import rule.model.Rule;
import rule.spellout.SpelloutMaker;
import semantics.model.Constituent;
import semantics.view.BlockListener;
import semantics.view.SemanticEditorPanel;

import commons.main.MainFrame;

public class SemanticEditor {
    private static SemanticEditorPanel panel;
    
    private Rule rule;
    
    public static void run(MainFrame frame) {
        if (panel == null) {
            new SemanticEditor(frame);
        }
        frame.setPanel(panel);
    }
    
    private void classicRuleTest()
    {
    	//TODO: declare or get a new classic rule; clone current constituent to input constituent (?), getOutput of the input constituent; create a rule; evaluate rule
    	InputCons input=new InputCons();
    	 // OutputCons output=new ClassicRuleMaker(p).getOutput(inputCons); //not sure how to pass this lol
    	 Rule r=new Rule();
    	 
    }
    
    public SemanticEditor(MainFrame frame) {
        panel = new SemanticEditorPanel(frame);
        panel.addBlockListener(new ImpBlockListener());
        panel.addFeatureValuesListener(new ImpFeatureValuesListener());
        panel.addListener(new UiListener());
        frame.setPanel(panel);
        
        Constituent con = new Constituent();
      
        panel.updateConstituent(con);
        
    }
    
    private class ImpBlockListener implements BlockListener {
        @Override
        public void selectedConstituent(Constituent constituent) {}

        @Override
        public void droppedBlock(Constituent dropped, Constituent destination, int index) {
            destination.moveChild(dropped, index);
            panel.refresh();
        }

        @Override
        public void droppedButton(Constituent dropped, Constituent destination, int index) {
            //SelectConstituent selectConstituent = new SelectConstituent(dropped, destination, index);
            //selectConstituent.addListener(new AddConstituentListener());
            destination.moveChild(dropped, index);
            panel.refresh();
        }

		@Override
		public void rightClick(Constituent category) {
			int choice = JOptionPane.showConfirmDialog(panel, "Delete this constituent?", "Confirm Delete", 0); 
			if(choice == JOptionPane.YES_OPTION) {
				if (category.getParent() != null) {
		            category.getParent().getChildren().remove(category);
		            category.setParent(null);
		            panel.refresh();
		        }
			}
		}
    }
    
    private class ImpFeatureValuesListener implements FeatureValuesListener {
        @Override
        public void featureValueChanged(Constituent constituent, Feature feature, String newValue) {
            constituent.updateFeature(feature, newValue);
            System.out.println("here");
            panel.refresh();
        }
    }
    
    private class AddConstituentListener implements SelectConstituent.Listener {
        @Override
        public void done() {
            panel.refresh();
        }
    }
    
    private class UiListener implements SemanticEditorPanel.Listener {
        @Override
        public void generate(Constituent constituent) {
            
            RuleEngine engine = new RuleEngine(constituent);
            engine.apply();
            panel.clearTranslation();
            displayTranslation(constituent);
            panel.refresh();
        }
        
        public void displayTranslation(Constituent c)
        {
        	if(c.hasChildren())
        	{
        	 for(Constituent child: c.getChildren())
             {
             	displayTranslation(child);
             	
             
             }
        	}
        	else
        	{
        		System.out.println("Target is: "+c.getTarget().toString());
             	panel.appendTranslation(c.getTarget().toString());
        	}
        }
        
        @Override
        public void setConcept(Constituent constituent) {
            Concept concept = ConceptSelector.select();
            if (concept != null) {
                constituent.setConcept(concept);
                panel.refresh();
            }
        }
        @Override
        public void getRule() {
            rule = SpelloutMaker.create(Category.getByName("Noun"));
            if (rule != null) {
                //rules.add(rule);
            }
        }

    }

}
