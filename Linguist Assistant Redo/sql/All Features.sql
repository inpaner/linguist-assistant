SELECT Feature.name AS name
  FROM Feature
       JOIN SemanticCategory
         ON Feature.semanticCategoryPk = SemanticCategory.pk
       JOIN SyntacticCategory
         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk
 WHERE SyntacticCategory.name = "Clause";

