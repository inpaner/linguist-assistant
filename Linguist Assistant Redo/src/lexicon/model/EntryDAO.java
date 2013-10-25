package lexicon.model;

import grammar.model.Constituent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

public class EntryDAO {
private DAOFactory factory;
    
    public static void main(String[] args) {
        EntryDAO dao = new EntryDAO(DAOFactory.getInstance());
        Language lang = Language.getInstance("English");
        Constituent c = Constituent.getByName("Noun");
        dao.retrieveAll(lang, c);
    }
    
    public EntryDAO(DAOFactory aDAOFactory) {
        factory = aDAOFactory;
    }
    
    private static final String FIELDS = 
            " pk, stem, gloss, languagePk, categoryPk ";
    
    private static final String SQL_CREATE = 
            "INSERT INTO Lexicon(" +  FIELDS + ") " +
            " VALUES (?) ";
    
    private static final String SQL_RETRIEVE_BY_PK = 
            "SELECT " +  FIELDS + " " + 
            " FROM Lexicon " +
            " WHERE pk = (?) ";
    
    private static final String SQL_RETRIEVE_ALL = 
            "SELECT " +  FIELDS + " " + 
            " FROM Lexicon " +
            " WHERE languagePk = (?) " +
            "       AND " +
            "       categoryPk = (?) ";
    
    private static final String SQL_DELETE =
            "DELETE FROM Entry WHERE pk = (?)";
        
    private static final String SQL_UPDATE =
             "UPDATE Lexicon SET " +
             "  name = (?) " +
             " WHERE pk = (?)";
        
    void create(Entry entry) {
        Object[] values = {
                entry.getStem(),
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
    
    Entry retrieve(int pk) {
        Object[] values = {
                pk
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Entry result = null;
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
    
    List<Entry> retrieveAll(Language language, Constituent constituent) {
        Object[] values = {
                language.getPk(),
                constituent.getPk()
        };
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Entry> result = new ArrayList<>();
        try {
            String sql = SQL_RETRIEVE_ALL;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                Entry item = map(rs);
                result.add(item);    
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return result;
    }
    
    void update(Entry entry) {
        Object[] values = {
                entry.getStem(),
                entry.getPk(),
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
    
    void delete(Entry entry) {
        Object[] values = {
                entry.getPk(),
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
    
    private Entry map(ResultSet rs) throws SQLException {
        Entry result = Entry.getEmpty();
        result.setPk(rs.getInt("pk"));
        result.setStem(rs.getString("stem"));
        result.setGloss(rs.getString("gloss"));
        
        Language language = Language.getInstance(rs.getInt("languagePk"));
        result.setLanguage(language);
        Constituent constituent = Constituent.getInstance(rs.getInt("categoryPk"));
        result.setConstituent(constituent);
        return result;
    }
}