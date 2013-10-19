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
 
    private static final String SQL_ADD =
             "INSERT INTO Ontology(stem, sense, gloss, semanticCategoryPk) " +
             " VALUES (?, ?, ?, ?)";
    
    private DAOFactory fDAOFactory;
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        Constituent c = Constituent.get("N");
        Concept concept = new Concept(c);
        concept.setStem("dog");
        concept.setGloss("animal that barks");
        
        dao.add(concept);
    }
    
    public ConceptDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public void add(Concept aConcept) {
        Object[] values = {
                aConcept.getStem(),
                aConcept.getParent().getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // Get sense to add
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
        finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        values = new Object[] {
                aConcept.getStem(),
                newSense,
                aConcept.getGloss(),
                aConcept.getParent().getPk()
        };
        // Add concept
        try {
            String sql = SQL_ADD;
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
    
}
