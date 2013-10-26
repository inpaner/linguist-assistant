package grammar.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lexicon.model.Language;
import commons.dao.DAOFactory;
import commons.dao.DAOUtil;
import commons.dao.DBUtil;

public class FeatureDAO {
    private DAOFactory factory;
    
    private static final String SQL_POSSIBLE_VALUES = 
            "SELECT name " +
            "  FROM FeatureValue " +
            " WHERE featurePk = (?) ";
            
    private static final String SQL_DEFAULT_VALUE = 
            "SELECT name " +
            "  FROM FeatureValue " +
            " WHERE featurePk = (?) " +
            " LIMIT 1; ";
    
    private static final String FEATURE_FIELDS = 
            " name, description, " +
            " languagePk, semanticCategoryPk ";
    
    private static final String SQL_CREATE = 
            "INSERT INTO Feature(" + FEATURE_FIELDS + ") " +
            " VALUES (?, ?, ?, ?) ";
    
    private static final String SQL_RETRIEVE_ALL_FEATURES = 
            "SELECT pk, name, description, languagePk, categoryPk " +
            "  FROM Feature " +
            " WHERE categoryPK = (?); ";
    
    private static final String SQL_UPDATE =
            "UPDATE Feature SET " +
            "   name = (?), " +
            "   description = (?), " +
            "   languagePk = (?), " +
            "   semanticCategoryPk = (?) " +
            " WHERE pk = (?)";
                    
    private static final String SQL_DELETE =
            "DELETE FROM Feature WHERE pk = (?)";
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        Constituent cons = Constituent.getByName("Noun");
        for (Feature feature : dao.getAllFeatures(cons)) {
            System.out.println(feature);
            for (String value : dao.getPossibleValues(feature)) {
                System.out.println("---" + value);
            }
        }
    }
    
    public FeatureDAO(DAOFactory aDAOFactory) {
        factory = aDAOFactory;
    }

    List<String> getPossibleValues(Feature feature) {
        ArrayList<String> possibleValues = new ArrayList<>();
        Object[] values = {
                feature.getPk(),
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
    
    public List<Feature> getAllFeatures(Constituent constituent) {
        ArrayList<Feature> result = new ArrayList<Feature>();
        Object[] values = {
                constituent.getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_RETRIEVE_ALL_FEATURES;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feature feature = map(rs, constituent);
                result.add(feature);
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
    

    private Feature map(ResultSet rs, Constituent parent) throws SQLException {
        Feature feature = Feature.getEmpty(parent);
        feature.setPk(rs.getInt("pk"));
        feature.setName(rs.getString("name"));
        feature.setDescription(rs.getString("description"));
        Language language = Language.getInstance(rs.getInt("languagePk"));
        feature.setLanguage(language);
        return feature;
    }
}
