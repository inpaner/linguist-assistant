package ontology.model;

public class FormDAO {
	
	   private static final String SQL_CREATE =
	             "INSERT INTO Form(pk, name,syntacticCategoryPk) " +
	             " VALUES (?, ?, ?)";
	    
	    private static final String SQL_RETRIEVE = 
	            "SELECT pk, name,syntacticCategoryPk " +
	            "  FROM Form " +
	            " WHERE name = (?) " +
	            "       AND " +
	            "       syntacticCategoryPk = (?) ";
	    
	    private static final String SQL_RETRIEVE_ALL_BY_SUBSTRING = 
	            "SELECT pk, name,syntacticCategoryPk " +
	            "  FROM Form " +
	            " WHERE name LIKE (?) " +
	            "       AND " +
	            "       syntacticCategoryPk = (?) ";

}
