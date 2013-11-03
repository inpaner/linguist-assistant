package grammar.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexicon.model.Form;
import ontology.model.Concept;
import commons.dao.DAOFactory;
import commons.dao.DBUtil;

/**
 * Blah di blah
 * 
 * @author Ivan
 * 
 */

public class Category {
    private static Map<Integer, List<Feature>> fPossibleFeatures = new HashMap<>();
    private static Map<Integer, List<Form>> fPossibleForms = new HashMap<>();
/*    
    public static void main(String[] args) {
        Constituent con = Constituent.getByName("Noun");
        System.out.println(con.abbreviation);
    }
    */
    
    // TODO implement properly with copying of features
    public static Category copy(Category toCopy) {
        Category clone = new Category();
        clone.pk = toCopy.pk;
        clone.name = toCopy.name;
        clone.abbreviation = toCopy.abbreviation;
        
        return clone;
    }
    
    public static List<Category> getAll() {
        DAOFactory factory = DAOFactory.getInstance();
        CategoryDAO dao = new CategoryDAO(factory);
        return dao.getAllConstituents();
    }
    
    public static Category getInstance(int pk) {
        DAOFactory factory = DAOFactory.getInstance();
        CategoryDAO dao = new CategoryDAO(factory);
        return dao.retrieve(pk);
    }
    
    public static Category getByAbbreviation(String abbreviation) {
        DAOFactory factory = DAOFactory.getInstance();
        CategoryDAO dao = new CategoryDAO(factory);
        return dao.retrieveByAbbreviation(abbreviation);
    }
    
    public static Category getByName(String name) {
        DAOFactory factory = DAOFactory.getInstance();
        CategoryDAO dao = new CategoryDAO(factory);
        return dao.retrieveByName(name);
    }
    
    public static Category getEmpty() {
        return new Category();
    }
    
    private Integer pk;
    private String name;
    private String abbreviation;
    private ArrayList<Feature> features = new ArrayList<>();;
    private ArrayList<Form> forms = new ArrayList<>();;
    
    public void setLabel(String label) {
        this.name = label;
    }
    
    public String getLabel() {
        return name;
    }
    
    public boolean hasFeatures() {
        return features.size() > 0;
    }
        
    public List<Form> getForms() {
        List<Form> allForms = new ArrayList<Form>();
        List<Form> possibleForms = fPossibleForms.get(pk);
        
        if (possibleForms == null) {
            DAOFactory factory = DAOFactory.getInstance();
            CategoryDAO dao = new CategoryDAO(factory);
            possibleForms = dao.getAllForms(this);
            fPossibleForms.put(pk, possibleForms);
        }
        
        for (Form Form : possibleForms) {
            boolean found = false;
            for (Form ownForm : forms) {
                if (ownForm.equals(Form)) {
                    allForms.add(ownForm);
                    found = true;
                    break;
                }
            }
            if (!found) {
                allForms.add(Form);
            }
           
        }
        return allForms;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Category))
            return false;

        Category otherCon = (Category) other;
        
        return name.equals(otherCon.name);

    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    public String getName() {
        return name;
    }
    
    public void addNewFeature(String string) {
        try {
            String query =
                    "SELECT SemanticCategory.pk AS pk" +
                    "  FROM SemanticCategory " +
                    "       JOIN SyntacticCategory " +
                    "         ON SemanticCategory.syntacticCategoryPk = SyntacticCategory.pk " +
                    " WHERE SyntacticCategory.name = '" + name + "'; ";
            
            ResultSet rs = DBUtil.executeQuery(query);
            rs.next();
            int pk = rs.getInt("pk");
            DBUtil.finishQuery();
            String update =
                    "INSERT INTO Feature(name, semanticCategoryPk) " +
                    "values ('" + string +"', "+ pk +")";
            DBUtil.executeUpdate(update);
            
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }
    
    public List<Feature> getFeatures() {
        DAOFactory factory = DAOFactory.getInstance();
        FeatureDAO dao = new FeatureDAO(factory);
        return dao.getAllFeatures(this);
    }
    
    void setPk(int aPk) {
        pk = aPk;
    }
    
    public Integer getPk() {
        return pk;
    }


    
}
