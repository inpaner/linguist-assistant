package rule.generic;

import grammar.model.Category;
import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import rule.model.Rule;
import rule.model.RuleSet;
import rule.model.input.And;
import rule.model.input.HasCategory;
import rule.model.input.HasChild;
import rule.model.input.HasFeature;
import rule.model.input.Or;
import rule.model.output.AddConstituent;
import rule.model.output.ForceTranslation;
import rule.model.output.Output;
import rule.model.output.ReorderChildren;
import rule.model.output.SetFormTranslation;
import semantics.model.Constituent;

public class RuleEngine {
    private List<Rule> rules = new ArrayList<>();
    private Constituent constituent;
    
    
    public RuleEngine(Constituent constituent) {
        this.constituent = constituent;
        rule5();
        //rule7();
        //rule8();
    }
    
    public void apply() {
        for (Rule rule : rules) {
            constituent.evaluate(rule);
        }
        constituent.applyRules();
    }
    
    private void rule1() {
        Category np = Category.getByName("Noun Phrase");
        HasCategory hasNP = new HasCategory(np);
        
        Feature typeFocus = Feature.getEmpty(np);
        typeFocus.setName("semantic role");
        typeFocus.setValue("focus");
        HasFeature hasTypeFocus = new HasFeature(typeFocus);
        
        
    }
    
    
    private void rule5() {
        
        Category np = Category.getByName("Noun Phrase");
        Category noun = Category.getByName("Noun");
        Category marker = Category.getByName("Marker");
        
        ////////Rule 1
        // input
       
        HasCategory hasNP = new HasCategory(np);
        
        Feature typeFocus = Feature.getEmpty(np);
        typeFocus.setName("type");
        typeFocus.setValue("focus");
        HasFeature hasTypeFocus = new HasFeature(typeFocus);
        
        //HasCategory hasNoun = new HasCategory(noun);
        //HasChild nounChild = new HasChild("noun", hasNoun);
        
        And rule1Input = new And();
        rule1Input.addRule(hasNP);
        rule1Input.addRule(hasTypeFocus);
        //rule1Input.addRule(nounChild);
        
        // output
        AddConstituent addAng = new AddConstituent(marker, "root");
        Output setAng = new ForceTranslation("ang");
        addAng.addOutput(setAng);
        
        Rule angRule = new Rule();
        angRule.setInput(rule1Input);
        angRule.addOutput(addAng);
        
        ////////Rule 2 (complement type = actor | object) -> marker = ng
        // input
        Feature typeUndefined = Feature.getEmpty(np);
        typeUndefined.setName("type");
        typeUndefined.setValue("undefined");
        HasFeature hasTypeUndefined = new HasFeature(typeUndefined);
          
        Feature complementActor = Feature.getEmpty(np);
        complementActor.setName("complement type");
        complementActor.setValue("actor");
        HasFeature hasComplementActor = new HasFeature(complementActor);
        
        Feature complementObject = Feature.getEmpty(np);
        complementObject.setName("complement type");
        complementObject.setValue("object");
        HasFeature hasComplementObject = new HasFeature(complementObject);
        
        Or actorOrObject = new Or();
        actorOrObject.addRule(hasComplementActor);
        actorOrObject.addRule(hasComplementObject);
        
        
        And rule2Input = new And();
        rule2Input.addRule(hasNP);
        rule2Input.addRule(actorOrObject);
        rule2Input.addRule(hasTypeUndefined);
        //rule2Input.addRule(nounChild);
        
        // output
        AddConstituent addNg = new AddConstituent(marker, "root");
        Output setNg = new ForceTranslation("ng");
        addNg.addOutput(setNg);
        
        Rule ngRule = new Rule();
        ngRule.setInput(rule2Input);
        ngRule.addOutput(addNg);
       
       
        ////////Rule 3 (complement type = directional) -> marker = sa
        // input
        Feature complementDirectional = Feature.getEmpty(np);
        complementDirectional.setName("complement type");
        complementDirectional.setValue("directional");
        HasFeature hasComplementDirectional = new HasFeature(complementDirectional);
        
        And rule3Input = new And();
        rule3Input.addRule(hasNP);
        rule3Input.addRule(hasComplementDirectional);
        rule3Input.addRule(hasTypeUndefined);
        //rule3Input.addRule(nounChild);
        
        // output
        AddConstituent addSa = new AddConstituent(marker, "root");
        Output setSa = new ForceTranslation("sa");
        addNg.addOutput(setSa);
        
        
        Rule saRule = new Rule();
        saRule.setInput(rule3Input);
        saRule.addOutput(addSa);
        
        RuleSet set = new RuleSet();
        set.addRule(angRule);
        set.addRule(ngRule);
        set.addRule(saRule);
        rules.add(set);
    }
    
    
    private void rule6() {
        Category verb = Category.getByName("Verb");
        HasCategory hasVerb = new HasCategory(verb);
        
        //////// List of Feature Rules
        Feature af = Feature.getEmpty(verb);
        af.setName("focus");
        af.setValue("af");
        HasFeature hasAF = new HasFeature(af);
        
        Feature of = Feature.getEmpty(verb);
        of.setName("focus");
        of.setValue("of");
        HasFeature hasOF = new HasFeature(of);
        
        Feature df = Feature.getEmpty(verb);
        of.setName("focus");
        of.setValue("df");
        HasFeature hasDF = new HasFeature(df);
              
        Feature imperfective = Feature.getEmpty(verb);
        imperfective.setName("surface aspect");
        imperfective.setValue("imperfective");
        HasFeature hasImperfective = new HasFeature(imperfective);
        
        Feature perfective = Feature.getEmpty(verb);
        perfective.setName("surface aspect");
        perfective.setValue("perfective");
        HasFeature hasPerfective = new HasFeature(perfective);
        
        
        //////// Actual Rules
        // AF Imperfective
        And afImperfectiveInput = new And(); 
        afImperfectiveInput.addRule(hasVerb);

        afImperfectiveInput.addRule(hasAF); // always  changes
        afImperfectiveInput.addRule(hasImperfective); // always changes
        
        SetFormTranslation afImperfectiveOutput = new SetFormTranslation("af imperfective"); // always changes
        
        Rule afImperfective = new Rule();
        afImperfective.setInput(afImperfectiveInput); 
        afImperfective.addOutput(afImperfectiveOutput);
        
        
        // AF Perfective
        And afPerfectiveInput = new And();
        afImperfectiveInput.addRule(hasVerb);
        afPerfectiveInput.addRule(hasAF);
        afPerfectiveInput.addRule(hasImperfective);
        
        SetFormTranslation afPerfectiveOutput = new SetFormTranslation("af perfective");
        
        Rule afPerfective = new Rule();
        afPerfective.setInput(afPerfectiveInput);
        afPerfective.addOutput(afPerfectiveOutput);
        
        
        //////// RuleSet
        RuleSet rule = new RuleSet();
        rule.addRule(afImperfective);
        rule.addRule(afPerfective);
        
        
        
        rules.add(rule);
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
        
        Rule rule = new Rule();
        rule.setInput(input);
        rule.addOutput(reorder);
        
        rules.add(rule);
    }
    

    private void rule8() {

        // input
        Category np = Category.getByName("Noun Phrase");
        Category noun = Category.getByName("Noun");
        Category marker = Category.getByName("Marker");
        
        HasCategory hasNP = new HasCategory(np);
        HasCategory hasNoun = new HasCategory(noun);
        HasCategory hasMarker = new HasCategory(marker);
        
        HasChild markerChild = new HasChild("marker", hasMarker);
        HasChild nounChild = new HasChild("noun", hasNoun);
        
        And input = new And();
        input.addRule(hasNP);
        input.addRule(markerChild);
        input.addRule(nounChild);
        
        // output
        ReorderChildren reorder = new ReorderChildren();
        reorder.addChild("marker");
        reorder.addChild("noun");
        
        Rule rule = new Rule();
        rule.setInput(input);
        rule.addOutput(reorder);
        
        rules.add(rule);
    }
    
}
