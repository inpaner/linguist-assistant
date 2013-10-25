package lexicon.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

public class FormDAO {
	DAOFactory fDAOFactory=new DAOFactory();
	public FormDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
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
	    private static final String SQL_GET_BY_SYNTACTIC_CATEGORY = 
	            "SELECT Form.pk AS pk, " +
	            "       Form.name AS formName, " +
	            "       SyntacticCategory.name AS synName, " +
	            "       SyntacticCategory.abbreviation AS synAbbr " +
	            "  FROM Form " +
	            "       JOIN SyntacticCategory " +
	            "         ON Form.syntacticCategoryPk = SyntacticCategory.pk " +
	            " WHERE SyntacticCategory.name = (?); ";
	    public Form getBySyntacticCategory(String category) {
	        Form form = null;
	        Object[] values = {
	                category,
	        };

	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        
	        try {
	            String sql = SQL_GET_BY_SYNTACTIC_CATEGORY;
	            conn = fDAOFactory.getConnection();
	            ps = DAOUtil.prepareStatement(conn, sql, false, values);
	            rs = ps.executeQuery();
	            rs.next();
	            form = map(rs);
	        } 
	        catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        finally {
	            DAOUtil.close(conn, ps, rs);
	        }
	        
	        return form;
	    }
	    public void create(Form form) {
	       Object[] values = {
	                form.getPK(),
	                form.getName(),
	                form.getSyntacticCategoryPK()
	        };

	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        
	        values = new Object[] {
	        		form.getPK(),
	                form.getName(),
	                form.getSyntacticCategoryPK()
	        };

	        // Create concept
	        try {
	            String sql = SQL_CREATE;
	            ps = DAOUtil.prepareStatement(conn, sql, false, values);
	            ps.executeUpdate();
	            
	        }
	        catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        finally {
	            DAOUtil.close(conn, ps, rs);
	        }
	    }
	    
	    private Form map(ResultSet rs) throws SQLException {
	        Form form=new Form(null);
	        form.setPk(rs.getInt("pk"));
	        form.setSyntacticCategory(rs.getString("synName"));
	        return form;
	    }
}
