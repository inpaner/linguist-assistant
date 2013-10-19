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

public class FeatureDAO {
    
    /*
     * SQL Statements
     */
    private DAOFactory fDAOFactory;
    
    private static final String SQL_POSSIBLE_VALUES = 
            "SELECT FeatureValue.name AS name " +
            "  FROM FeatureValue " +
            "       JOIN Feature " +
            "         ON FeatureValue.featurePk = Feature.pk " +
            "       JOIN SemanticCategory " +
            "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.name = (?) " +
            "       AND " +
            "       Feature.name = (?) ";
    
    private static final String SQL_DEFAULT_VALUE = 
            "SELECT FeatureValue.name AS name " +
            "  FROM FeatureValue " +
            "       JOIN Feature " +
            "         ON FeatureValue.featurePk = Feature.pk " +
            "       JOIN SemanticCategory " +
            "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.name = (?) " +
            "       AND " +
            "       Feature.name = (?) " +
            " LIMIT 1; ";
    
    private static final String SQL_ADD_FEATURE  = 
            "SELECT FeatureValue.name AS name " +
            "  FROM FeatureValue " +
            "       JOIN Feature " +
            "         ON FeatureValue.featurePk = Feature.pk " +
            "       JOIN SemanticCategory " +
            "         ON Feature.semanticCategoryPk = SemanticCategory.pk " +
            "       JOIN SyntacticCategory " +
            "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.name = (?) " +
            "       AND " +
            "       Feature.name = (?) " +
            " LIMIT 1; ";
       
    public void main(String[] args) {
        Constituent cons = new Constituent(null);
        cons.setLabel("Noun");
        Feature feature = new Feature("type", cons);
    }
    
    public FeatureDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }

    List<String> getPossibleValues(Feature aFeature) {
        ArrayList<String> possibleValues = new ArrayList<>();
        Object[] values = {
                aFeature.getParent().getSyntacticCategory(),
                aFeature.getName()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_POSSIBLE_VALUES;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                possibleValues.add(rs.getString("name"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        return possibleValues;
    }
    
    String getDefaultValue(Feature aFeature) {
        String defaultValue = "";
        
        Object[] values = {
                aFeature.getParent().getSyntacticCategory(),
                aFeature.getName()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_DEFAULT_VALUE;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            rs.next();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        return defaultValue;
    }
    
    void addFeature(Feature aFeature) {
        String defaultValue = "";
        
        Object[] values = {
                aFeature.getPk(),
                aFeature.getParent().getPk()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_ADD_FEATURE;
            conn = fDAOFactory.getConnection();
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
    
}
