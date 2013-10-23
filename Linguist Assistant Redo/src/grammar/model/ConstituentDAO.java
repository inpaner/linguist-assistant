package grammar.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ontology.model.Form;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;
import commons.dao.DBUtil;

public class ConstituentDAO {    
    
    private static final String SQL_RETRIEVE_BY_SYNTACTIC_ABBR = 
            "SELECT SemanticCategory.pk AS pk, " +
            "       SemanticCategory.name AS semName, " +
            "       SemanticCategory.abbreviation AS semAbbr, " +
            "       SemanticCategory.deepAbbreviation AS deepAbbr, " +
            "       SyntacticCategory.name AS synName, " +
            "       SyntacticCategory.abbreviation AS synAbbr " +
            "  FROM SemanticCategory " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.abbreviation = (?); ";
    
    private static final String SQL_RETRIEVE_BY_SYNTACTIC_CATEGORY = 
            "SELECT SemanticCategory.pk AS pk, " +
            "       SemanticCategory.name AS semName, " +
            "       SemanticCategory.abbreviation AS semAbbr, " +
            "       SemanticCategory.deepAbbreviation AS deepAbbr, " +
            "       SyntacticCategory.name AS synName, " +
            "       SyntacticCategory.abbreviation AS synAbbr " +
            "  FROM SemanticCategory " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.name = (?); ";
    
    private static final String SQL_RETRIEVE_ALL = 
            "SELECT SemanticCategory.pk AS pk, " +
            "       SemanticCategory.name AS semName, " +
            "       SemanticCategory.abbreviation AS semAbbr, " +
            "       SemanticCategory.deepAbbreviation AS deepAbbr, " +
            "       SyntacticCategory.name AS synName, " +
            "       SyntacticCategory.abbreviation AS synAbbr " +
            "  FROM SemanticCategory " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk ";
    
    private static final String SQL_GET_ALL_FEATURES = 
            "SELECT pk, name " +
            "  FROM Feature " +
            " WHERE semanticCategoryPK = (?); ";
    
    private static final String SQL_GET_ALL_FORMS = 
            "SELECT pk, name " +
            "  FROM Form " +
            " WHERE semanticCategoryPK = (?); ";
    
    public ConstituentDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public Constituent retrieveBySyntacticAbbr(String syntacticAbbr) {
        Constituent constituent = null;
        Object[] values = {
                syntacticAbbr,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_BY_SYNTACTIC_ABBR;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            rs.next();
            constituent = map(rs);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return constituent;
    }

    public Constituent retrieveBySyntacticCategory(String category) {
        Constituent constituent = null;
        Object[] values = {
                category,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_BY_SYNTACTIC_CATEGORY;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            rs.next();
            constituent = map(rs);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return constituent;
    }
    
    public List<Constituent> getAllConstituents() {
        List<Constituent> allConstituents = new ArrayList<Constituent>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] values = {};

        try {
            String sql = SQL_RETRIEVE_ALL;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                allConstituents.add(map(rs));
            }
            DBUtil.finishQuery();
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        return allConstituents;
    }
    
    public List<Feature> getAllFeatures(Constituent constituent) {
        ArrayList<Feature> allFeatures = new ArrayList<Feature>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] values = {
                constituent.getPk()
        };

        try {
            String sql = SQL_GET_ALL_FEATURES;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                Integer pk = rs.getInt("pk");
                String name = rs.getString("name");
                allFeatures.add(new Feature(pk, name, constituent));
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return allFeatures;
    }
    
    private Constituent map(ResultSet rs) throws SQLException {
        Constituent constituent = new Constituent();
        constituent.setPk(rs.getInt("pk"));
        constituent.setSyntacticCategory(rs.getString("synName"));
        constituent.setSyntacticAbbreviation(rs.getString("synAbbr"));
        constituent.setSemanticCategory(rs.getString("semName"));
        constituent.setSemanticAbbreviation(rs.getString("semAbbr"));
        constituent.setDeepAbbreviation(rs.getString("deepAbbr"));
        constituent.level = 0;
        return constituent;
    }
    
    private DAOFactory fDAOFactory;

	public List<Form> getAllForms(Constituent constituent) {
		// TODO Auto-generated method stub
		 ArrayList<Form> allForms = new ArrayList<Form>();
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        Object[] values = {
	                constituent.getPk()
	        };

	        try {
	            String sql = SQL_GET_ALL_FORMS;
	            conn = fDAOFactory.getConnection();
	            ps = DAOUtil.prepareStatement(conn, sql, false, values);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                Integer pk = rs.getInt("pk");
	                String name = rs.getString("name");
	                allForms.add(new Form(name));
	            }
	        } 
	        
	        catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        finally {
	            DAOUtil.close(conn, ps, rs);
	        }
	        
	        return allForms;
	
	}
}
