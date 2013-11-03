package grammar.model;

import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lexicon.model.Entry;
import lexicon.model.Language;
import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

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
    
    private static final String SQL_RETRIEVE_LEXICON_FEATURES = 
            "SELECT pk, name, description, languagePk, categoryPk " +
            "  FROM Feature " +
            " WHERE languagePK = (?)  " +
            "       AND " +
            "       categoryPk = (?) ";
    
    private static final String SQL_RETRIEVE_LEXICON_FEATURE_VALUES = 
            "SELECT FeatureValue.name AS name " +
            "  FROM FeatureValue " +
            "       JOIN Feature " +
            "         ON Feature.pk = FeatureValue.featurePk " +
            "       JOIN LexiconFeature " +
            "         ON FeatureValue.pk = LexiconFeature.featureValuePk " +
            " WHERE LexiconFeature.lexiconPk = (?)  " +
            "       AND " +
            "       Feature.pk = (?); ";
            
    private static final String SQL_UPDATE =
            "UPDATE Feature SET " +
            "   name = (?), " +
            "   description = (?), " +
            "   languagePk = (?), " +
            "   categoryPk = (?) " +
            " WHERE pk = (?)";
                    
    private static final String SQL_DELETE =
            "DELETE FROM Feature WHERE pk = (?)";
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        Entry entry = Entry.getInstance(1);
        List<Feature> features = dao.retrieveAll(entry);
        for (Feature feature : features) {
            System.out.println(feature.getName() + " -- " + feature.getValue());
        }
        
    }
    
    public FeatureDAO(DAOFactory aDAOFactory) {
        factory = aDAOFactory;
    }

    List<String> getPossibleValues(Feature feature) {
        List<String> possibleValues = new ArrayList<>();
        Object[] values = {
                feature.getPk(),
        };
        List<Integer> list = new ArrayList<>();
        list.add(3);
        
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
    
    String getDefaultValue(Feature feature) {
        String defaultValue = "";
        
        Object[] values = {
                feature.getPk()
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
            defaultValue = rs.getString("name");
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
    
    public List<Feature> getAllFeatures(Category category) {
        ArrayList<Feature> result = new ArrayList<Feature>();
        Object[] values = {
                category.getPk()
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
                Feature feature = map(rs, category);
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
    
    public List<Feature> retrieveAll(Entry entry) {
        ArrayList<Feature> result = new ArrayList<Feature>();
        Object[] values = {
                entry.getConstituent().getPk(),
                entry.getLanguage().getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // acquire features
        try {
            String sql = SQL_RETRIEVE_LEXICON_FEATURES;
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feature feature = map(rs, entry.getConstituent());
                result.add(feature);
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // assign values
        try {
            String sql = SQL_RETRIEVE_LEXICON_FEATURE_VALUES;
            for (Feature feature : result) {
                values = new Object[] {
                    entry.getPk(),
                    feature.getPk()
                };
                ps = DAOUtil.prepareStatement(conn, sql, false, values);
                rs = ps.executeQuery();
                if (rs.next()) {
                    feature.setValue(rs.getString("name"));
                }
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
    

    private Feature map(ResultSet rs, Category parent) throws SQLException {
        Feature feature = Feature.getEmpty(parent);
        feature.setPk(rs.getInt("pk"));
        feature.setName(rs.getString("name"));
        String value = getDefaultValue(feature);
        feature.setValue(value);
        feature.setDescription(rs.getString("description"));
        Language language = Language.getInstance(rs.getInt("languagePk"));
        feature.setLanguage(language);
        return feature;
    }
}
