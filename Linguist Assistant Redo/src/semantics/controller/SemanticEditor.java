package semantics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ontology.controller.ConceptSelector;
import ontology.model.Concept;
import lexicon.model.Language;
import rule.Rule;
import rule.input.And;
import rule.input.HasCategory;
import rule.input.HasChild;
import rule.input.HasConcept;
import rule.output.SetTarget;
import semantics.model.Constituent;
import semantics.view.BlockListener;
import semantics.view.SemanticEditorPanel;
import grammar.controller.SelectConstituent;
import grammar.model.Category;
import grammar.model.Feature;
import grammar.view.FeatureValuesListener;
import commons.main.MainFrame;

public class SemanticEditor {
    private SemanticEditorPanel display;
    private List<Rule> rules = new ArrayList<>();
    
    public static void main(String[] args) {
        new SemanticEditor();
    }
    
    private void ruleTest() {
        // Rule 1
        Category clause = Category.getByName("Clause");
        Category noun = Category.getByName("Noun");
        HasCategory hasClause = new HasCategory(clause);
        HasCategory hasNoun = new HasCategory(noun);
        HasConcept hasAlex = new HasConcept(Concept.getInstance("Aaron", "A", noun));
        And nounChildConditions = new And();
        nounChildConditions.addRule(hasNoun);
        nounChildConditions.addRule(hasAlex);
        
        HasChild hasNounChild = new HasChild("NounChild", nounChildConditions);
        
        And input1 = new And();
        input1.addRule(hasClause);
        input1.addRule(hasNounChild);
        
        Language english = Language.getInstance("English");
        SetTarget englishTarget = new SetTarget("NounChild", english);
        Rule rule1 = new Rule();
        rule1.setInput(input1);
        rule1.addOutput(englishTarget);
        
        // Rule 2
        Category verb = Category.getByName("Verb");
        HasCategory hasVerb = new HasCategory(verb);
        HasConcept hasRun = new HasConcept(Concept.getInstance("run", "A", verb));
        And verbChildConditions = new And();
        verbChildConditions.addRule(hasVerb);
        verbChildConditions.addRule(hasRun);
        
        HasChild hasVerbChild = new HasChild("VerbChild", verbChildConditions);
        
        
        And input2 = new And();
        input2.addRule(hasClause);
        input2.addRule(hasVerbChild);
        
        Language filipino = Language.getInstance("Filipino");
        SetTarget filipinoTarget = new SetTarget("VerbChild", filipino);
        
        Rule rule2 = new Rule();
        rule2.setInput(input2);
        rule2.addOutput(filipinoTarget);
        
        rules.add(rule1);
        rules.add(rule2);
    }
    
    public SemanticEditor() {
        MainFrame frame = new MainFrame();
        display = new SemanticEditorPanel();
        display.addBlockListener(new ImpBlockListener());
        display.addFeatureValuesListener(new ImpFeatureValuesListener());
        display.addListener(new UiListener());
        frame.setPanel(display);
        
        Constituent con = new Constituent();
        display.updateConstituent(con);
        
        ruleTest();
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
    
    private class UiListener implements SemanticEditorPanel.Listener {
        @Override
        public void generate(Constituent constituent) {
            for (Rule rule : rules) {
                System.out.println("Evaluating rules");
                constituent.evaluate(rule);
                
                System.out.println("Applying rules");
                constituent.applyRules();
                
                display.refresh();
            }
        }
        
        @Override
        public void setConcept(Constituent constituent) {
            Concept concept = ConceptSelector.select();
            if (concept != null) {
                constituent.setConcept(concept);
                display.refresh();
            }
        }
    }

}
