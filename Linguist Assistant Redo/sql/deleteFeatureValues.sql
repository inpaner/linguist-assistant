DELETE 
    FROM FeatureValue 
        WHERE FeatureValue.pk IN
         ( SELECT FeatureValue.pk 
                 FROM FeatureValue 
                         JOIN FEATURE ON FeatureValue.featurePk = Feature.pk JOIN SemanticCategory ON Feature.semanticCategoryPk = SemanticCategory.pk JOIN SyntacticCategory ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk 
            WHERE SyntacticCategory.name = "Clause" AND Feature.name = "listener" AND FeatureValue.name = "angel" );
