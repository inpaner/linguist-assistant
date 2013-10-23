package lexicon.model;

import grammar.model.Constituent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

public class LanguageDAO {
    private DAOFactory factory;
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        LanguageDAO dao = new LanguageDAO(factory);
        Language empty = dao.retrieve(1);
        dao.delete(empty);
    }
    
    public LanguageDAO(DAOFactory aDAOFactory) {
        factory = aDAOFactory;
    }
    
    private static final String FIELDS = 
            " pk, name ";
    
    private static final String SQL_CREATE = 
            "INSERT INTO Language(" +  FIELDS + ") " +
            " VALUES (?) ";
    
    private static final String SQL_RETRIEVE_BY_PK = 
            "SELECT " +  FIELDS + " " + 
            " FROM Language " +
            " WHERE pk = (?) ";
    
    private static final String SQL_RETRIEVE_BY_UNIQUE = 
            "SELECT " +  FIELDS + " " + 
            " FROM Language " +
            " WHERE name = (?) ";
    
    private static final String SQL_DELETE =
            "DELETE FROM Language WHERE pk = (?)";
        
    private static final String SQL_UPDATE =
         "UPDATE Language SET " +
         "  name = (?) " +
         " WHERE pk = (?)";
    
    
    void create(Language language) {
        Object[] values = {
                language.getName(),
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_CREATE;
            System.out.println(SQL_CREATE);
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
    }
    
    Language retrieve(int pk) {
        Object[] values = {
                pk
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Language result = null;
        try {
            String sql = SQL_RETRIEVE_BY_PK;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            result = map(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return result;
    }
    
    Language retrieve(String name) {
        Object[] values = {
                name
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Language result = null;
        try {
            String sql = SQL_RETRIEVE_BY_UNIQUE;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            result = map(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return result;
    }
    
    void update(Language language) {
        Object[] values = {
                language.getName(),
                language.getPk(),
        };
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_UPDATE;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
    }
    
    void delete(Language language) {
        Object[] values = {
                language.getPk(),
        };
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_DELETE;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
    }
    
    private Language map(ResultSet rs) throws SQLException {
        Language result = Language.getEmpty();
        result.setPk(rs.getInt("pk"));
        result.setName(rs.getString("name"));
        return result;
    }
}
