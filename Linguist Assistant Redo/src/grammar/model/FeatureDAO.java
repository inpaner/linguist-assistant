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
    private DAOFactory factory;
    
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
    
    private static final String FEATURE_FIELDS = 
            " name, description, " +
            " languagePk, semanticCategoryPk ";
    
    private static final String SQL_CREATE = 
            "INSERT INTO Feature(" + FEATURE_FIELDS + ") " +
            " VALUES (?, ?, ?, ?) ";
   
    private static final String SQL_DELETE =
            "DELETE FROM Feature WHERE pk = (?)";
           
    private static final String SQL_UPDATE =
            "UPDATE Feature SET " +
            "   name = (?), " +
            "   description = (?), " +
            "   languagePk = (?), " +
            "   semanticCategoryPk = (?) " +
            " WHERE pk = (?)";
                    
    public void main(String[] args) {
        Constituent cons = new Constituent(null);
        cons.setLabel("Noun");
        Feature feature = new Feature("type", cons);
    }
    
    public FeatureDAO(DAOFactory aDAOFactory) {
        factory = aDAOFactory;
    }

    List<String> getPossibleValues(Feature aFeature) {
        ArrayList<String> possibleValues = new ArrayList<>();
        Object[] values = {
                aFeature.getParent().getName(),
                aFeature.getName()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_POSSIBLE_VALUES;
            conn = factory.getConnection();
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
                aFeature.getParent().getName(),
                aFeature.getName()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_DEFAULT_VALUE;
            conn = factory.getConnection();
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
    
    void create(Feature feature) {
        Object[] values = {
                feature.getName(),
                feature.getDescription(),
                feature.getLanguage().getPk(),
                feature.getParent().getPk()
        };
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_CREATE;
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
    
    void update(Feature feature) {
        Object[] values = {
                feature.getName(),
                feature.getDescription(),
                feature.getLanguage().getPk(),
                feature.getParent().getPk(),
                feature.getPk(),
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
    
    void delete(Feature feature) {
        Object[] values = {
                feature.getPk(),
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
}
