package grammar.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;
import commons.dao.DBUtil;

public class ConstituentDAO {
    private static final String SQL_GET_BY_SYNTACTIC_ABBR = 
            "SELECT SemanticCategory.name AS semName, " +
            "       SemanticCategory.abbreviation AS semAbbr, " +
            "       SemanticCategory.deepAbbreviation AS deepAbbr, " +
            "       SyntacticCategory.name AS synName, " +
            "       SyntacticCategory.abbreviation AS synAbbr " +
            "  FROM SemanticCategory " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.abbreviation = (?); ";
    
    private static final String SQL_GET_ALL = 
            "SELECT SemanticCategory.name AS semName, " +
            "       SemanticCategory.abbreviation AS semAbbr, " +
            "       SemanticCategory.deepAbbreviation AS deepAbbr, " +
            "       SyntacticCategory.name AS synName, " +
            "       SyntacticCategory.abbreviation AS synAbbr " +
            "  FROM SemanticCategory " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk ";
    
    public ConstituentDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        ConstituentDAO dao = new ConstituentDAO(factory);
        for (Constituent c : dao.getAllConstituents()) {
            System.out.println(c.getDeepAbbreviation());
        }
    }
    
    public Constituent getBySyntacticAbbr(String syntacticAbbr) {
        Constituent constituent = null;
        Object[] values = {
                syntacticAbbr,
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_GET_BY_SYNTACTIC_ABBR;
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
            String sql = SQL_GET_ALL;
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
    
    private Constituent map(ResultSet rs) throws SQLException {
        Constituent constituent = new Constituent();
        constituent.setSyntacticCategory(rs.getString("synName"));
        constituent.setSyntacticAbbreviation(rs.getString("synAbbr"));
        constituent.setSemanticCategory(rs.getString("semName"));
        constituent.setSemanticAbbreviation(rs.getString("semAbbr"));
        constituent.setDeepAbbreviation(rs.getString("deepAbbr"));
        constituent.fLevel = 0;
        return constituent;
    }
    
    private DAOFactory fDAOFactory;
}
