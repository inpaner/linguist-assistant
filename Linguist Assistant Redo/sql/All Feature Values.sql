SELECT FeatureValue.name AS name
  FROM FeatureValue
       JOIN Feature
         ON FeatureValue.featurePk = Feature.pk
       JOIN SemanticCategory
         ON Feature.semanticCategoryPk = SemanticCategory.pk
       JOIN SyntacticCategory
         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk
 WHERE SyntacticCategory.name = "Clause"
     AND Feature.name = "type"

