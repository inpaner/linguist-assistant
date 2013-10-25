package grammar.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lexicon.model.Form;
import commons.dao.DAOFactory;
import commons.dao.DAOUtil;
import commons.dao.DBUtil;

public class ConstituentDAO {    
    public static void main(String[] args) {
        Constituent con = Constituent.getByName("Noun");
        System.out.println(con.getName());
    }
    
    
    private static final String SQL_RETRIEVE_BY_ABBR = 
            "SELECT pk, name, abbreviation, description " +
            "  FROM Category " +
            " WHERE abbreviation = (?); ";
    
    private static final String SQL_RETRIEVE_BY_NAME = 
            "SELECT pk, name, abbreviation, description " +
            "  FROM Category " +
            " WHERE name = (?); ";
    
            
    private static final String SQL_RETRIEVE_ALL = 
            "SELECT pk, name, abbreviation, description " +
            "  FROM Category ";
    
    private static final String SQL_RETRIEVE = 
            "SELECT pk, name, abbreviation, description " +
            "  FROM Category " +
            " WHERE pk = (?); ";
            
    
    private static final String SQL_GET_ALL_FEATURES = 
            "SELECT SemanticCategory.pk AS pk, name " +
            "  FROM Feature " +
            " WHERE semanticCategoryPK = (?); ";
    
    private static final String SQL_GET_ALL_FORMS = 
            "SELECT pk, name " +
            "  FROM Form " +
            " WHERE semanticCategoryPK = (?); ";
    
    public ConstituentDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public Constituent retrieve(int pk) {
        Object[] values = {
                pk,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Constituent result = null;
        
        try {
            String sql = SQL_RETRIEVE;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            rs.next();
            result = map(rs);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return result;
    }
    
    public Constituent retrieveByAbbreviation(String abbreviation) {
        Constituent constituent = null;
        Object[] values = {
                abbreviation,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_BY_ABBR;
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

    public Constituent retrieveByName(String category) {
        Constituent constituent = null;
        Object[] values = {
                category,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_BY_NAME;
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
        constituent.setName(rs.getString("name"));
        constituent.setAbbreviation(rs.getString("abbreviation"));
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
