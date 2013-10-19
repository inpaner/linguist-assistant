DELETE 
    FROM Feature
        WHERE pk IN
        (SELECT Feature.pk 
                	FROM Feature
                	    	JOIN SemanticCategory
                      ON Feature.semanticCategoryPk = SemanticCategory.pk
                      JOIN SyntacticCategory
                      ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk
          WHERE SemanticCategory.name = "Object"and Feature.name = "number")    
