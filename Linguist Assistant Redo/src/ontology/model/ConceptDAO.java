package ontology.model;

import grammar.model.Constituent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

public class ConceptDAO {
    private static final String SQL_LATEST_SENSE =
            "SELECT sense " +
            "  FROM Ontology " +
            " WHERE stem = (?) " +
            "       AND " +
            "       semanticCategoryPk = (?) " +
            " ORDER BY sense DESC " +
            " LIMIT 1; ";
 
    private static final String SQL_CREATE =
             "INSERT INTO Ontology(stem, sense, gloss, semanticCategoryPk) " +
             " VALUES (?, ?, ?, ?)";
    
    private static final String SQL_ADD_TAG = 
            "SELECT pk, stem, sense, semanticCategoryPk " +
            "  FROM Ontology " +
            " WHERE stem = (?) " +
            "       AND " +
            "       sense = (?) " +
            "       AND " +
            "       semanticCategoryPk = (?) ";
    
    private static final String SQL_RETRIEVE = 
            "INSERT INTO OntologyTag(ontologyPk, tagPk) " +
             " VALUES (?, ?)";
    
    private DAOFactory fDAOFactory;
    
    
    public ConceptDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public void create(Concept aConcept) {
        Object[] values = {
                aConcept.getStem(),
                aConcept.getParent().getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Query latest sense then use next sense 
        // in sequence for creation
        String newSense = "";
        try {
            String sql = SQL_LATEST_SENSE;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            String lastSense = "";
            if (rs.next()) {        
                lastSense = rs.getString("sense");
            }
            newSense = Concept.getNextSense(lastSense);
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        values = new Object[] {
                aConcept.getStem(),
                newSense,
                aConcept.getGloss(),
                aConcept.getParent().getPk()
        };

        // Create concept
        try {
            String sql = SQL_CREATE;
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            ps.executeUpdate();
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
    }
    
    
    public Concept retrieve(String stem, String gloss, Constituent constituent) {
        Object[] values = {
                stem,
                gloss,
                constituent.getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Concept concept = null;
        try {
            String sql = SQL_RETRIEVE;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                concept = map(rs, constituent); 
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        return concept;
    }
    
    public void addTag(Concept aConcept, Tag aTag) {
        Object[] values = {
                aConcept.getPk(),
                aTag.getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String sql = SQL_ADD_TAG;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            ps.executeUpdate();   
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            DAOUtil.close(conn, ps, rs);
        }
    }
    
    private Concept map(ResultSet rs, Constituent constituent) throws SQLException {
        Concept result = new Concept(constituent);
        int pk = rs.getInt("pk");
        String stem = rs.getString("stem");
        String gloss = rs.getString("gloss");
        result.setPk(pk);
        result.setStem(stem);
        result.setGloss(gloss);
        return result;
    }
}
