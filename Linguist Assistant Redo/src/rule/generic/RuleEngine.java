package rule.generic;

import grammar.model.Category;
import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import rule.model.Rule;
import rule.model.input.And;
import rule.model.input.HasCategory;
import rule.model.input.HasChild;
import rule.model.input.HasFeature;
import rule.model.output.ReorderChildren;
import semantics.model.Constituent;

public class RuleEngine {
    private List<Rule> rules = new ArrayList<>();
    private Constituent constituent;
    
    
    public RuleEngine(Constituent constituent) {
        this.constituent = constituent;
        rule7();
    }
    
    public void apply() {
        for (Rule rule : rules) {
            constituent.evaluate(rule);
        }
        constituent.applyRules();
    }
    
    
    
    private void rule7() {
        
        Category clause = Category.getByName("Clause");
        Category np = Category.getByName("Noun Phrase");
        Category vp = Category.getByName("Verb Phrase");
        
        HasCategory hasClause = new HasCategory(clause);
        HasCategory hasNP = new HasCategory(np);
        HasCategory hasVP = new HasCategory(vp);
        
        //////// VP
        
        And vpConds = new And();
        vpConds.addRule(hasVP);
        HasChild vpChild = new HasChild("vp", vpConds);
        vpChild.setOptional(true);
        
        
        //////// NP
        //// type: Focus
        Feature typeFocus = Feature.getEmpty(np);
        typeFocus.setName("type");
        typeFocus.setValue("focus");
        HasFeature hasTypeFocus = new HasFeature(typeFocus);
        
        And typeFocusConds = new And();
        typeFocusConds.addRule(hasNP);
        typeFocusConds.addRule(hasTypeFocus);
        HasChild typeFocusChild = new HasChild("type focus", typeFocusConds);
        typeFocusChild.setOptional(true);
        
        //// type: undefined
        Feature typeUndefined = Feature.getEmpty(np);
        typeUndefined.setName("type");
        typeUndefined.setValue("undefined");
        HasFeature hasTypeUndefined = new HasFeature(typeUndefined);
        
        
        // complement: actor
        Feature complementActor = Feature.getEmpty(np);
        complementActor.setName("complement type");
        complementActor.setValue("actor");
        HasFeature hasComplementActor = new HasFeature(complementActor);
        
        And complementActorConds = new And();
        complementActorConds.addRule(hasNP);
        complementActorConds.addRule(hasTypeUndefined);
        complementActorConds.addRule(hasComplementActor);
                
        HasChild complementActorChild = new HasChild("complement actor", complementActorConds);
        complementActorChild.setOptional(true);
        
        
        // complement: object
        Feature complementObject = Feature.getEmpty(np);
        complementObject.setName("complement type");
        complementObject.setValue("object");
        HasFeature hasComplementObject = new HasFeature(complementObject);
        
        And complementObjectConds = new And();
        complementObjectConds.addRule(hasNP);
        complementObjectConds.addRule(hasTypeUndefined);
        complementObjectConds.addRule(hasComplementObject);
                
        HasChild complementObjectChild = new HasChild("complement object", complementObjectConds);
        complementObjectChild.setOptional(true);
        
        
        // complement: directional
        Feature complementDirectional = Feature.getEmpty(np);
        complementDirectional.setName("complement type");
        complementDirectional.setValue("object");
        HasFeature hasComplementDirectional = new HasFeature(complementDirectional);
        
        And complementDirectionalConds = new And();
        complementDirectionalConds.addRule(hasNP);
        complementDirectionalConds.addRule(hasTypeUndefined);
        complementDirectionalConds.addRule(hasComplementDirectional);
                
        HasChild complementDirectionalChild = new HasChild("complement directional", complementDirectionalConds);
        complementDirectionalChild.setOptional(true);
        
        
        //////// Clause
        And input = new And();
        input.addRule(hasClause);
        input.addRule(typeFocusChild);
        input.addRule(complementActorChild);
        input.addRule(complementObjectChild);
        input.addRule(complementDirectionalChild);        
        input.addRule(vpChild);
        
        
        ReorderChildren reorder = new ReorderChildren();
        reorder.addChild("vp");
        reorder.addChild("complement actor");
        reorder.addChild("complement object");
        reorder.addChild("complement directional");
        reorder.addChild("type focus");
        
        Rule rule7 = new Rule();
        rule7.setInput(input);
        rule7.addOutput(reorder);
        
        rules.add(rule7);
    }
    
}
