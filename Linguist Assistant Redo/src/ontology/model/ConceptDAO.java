package ontology.model;

import grammar.model.Constituent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import commons.dao.DAOFactory;
import commons.dao.DAOUtil;

public class ConceptDAO {
    private static final String SQL_LATEST_SENSE =
            "SELECT sense " +
            "  FROM Ontology " +
            " WHERE stem = (?) " +
            "       AND " +
            "       categoryPk = (?) " +
            " ORDER BY sense DESC " +
            " LIMIT 1; ";
 
    private static final String SQL_CREATE =
             "INSERT INTO Ontology(stem, sense, gloss, categoryPk) " +
             " VALUES (?, ?, ?, ?)";
    
    private static final String SQL_RETRIEVE = 
            "SELECT pk, stem, sense, gloss, semanticCategoryPk " +
            "  FROM Ontology " +
            " WHERE stem = (?) " +
            "       AND " +
            "       sense = (?) " +
            "       AND " +
            "       categoryPk = (?) ";
    
    private static final String SQL_RETRIEVE_ALL_BY_SUBSTRING = 
            "SELECT Ontology.pk AS pk, stem, sense, gloss, categoryPk " +
            "  FROM Ontology " +
            " WHERE stem LIKE (?) " +
            "       AND " +
            "       categoryPk = (?) " +
            " ORDER BY stem ";
    
    
    private static final String SQL_RETRIEVE_ALL_WITH_TAG = 
            "SELECT Ontology.pk AS pk, stem, sense, gloss, categoryPk " +
            "  FROM Ontology " +
            "  JOIN OntologyTag " +
            "       ON Ontology.pk = OntologyTag.ontologyPk " +
            " WHERE stem LIKE (?) " +
            "       AND " +
            "       categoryPk = (?) " +
            "       AND " +
            "       tagPk = (?) " +
            " ORDER BY stem ";
    
    private static final String SQL_ADD_TAG = 
            "INSERT INTO OntologyTag(ontologyPk, tagPk) " +
            " VALUES (?, ?)";
    
    private static final String SQL_ALL_TAGS =
            "SELECT tagPk " +
            "  FROM OntologyTag " +
            " WHERE ontologyPk = (?) ";
            
    
    private DAOFactory fDAOFactory;
    
    public ConceptDAO(DAOFactory aDAOFactory) {
        fDAOFactory = aDAOFactory;
    }
    
    public void create(Concept aConcept) {
        Object[] values = {
                aConcept.getStem(),
                aConcept.getParent().getPk()
        };
        System.out.println("here");
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
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        Constituent con = Constituent.getByAbbreviation("N");
        
        long start = System.nanoTime();
        List<Concept> result = dao.retrieveBySubstring("Adah", con);
        long end = System.nanoTime();
        long elapsedTime = end - start;
        
        for (Concept item : result) {
            System.out.println(item);
            System.out.println(item.getTags());
        }
        
        double seconds = (double)elapsedTime / 1000000000.0;
        System.out.println("Total time: " + seconds);
    }
    
    public List<Concept> retrieveBySubstring(String stemSubString, Constituent constituent) {
        List<Concept> result = new ArrayList<>();
        Object[] values = {
                "%" + stemSubString + "%",
                constituent.getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = SQL_RETRIEVE_ALL_BY_SUBSTRING;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                result.add(map(rs, constituent));
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

    public List<Concept> retrieveByTag(String stemSubString, Tag tag, Constituent constituent) {
        List<Concept> result = new ArrayList<>();
        Object[] values = {
                "%" + stemSubString + "%",
                constituent.getPk(),
                tag.getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = SQL_RETRIEVE_ALL_WITH_TAG;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                result.add(map(rs, constituent));
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
    
    public List<Tag> retrieveAllTags(Concept concept) {
        List<Tag> result = new ArrayList<>();
        Object[] values = {
                concept.getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = SQL_ALL_TAGS;
            conn = fDAOFactory.getConnection();
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Tag tag = Tag.getInstance(rs.getInt("tagPk"));
                result.add(tag);
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
    
    private Concept map(ResultSet rs, Constituent constituent) throws SQLException {
        Concept result = new Concept(constituent);
        result.setPk(rs.getInt("pk"));
        result.setStem(rs.getString("stem"));
        result.setSense(rs.getString("sense"));
        result.setGloss(rs.getString("gloss"));
        return result;
    }
}
