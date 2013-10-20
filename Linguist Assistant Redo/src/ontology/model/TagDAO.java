package ontology.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

public class TagDAO {
    private static final String SQL_RETREIVE_ALL = 
            "SELECT pk, name " +
            "  FROM Tag ";
    
    private static final String SQL_RETREIVE_BY_PK = 
            "SELECT pk, name " +
            "  FROM Tag " +
            " WHERE pk = (?) ";
    
    private static final String SQL_RETREIVE_BY_NAME = 
            "SELECT pk, name " +
            "  FROM Tag " +
            " WHERE name = (?) ";
             
    
    private DAOFactory fDAOFactory;
    
    public TagDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public List<Tag> retrieveAll() {
        List<Tag> result = new ArrayList<>();
        Object[] values = {
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = SQL_RETREIVE_ALL;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(map(rs));
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }        
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return result;
    }
    
    public Tag retrieve(int aPk) {
        Object[] values = {
                aPk
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tag tag = null;
        try {
            String sql = SQL_RETREIVE_BY_PK;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            if (rs.next()) {        
                tag = map(rs);
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }        
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return tag;
    }
    
    public Tag retrieve(String aName) {
        Object[] values = {
                aName
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tag tag = null;
        try {
            String sql = SQL_RETREIVE_BY_NAME;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            if (rs.next()) {        
                tag = map(rs);
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }        
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return tag;
    }
    
    private Tag map(ResultSet rs) throws SQLException {
        int pk = rs.getInt("pk");
        String name = rs.getString("name"); 
        return new Tag(pk, name);
    }
    

}
