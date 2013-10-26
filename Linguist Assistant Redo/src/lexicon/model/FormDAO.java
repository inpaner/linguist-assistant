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

public class FormDAO {
	DAOFactory factory;
	
	public FormDAO(DAOFactory aDAOFactory) {
        factory = aDAOFactory;
    }
	
	private static final String SQL_CREATE =
	             "INSERT INTO Form(pk, name, categoryPk) " +
	             " VALUES (?, ?, ?)";
	    
    private static final String SQL_RETRIEVE_ALL = 
            "SELECT pk, name, description, languagePk, categoryPk " +
            "  FROM Form " +
            " WHERE languagePk = (?) " +
            "       AND " +
            "       categoryPk = (?) ";
    
    private static final String SQL_RETRIEVE_VALUE = 
            "SELECT pk, value, lexiconPk, formPk " +
            "  FROM LexiconForm " +
            " WHERE lexiconPk = (?) " +
            "       AND " +
            "       formPk = (?) ";
    
    private static final String SQL_RETRIEVE_ALL_BY_SUBSTRING = 
            "SELECT pk, name,syntacticCategoryPk " +
            "  FROM Form " +
            " WHERE name LIKE (?) " +
            "       AND " +
            "       syntacticCategoryPk = (?) ";
    
    private static final String SQL_GET_BY_SYNTACTIC_CATEGORY = 
            "SELECT Form.pk AS pk, " +
            "       Form.name AS formName, " +
            "       SyntacticCategory.name AS synName, " +
            "       SyntacticCategory.abbreviation AS synAbbr " +
            "  FROM Form " +
            "       JOIN SyntacticCategory " +
            "         ON Form.syntacticCategoryPk = SyntacticCategory.pk " +
            " WHERE SyntacticCategory.name = (?); ";
    
    public void create(Form form) {
       Object[] values = {
                form.getPK(),
                form.getName(),
                form.getConstituent().getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        values = new Object[] {
        		form.getPK(),
                form.getName(),
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
    
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        FormDAO dao = new FormDAO(factory);
        Entry entry = Entry.getInstance(5);
        for (Form form :dao.retrieveAll(entry)) {
            System.out.println(form.getName() + " " + form.getValue());
        }
        
    }
    
    public List<Form> retrieveAll(Entry entry) {
        // Get all forms
        Object[] values = {
                entry.getLanguage().getPk(),
                entry.getConstituent().getPk()
        };

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        List<Form> result = new ArrayList<>();

        try {
            String sql = SQL_RETRIEVE_ALL;
            conn = factory.getConnection();            
            ps = DAOUtil.prepareStatement(conn, sql, false, values);
            rs = ps.executeQuery();
            while (rs.next()) {
                Form form = map(rs, entry.getLanguage(), entry.getConstituent());
                result.add(form);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        for (Form form : result) {
            values = new Object[] {
                    entry.getPk(),
                    form.getPk()
            };
            try {
                String sql = SQL_RETRIEVE_VALUE;
                conn = factory.getConnection();            
                ps = DAOUtil.prepareStatement(conn, sql, false, values);
                rs = ps.executeQuery();
                rs.next();
                form.setValue(rs.getString("value"));
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }    
        }
        
        DAOUtil.close(conn, ps, rs);
        return result;
    }
    

    
    private Form map(ResultSet rs, Language language, Constituent constituent) 
                                                    throws SQLException {
        Form form = Form.getEmpty();
        form.setPk(rs.getInt("pk"));
        form.setName(rs.getString("name"));
        form.setDescription(rs.getString("description"));
        form.setLanguage(language);
        form.setConstituent(constituent);

        return form;
    }
}
