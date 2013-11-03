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

public class CategoryDAO {    
    public static void main(String[] args) {
        Category con = Category.getByName("Noun");
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
    
    private static final String SQL_GET_ALL_FORMS = 
            "SELECT pk, name " +
            "  FROM Form " +
            " WHERE categoryPK = (?); ";
    
    public CategoryDAO(DAOFactory factory) {
        this.factory = factory;
    }
    
    public Category retrieve(int pk) {
        Object[] values = {
                pk,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Category result = null;
        
        try {
            String sql = SQL_RETRIEVE;
            conn = factory.getConnection();
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
    
    public Category retrieveByAbbreviation(String abbreviation) {
        Category category = null;
        Object[] values = {
                abbreviation,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_BY_ABBR;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            rs.next();
            category = map(rs);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return category;
    }

    public Category retrieveByName(String categoryString) {
        Category category = null;
        Object[] values = {
                category,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_BY_NAME;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            rs.next();
            category = map(rs);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return category;
    }
    
    public List<Category> getAllConstituents() {
        List<Category> allConstituents = new ArrayList<Category>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] values = {};

        try {
            String sql = SQL_RETRIEVE_ALL;
            conn = factory.getConnection();
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
    
        
    public List<Form> getAllForms(Category category) {
    	// TODO Auto-generated method stub
    	 ArrayList<Form> allForms = new ArrayList<Form>();
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Object[] values = {
                    category.getPk()
            };
    
            try {
                String sql = SQL_GET_ALL_FORMS;
                conn = factory.getConnection();
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

    private Category map(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setPk(rs.getInt("pk"));
        category.setName(rs.getString("name"));
        category.setAbbreviation(rs.getString("abbreviation"));
        return category;
    }
    
    private DAOFactory factory;
}
