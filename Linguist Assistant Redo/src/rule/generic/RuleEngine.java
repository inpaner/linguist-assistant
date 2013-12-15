package rule.generic;

import grammar.model.Category;
import grammar.model.Feature;

import java.util.ArrayList;
import java.util.List;

import lexicon.model.Language;
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
import rule.model.output.SetFeature;
import rule.model.output.SetFormTranslation;
import rule.model.output.SetTranslation;
import semantics.model.Constituent;

public class RuleEngine {
    
    private List<Rule> translationRules = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();
    private Constituent root;
    
    
    public RuleEngine(Constituent constituent) {
        this.root = constituent;
        translationRules();
        rule1(); // set np complement
        rule2(); // determine np focus
        rule3(); // set verb surface
        rule4(); // set verb focus
        rule5(); // add marker
        rule6(); // select form
        rule7(); // reorder vp np np
        rule8(); // reorder marker
    }
    
    public void apply() {
        for (Rule rule : translationRules) {
            root.evaluate(rule);
        }
        root.applyRules();
        
        boolean ruleApplied;
        do {
            System.out.println();
            System.out.println();
            System.out.println("Iterating***************************************************");
            ruleApplied = false;
            for (Rule rule : rules) {
                System.out.println();
                System.out.println("Rule: " + rule.toString());
                if (root.evaluate(rule)) {
                    ruleApplied = true;
                }
                root.applyRules();
            }
            
        } while (ruleApplied); 
        
        
    }
    
    private void translationRules() {
        // Noun
        Category noun = Category.getByName("Noun");
        HasCategory hasNoun = new HasCategory(noun);
        
        Language filipino = Language.getInstance("Filipino");
        
        SetTranslation translateNoun = new SetTranslation("root", filipino);
        
        Rule filNoun = new Rule();
        filNoun.setName("noun translate");
        filNoun.setInput(hasNoun);
        filNoun.addOutput(translateNoun);
        
        translationRules.add(filNoun);
        
        // Verb
        Category verb = Category.getByName("Verb");
        HasCategory hasVerb = new HasCategory(verb);
        
        SetTranslation translateVerb = new SetTranslation("root", filipino);
        
        Rule filVerb = new Rule();
        filNoun.setName("verb translate");
        filVerb.setInput(hasVerb);
        filVerb.addOutput(translateVerb);
        
        translationRules.add(filVerb);
        
    }
    
    private void rule1() {
        Category np = Category.getByName("Noun Phrase");
        HasCategory hasNP = new HasCategory(np);
        
        
        // Rule 1
        Feature mostAgent = Feature.get(np, "semantic role", "agent");
        HasFeature hasMostAgent = new HasFeature(mostAgent);
        
        And rule1Input = new And();
        rule1Input.addRule(hasNP);
        rule1Input.addRule(hasMostAgent);
        
        SetFeature rule1Output = new SetFeature(np, "complement type", "actor");
        
        Rule rule1 = new Rule();
        rule1.setInput(rule1Input);
        rule1.addOutput(rule1Output);
        
        
        // Rule 2
        Feature mostPatient = Feature.get(np, "semantic role", "patient");
        HasFeature hasMostPatient = new HasFeature(mostPatient);
        
        And rule2Input = new And();
        rule2Input.addRule(hasNP);
        rule2Input.addRule(hasMostPatient);
        
        SetFeature rule2Output = new SetFeature(np, "complement type", "object");
        
        Rule rule2 = new Rule();
        rule2.setInput(rule2Input);
        rule2.addOutput(rule2Output);
        
        
        // Rule 3
        Feature destination = Feature.get(np, "semantic role", "destination");
        HasFeature hasDestination = new HasFeature(destination);
        
        Feature beneficiary = Feature.get(np, "semantic role", "beneficiary");
        HasFeature hasBeneficiary = new HasFeature(beneficiary);
        
        Or destinationOrBeneficiary = new Or();
        destinationOrBeneficiary.addRule(hasDestination);
        destinationOrBeneficiary.addRule(hasBeneficiary);
        
        And rule3Input = new And();
        rule3Input.addRule(hasNP);
        rule3Input.addRule(destinationOrBeneficiary);
        
        SetFeature rule3Output = new SetFeature(np, "complement type", "directional");
        
        Rule rule3 = new Rule();
        rule3.setInput(rule3Input);
        rule3.addOutput(rule3Output);
        
        RuleSet rule = new RuleSet();
        rule.setName("rule 1");
        
        rule.addRule(rule1);
        rule.addRule(rule2);
        rule.addRule(rule3);
        
        rules.add(rule);
    }
    
    
    private void rule2() {
        
        Category clause = Category.getByName("Clause");
        Category np = Category.getByName("Noun Phrase");
        Category noun = Category.getByName("Noun");
        
        HasCategory hasClause = new HasCategory(clause);
        HasCategory hasNP = new HasCategory(np);
        HasCategory hasNoun = new HasCategory(noun);
        
        SetFeature typeFocus = new SetFeature(np, "type", "focus");
        typeFocus.setKey("target");
        
        
        //// Rule 1
        // Noun: participant tracking = routine
        Feature trackingRoutine = Feature.get(noun, "participant tracking", "routine");
        HasFeature hasTrackingRoutine = new HasFeature(trackingRoutine);
        
        Feature trackingInferable = Feature.get(noun, "participant tracking", "frame inferable");
        HasFeature hasTrackingInferable = new HasFeature(trackingInferable);
        
        Or routineOrInferable = new Or();
        routineOrInferable.addRule(hasTrackingRoutine);
        routineOrInferable.addRule(hasTrackingInferable);
        
        And nounConds = new And();
        nounConds.addRule(hasNoun);
        nounConds.addRule(routineOrInferable);
        HasChild routineNounChild = new HasChild("noun", nounConds);
        
        // NP: complement type = object (target)
        Feature complementObject = Feature.get(np, "complement type", "object");
        HasFeature hasComplementObject = new HasFeature(complementObject);
        
        And npCOConds = new And();
        npCOConds.addRule(hasNP);
        npCOConds.addRule(hasComplementObject);
        npCOConds.addRule(routineNounChild);
        
        HasChild npCOChild = new HasChild("target", npCOConds);
        
        And rule1Input = new And();
        rule1Input.addRule(hasClause);
        rule1Input.addRule(npCOChild);
        
        Rule rule1 = new Rule();
        rule1.setInput(rule1Input);
        rule1.addOutput(typeFocus);
        
        
        //// Rule 2
        // NP: complement type = object, type = undefined
        Feature typeUndefined = Feature.get(np, "type", "undefined");
        HasFeature hasTypeUndefined = new HasFeature(typeUndefined);
        
        And npCOTUConds = new And();
        npCOTUConds.addRule(hasNP);
        npCOTUConds.addRule(hasComplementObject);
        npCOTUConds.addRule(hasTypeUndefined);
        HasChild npCOTUChild = new HasChild("co tu", npCOTUConds);
        npCOTUChild.setOptional(true);
        
        // NP: complement type = directional (target)
        Feature complementDirectional = Feature.get(np, "complement type", "directional");
        HasFeature hasComplementDirectional = new HasFeature(complementDirectional);
        
        And npCDConds = new And();
        npCDConds.addRule(hasNP);
        npCDConds.addRule(hasComplementDirectional);
        npCDConds.addRule(routineNounChild);
        HasChild npCDChild = new HasChild("target", npCDConds);
        
        And rule2Input = new And();
        rule2Input.addRule(hasClause);
        rule2Input.addRule(npCOTUChild);
        rule2Input.addRule(npCDChild);
        
        Rule rule2 = new Rule();
        rule2.setInput(rule2Input);
        rule2.addOutput(typeFocus);
        
        
        //// Rule 3
        // NP: complement type = directional, type = undefined
        And npCDTUConds = new And();
        npCDTUConds.addRule(hasNP);
        npCDTUConds.addRule(hasComplementDirectional);
        npCDTUConds.addRule(hasTypeUndefined);
        HasChild npCDTUChild = new HasChild("target", npCDTUConds);
        npCDTUChild.setOptional(true);
        
        // NP: complement type = actor (target)
        Feature complementActor = Feature.get(np, "complement type", "actor");
        HasFeature hasComplementActor = new HasFeature(complementActor);
        
        And npCAConds = new And();
        npCAConds.addRule(hasNP);
        npCAConds.addRule(hasComplementActor);
        HasChild npCAChild = new HasChild("target", npCAConds);
        
        And rule3Input = new And();
        rule3Input.addRule(hasClause);
        rule3Input.addRule(npCOTUChild);
        rule3Input.addRule(npCDTUChild);
        rule3Input.addRule(npCAChild);

        Rule rule3 = new Rule();
        rule3.setInput(rule3Input);
        rule3.addOutput(typeFocus);
        
        
        //// Rule Set
        RuleSet rule = new RuleSet();
        rule.setName("rule 2");
        rule.addRule(rule1);
        rule.addRule(rule2);
        rule.addRule(rule3);
        
        rules.add(rule);
    }
    
    
    private void rule3() {
        Category verb = Category.getByName("Verb");
        HasCategory hasVerb = new HasCategory(verb);
        
        //// rule 1: aspect = imperfective -> surface = imperfective
        Feature aspectImperfect = Feature.get(verb, "aspect", "imperfect");
        HasFeature hasAspectImperfect = new HasFeature(aspectImperfect);
        
        And rule1Input = new And();
        rule1Input.addRule(hasVerb);
        rule1Input.addRule(hasAspectImperfect);
        
        SetFeature surfaceImperfect = new SetFeature(verb, "surface aspect", "imperfective");
        
        Rule rule1 = new Rule();
        rule1.setInput(rule1Input);
        rule1.addOutput(surfaceImperfect);
        
        
        //// rule 2: time = future -> surface = contemplative
        // Future features
        Feature immediateFuture = Feature.get(verb, "time", "immediate");
        HasFeature hasImmediateFuture = new HasFeature(immediateFuture);
        
        Feature laterToday = Feature.get(verb, "time", "later today");
        HasFeature hasLaterToday = new HasFeature(laterToday);
        
        Feature tomorrow = Feature.get(verb, "time", "tomorrow");
        HasFeature hasTomorrow = new HasFeature(tomorrow);
        
        Feature twoDaysFrom = Feature.get(verb, "time", "2 days from now");
        HasFeature hasTwoDaysFrom = new HasFeature(twoDaysFrom);
        
        Feature threeDaysFrom = Feature.get(verb, "time", "3 days from now");
        HasFeature hasThreeDaysFrom = new HasFeature(threeDaysFrom);
        
        Feature weekFromNow = Feature.get(verb, "time", "week from now");
        HasFeature hasWeekFromNow = new HasFeature(weekFromNow);
        
        Feature monthFromNow = Feature.get(verb, "time", "month from now");
        HasFeature hasMonthFromNow = new HasFeature(monthFromNow);
        
        Feature yearFromNow = Feature.get(verb, "time", "year from now");
        HasFeature hasYearFromNow = new HasFeature(yearFromNow);
        
        Feature unknownFuture = Feature.get(verb, "time", "unknown future");
        HasFeature hasUnknownFuture = new HasFeature(unknownFuture);
        
        Feature timeless = Feature.get(verb, "time", "timeless");
        HasFeature hasTimeless = new HasFeature(timeless);
        
        
        Or hasFuture = new Or();
        hasFuture.addRule(hasImmediateFuture);
        hasFuture.addRule(hasLaterToday);
        hasFuture.addRule(hasTomorrow);
        hasFuture.addRule(hasTwoDaysFrom);
        hasFuture.addRule(hasThreeDaysFrom);
        hasFuture.addRule(hasWeekFromNow);
        hasFuture.addRule(hasMonthFromNow);
        hasFuture.addRule(hasYearFromNow);
        hasFuture.addRule(hasUnknownFuture);
        hasFuture.addRule(hasTimeless);

        And rule2Input = new And();
        rule2Input.addRule(hasVerb);
        rule2Input.addRule(hasFuture);
        
        SetFeature surfaceContemplative = new SetFeature(verb, "surface aspect", "contemplative");
        
        Rule rule2 = new Rule();
        rule2.setInput(rule2Input);
        rule2.addOutput(surfaceContemplative);
        
        
        //// rule 3: time = past -> surface = perfective
        // past features
        Feature immediatePast = Feature.get(verb, "time", "immediate past");
        HasFeature hasImmediatePast = new HasFeature(immediatePast);
        
        Feature earlierToday = Feature.get(verb, "time", "earlier today");
        HasFeature hasEarlierToday = new HasFeature(earlierToday);
        
        Feature yesterday = Feature.get(verb, "time", "yesterday");
        HasFeature hasYesterday = new HasFeature(yesterday);
        
        Feature twoAgo = Feature.get(verb, "time", "2 days ago");
        HasFeature hasTwoAgo = new HasFeature(twoAgo);
        
        Feature threeAgo = Feature.get(verb, "time", "3 days ago");
        HasFeature hasThreeAgo = new HasFeature(threeAgo);
        
        Feature weekAgo = Feature.get(verb, "time", "week ago");
        HasFeature hasWeekAgo = new HasFeature(weekAgo);
        
        Feature monthAgo = Feature.get(verb, "time", "month ago");
        HasFeature hasMonthAgo = new HasFeature(monthAgo);
        
        Feature yearAgo = Feature.get(verb, "time", "year ago");
        HasFeature hasYearAgo = new HasFeature(yearAgo);
        
        Feature speakerLifetime = Feature.get(verb, "time", "speaker lifetime");
        HasFeature hasSpeakerLifetime = new HasFeature(speakerLifetime);
        
        Feature historicPast = Feature.get(verb, "time", "historic past");
        HasFeature hasHistoricPast = new HasFeature(historicPast);
        
        Feature eternityPast = Feature.get(verb, "time", "eternity past");
        HasFeature hasEternityPast = new HasFeature(eternityPast);
        
        Feature unknownPast = Feature.get(verb, "time", "unknown past");
        HasFeature hasUnknownPast = new HasFeature(unknownPast);
        
        Or hasPast = new Or();
        hasPast.addRule(hasImmediatePast);
        hasPast.addRule(hasEarlierToday);
        hasPast.addRule(hasYesterday);
        hasPast.addRule(hasTwoAgo);
        hasPast.addRule(hasThreeAgo);
        hasPast.addRule(hasWeekAgo);
        hasPast.addRule(hasMonthAgo);
        hasPast.addRule(hasYearAgo);
        hasPast.addRule(hasSpeakerLifetime);
        hasPast.addRule(hasHistoricPast);
        hasPast.addRule(hasEternityPast);
        hasPast.addRule(hasUnknownPast);
        
        And rule3Input = new And();
        rule3Input.addRule(hasVerb);
        rule3Input.addRule(hasPast);
        
        SetFeature surfacePerfective = new SetFeature(verb, "surface aspect", "perfective");
        
        Rule rule3 = new Rule();
        rule3.setInput(rule3Input);
        rule3.addOutput(surfacePerfective);
        
        
        // rule 3: time = present -> surface = imperfective
        Feature present = Feature.get(verb, "time", "present");
        HasFeature hasTimePresent = new HasFeature(present);
        
        Feature discourse = Feature.get(verb, "time", "discourse");
        HasFeature hasDiscourse = new HasFeature(discourse);
        
        Or hasPresent = new Or();
        hasPresent.addRule(hasTimePresent);
        hasPresent.addRule(hasDiscourse);
        
        And rule4Input = new And();
        rule4Input.addRule(hasVerb);
        rule4Input.addRule(hasPresent);
        
        SetFeature surfaceImperfective = new SetFeature(verb, "surface aspect", "imperfective");
        
        Rule rule4 = new Rule();
        rule4.setInput(rule4Input);
        rule4.addOutput(surfaceImperfective);
        
        RuleSet rule = new RuleSet();
        rule.setName("rule 3");
        
        rule.addRule(rule1);
        rule.addRule(rule2);
        rule.addRule(rule3);
        rule.addRule(rule4);
        
        rules.add(rule);
    }
    
    
    private void rule4() {
        Category clause = Category.getByName("Clause");
        Category np = Category.getByName("Noun Phrase");
        Category vp = Category.getByName("Verb Phrase");
        Category verb = Category.getByName("Verb");
        
        HasCategory hasClause = new HasCategory(clause);
        HasCategory hasNP = new HasCategory(np);
        HasCategory hasVP = new HasCategory(vp);
        HasCategory hasVerb = new HasCategory(verb);
        
        
        //// Rule 1
        
        // Noun Phrase: type = focus, complement type = actor
        Feature typeFocus = Feature.get(np, "type", "focus");
        HasFeature hasTypeFocus = new HasFeature(typeFocus);
        
        Feature complementActor = Feature.get(np, "complement type", "actor");
        HasFeature hascomplementActor= new HasFeature(complementActor);
        
        And tfcaConds = new And();
        tfcaConds.addRule(hasNP); 
        tfcaConds.addRule(hasTypeFocus); 
        tfcaConds.addRule(hascomplementActor);
        HasChild tfcaChild = new HasChild("tf cp", tfcaConds);
        
        
        // Verb (target)
        And verbChildConds = new And();
        verbChildConds.addRule(hasVerb);
        HasChild hasVerbChild = new HasChild("target", verbChildConds);
        
        
        // Verb Phrase
        And vpChildConds = new And();
        vpChildConds.addRule(hasVP);
        vpChildConds.addRule(hasVerbChild);
        
        HasChild hasVPChild = new HasChild("vp", vpChildConds);

        And rule1Input = new And();
        rule1Input.addRule(hasClause);
        rule1Input.addRule(tfcaChild);
        rule1Input.addRule(hasVPChild);
        
        SetFeature focusAF = new SetFeature(verb, "focus", "af");
        focusAF.setKey("target");
        
        Rule rule1 = new Rule();
        rule1.setInput(rule1Input);
        rule1.addOutput(focusAF);
        
        
        //// Rule 2 
        
        // Noun Phrase: type = focus, complement type = object
        Feature complementObject = Feature.get(np, "complement type", "object");
        HasFeature hasComplementObject= new HasFeature(complementObject);
        
        And tfcoConds = new And();
        tfcoConds.addRule(hasNP); 
        tfcoConds.addRule(hasTypeFocus); 
        tfcoConds.addRule(hasComplementObject);
        HasChild tfcoChild = new HasChild("tf co", tfcoConds);
        
        And rule2Input = new And();
        rule2Input.addRule(hasClause);
        rule2Input.addRule(tfcoChild);
        rule2Input.addRule(hasVPChild);
        
        SetFeature focusOF = new SetFeature(verb, "focus", "of");
        focusOF.setKey("target");
        
        Rule rule2 = new Rule();
        rule2.setInput(rule2Input);
        rule2.addOutput(focusOF);


        //// Rule 3 
        
        // Noun Phrase: type = focus, complement type = directional
        Feature complementDirectional = Feature.get(np, "complement type", "directional");
        HasFeature hasComplementDirectional= new HasFeature(complementDirectional);
        
        And tfcdConds = new And();
        tfcdConds.addRule(hasNP); 
        tfcdConds.addRule(hasTypeFocus); 
        tfcdConds.addRule(hasComplementDirectional);
        HasChild tfcdChild = new HasChild("tf cd", tfcdConds);
        
        And rule3Input = new And();
        rule3Input.addRule(hasClause);
        rule3Input.addRule(tfcdChild);
        rule3Input.addRule(hasVPChild);
        
        SetFeature focusDF = new SetFeature(verb, "focus", "df");
        focusDF.setKey("target");
        
        Rule rule3 = new Rule();
        rule3.setInput(rule3Input);
        rule3.addOutput(focusDF);
        
        RuleSet rule = new RuleSet();
        rule.setName("rule 4");
        
        rule.addRule(rule1);
        rule.addRule(rule2);
        rule.addRule(rule3);
        
        rules.add(rule);
    }
    
    
    private void rule5() {
        
        Category np = Category.getByName("Noun Phrase");
        Category marker = Category.getByName("Marker");
        
        ////////Rule 1
        // input
       
        HasCategory hasNP = new HasCategory(np);
        
        Feature typeFocus = Feature.get(np, "type", "focus");
        HasFeature hasTypeFocus = new HasFeature(typeFocus);
        
        And rule1Input = new And();
        rule1Input.addRule(hasNP);
        rule1Input.addRule(hasTypeFocus);
        
        // output
        AddConstituent addAng = new AddConstituent(marker, "root");
        Output setAng = new ForceTranslation("ang");
        addAng.addOutput(setAng);
        
        Rule angRule = new Rule();  
        angRule.setInput(rule1Input);
        angRule.addOutput(addAng);
        
        ////////Rule 2 (complement type = actor | object) -> marker = ng
        // input
        Feature typeUndefined = Feature.get(np, "type", "undefined");
        HasFeature hasTypeUndefined = new HasFeature(typeUndefined);
          
        Feature complementActor = Feature.get(np, "complement type", "actor");
        HasFeature hasComplementActor = new HasFeature(complementActor);
        
        Feature complementObject = Feature.get(np, "complement type", "object");
        HasFeature hasComplementObject = new HasFeature(complementObject);
        
        Or actorOrObject = new Or();
        actorOrObject.addRule(hasComplementActor);
        actorOrObject.addRule(hasComplementObject);
        
        And rule2Input = new And();
        rule2Input.addRule(hasNP);
        rule2Input.addRule(actorOrObject);
        rule2Input.addRule(hasTypeUndefined);
        
        // output
        AddConstituent addNg = new AddConstituent(marker, "root");
        Output setNg = new ForceTranslation("ng");
        addNg.addOutput(setNg);
        
        Rule ngRule = new Rule();
        ngRule.setInput(rule2Input);
        ngRule.addOutput(addNg);
       
       
        ////////Rule 3 (complement type = directional) -> marker = sa
        // input
        Feature complementDirectional = Feature.get(np, "complement type", "directional");
        HasFeature hasComplementDirectional = new HasFeature(complementDirectional);
        
        And rule3Input = new And();
        rule3Input.addRule(hasNP);
        rule3Input.addRule(hasComplementDirectional);
        rule3Input.addRule(hasTypeUndefined);
        
        // output
        AddConstituent addSa = new AddConstituent(marker, "root");
        Output setSa = new ForceTranslation("sa");
        addSa.addOutput(setSa);
        
        Rule saRule = new Rule();
        saRule.setInput(rule3Input);
        saRule.addOutput(addSa);
        
        RuleSet rule = new RuleSet();
        rule.setName("rule 5");
        
        rule.addRule(angRule);
        rule.addRule(ngRule);
        rule.addRule(saRule);
        rules.add(rule);
    }
    
    
    private void rule6() {
        Category verb = Category.getByName("Verb");
        HasCategory hasVerb = new HasCategory(verb);
        
        //////// List of Feature Rules
        Feature af = Feature.get(verb, "focus", "af");
        HasFeature hasAF = new HasFeature(af);
        
        Feature of = Feature.get(verb, "focus", "of");
        HasFeature hasOF = new HasFeature(of);
        
        Feature df = Feature.get(verb, "focus", "df");
        HasFeature hasDF = new HasFeature(df);
              
        Feature imperfective = Feature.get(verb, "surface aspect", "imperfective");
        HasFeature hasImperfective = new HasFeature(imperfective);
        
        Feature perfective = Feature.get(verb, "surface aspect", "perfective");
        HasFeature hasPerfective = new HasFeature(perfective);
        
        Feature contemplative = Feature.get(verb, "surface aspect", "contemplative");
        HasFeature hasContemplative = new HasFeature(contemplative);
        
        
        //////// Actual Rules
        // AF Imperfective
        And afImperfectiveInput = new And(); 
        afImperfectiveInput.addRule(hasVerb);
        afImperfectiveInput.addRule(hasAF); 
        afImperfectiveInput.addRule(hasImperfective); 
        
        SetFormTranslation afImperfectiveOutput = new SetFormTranslation("af imperfective"); 
        
        Rule afImperfective = new Rule();
        afImperfective.setInput(afImperfectiveInput); 
        afImperfective.addOutput(afImperfectiveOutput);
        
        
        // AF Perfective
        And afPerfectiveInput = new And();
        afImperfectiveInput.addRule(hasVerb);
        afPerfectiveInput.addRule(hasAF);
        afPerfectiveInput.addRule(hasPerfective);
        
        SetFormTranslation afPerfectiveOutput = new SetFormTranslation("af perfective");
        
        Rule afPerfective = new Rule();
        afPerfective.setInput(afPerfectiveInput);
        afPerfective.addOutput(afPerfectiveOutput);
        
        
        // AF Contemplative
        And afContemplativeInput = new And();
        afContemplativeInput.addRule(hasVerb);
        afContemplativeInput.addRule(hasAF);
        afContemplativeInput.addRule(hasContemplative);
        
        SetFormTranslation afContemplativeOutput = new SetFormTranslation("af contemplative");
        
        Rule afContemplative = new Rule();
        afContemplative.setInput(afContemplativeInput);
        afContemplative.addOutput(afContemplativeOutput);
        
        
        // OF Imperfective
        And ofImperfectiveInput = new And(); 
        ofImperfectiveInput.addRule(hasVerb);

        ofImperfectiveInput.addRule(hasOF);
        ofImperfectiveInput.addRule(hasImperfective);
        
        SetFormTranslation ofImperfectiveOutput = new SetFormTranslation("of imperfective");
        
        Rule ofImperfective = new Rule();
        ofImperfective.setInput(ofImperfectiveInput); 
        ofImperfective.addOutput(ofImperfectiveOutput);
        
        
        // OF Perfective
        And ofPerfectiveInput = new And();
        ofPerfectiveInput.addRule(hasVerb);
        ofPerfectiveInput.addRule(hasOF);
        ofPerfectiveInput.addRule(hasPerfective);
        
        SetFormTranslation ofPerfectiveOutput = new SetFormTranslation("of perfective");
        
        Rule ofPerfective = new Rule();
        ofPerfective.setInput(ofPerfectiveInput);
        ofPerfective.addOutput(ofPerfectiveOutput);
        
        
        // OF Contemplative
        And ofContemplativeInput = new And();
        ofContemplativeInput.addRule(hasVerb);
        ofContemplativeInput.addRule(hasOF);
        ofContemplativeInput.addRule(hasContemplative);
        
        SetFormTranslation ofContemplativeOutput = new SetFormTranslation("of contemplative");
        
        Rule ofContemplative = new Rule();
        ofContemplative.setInput(ofContemplativeInput);
        ofContemplative.addOutput(ofContemplativeOutput);
        
        
        // DF Imperfective
        And dfImperfectiveInput = new And(); 
        dfImperfectiveInput.addRule(hasVerb);

        dfImperfectiveInput.addRule(hasDF);
        dfImperfectiveInput.addRule(hasImperfective);
        
        SetFormTranslation dfImperfectiveOutput = new SetFormTranslation("df imperfective");
        
        Rule dfImperfective = new Rule();
        dfImperfective.setInput(dfImperfectiveInput); 
        dfImperfective.addOutput(dfImperfectiveOutput);
        
        
        // DF Perfective
        And dfPerfectiveInput = new And();
        dfPerfectiveInput.addRule(hasVerb);
        dfPerfectiveInput.addRule(hasDF);
        dfPerfectiveInput.addRule(hasPerfective);
        
        SetFormTranslation dfPerfectiveOutput = new SetFormTranslation("df perfective");
        
        Rule dfPerfective = new Rule();
        dfPerfective.setInput(dfPerfectiveInput);
        dfPerfective.addOutput(dfPerfectiveOutput);
        
        
        // DF Contemplative
        And dfContemplativeInput = new And();
        dfContemplativeInput.addRule(hasVerb);
        dfContemplativeInput.addRule(hasDF);
        dfContemplativeInput.addRule(hasContemplative);
        
        SetFormTranslation dfContemplativeOutput = new SetFormTranslation("df contemplative");
        
        Rule dfContemplative = new Rule();
        dfContemplative.setInput(dfContemplativeInput);
        dfContemplative.addOutput(dfContemplativeOutput);
        
        
        //////// RuleSet
        RuleSet rule = new RuleSet();
        rule.setName("rule 6");
        
        rule.addRule(afImperfective);
        rule.addRule(afPerfective);
        rule.addRule(afContemplative);
        rule.addRule(ofImperfective);
        rule.addRule(ofPerfective);
        rule.addRule(ofContemplative);
        rule.addRule(dfImperfective);
        rule.addRule(dfPerfective);
        rule.addRule(dfContemplative);
        
        
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
        Feature typeFocus = Feature.get(np, "type", "focus");
        HasFeature hasTypeFocus = new HasFeature(typeFocus);
        
        And typeFocusConds = new And();
        typeFocusConds.addRule(hasNP);
        typeFocusConds.addRule(hasTypeFocus);
        HasChild typeFocusChild = new HasChild("type focus", typeFocusConds);
        typeFocusChild.setOptional(true);
        
        //// type: undefined
        Feature typeUndefined = Feature.get(np, "type", "undefined");
        HasFeature hasTypeUndefined = new HasFeature(typeUndefined);
        
        
        // complement: actor
        Feature complementActor = Feature.get(np, "complement type", "actor");
        HasFeature hasComplementActor = new HasFeature(complementActor);
        
        And complementActorConds = new And();
        complementActorConds.addRule(hasNP);
        complementActorConds.addRule(hasTypeUndefined);
        complementActorConds.addRule(hasComplementActor);
                
        HasChild complementActorChild = new HasChild("complement actor", complementActorConds);
        complementActorChild.setOptional(true);
        
        
        // complement: object
        Feature complementObject = Feature.get(np, "complement type", "object");
        HasFeature hasComplementObject = new HasFeature(complementObject);
        
        And complementObjectConds = new And();
        complementObjectConds.addRule(hasNP);
        complementObjectConds.addRule(hasTypeUndefined);
        complementObjectConds.addRule(hasComplementObject);
                
        HasChild complementObjectChild = new HasChild("complement object", complementObjectConds);
        complementObjectChild.setOptional(true);
        
        
        // complement: directional
        Feature complementDirectional = Feature.get(np, "complement type", "object");
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
        rule.setName("rule 7");
        
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
        rule.setName("rule 8");
        
        rule.setInput(input);
        rule.addOutput(reorder);
        
        rules.add(rule);
    }
}
