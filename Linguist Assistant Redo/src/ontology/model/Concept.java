package ontology.model;

import grammar.model.Category;
import grammar.model.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lexicon.model.Entry;
import lexicon.model.EntryDAO;
import lexicon.model.Language;
import commons.dao.DAOFactory;

public class Concept {
    private Integer pk;
    private String stem;
    private String sense;
    private String gloss;
    private List<Tag> tags;
    private Category category;
    
    private final static List<String> senseList = 
            Arrays.asList("A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T", "U", "V", "W",
                    "X", "Y", "Z", "AA", "AB", "AC", "AD");
    
    public static List<Concept> getInstances(String stemSubString, Tag tag, Category category) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        List<Concept> result;
        if (tag.equals(Tag.getTagAll())) {
            result = dao.retrieveBySubstring(stemSubString, category); 
        }
        else {
            result = dao.retrieveByTag(stemSubString, tag, category);
        }
        return result;
    }
    
    public static List<Concept> getInstances(String stemSubstring, Category category) {
        return getInstances(stemSubstring, Tag.getTagAll(), category);
    }

    public static Concept getInstance(String stem, String sense, Category category) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieve(stem, sense, category);
    }
    
    public static Concept getInstance(int pk) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieve(pk);
    }
    
    public static Concept getEmpty(Category category) {
        return new Concept(category);
    }
    
    // max of 30 senses only
    public static String getNextSense(String aSense) {
        String sense = senseList.get(0);
        if (aSense != null && !aSense.isEmpty()) {
            int index = senseList.indexOf(aSense);
            sense = senseList.get(index + 1);
        }
        return sense;
    }
    
    public Concept(Category category) {
        this.category = category;
    }
    
    public Concept(String stem, Category category) {
        this(category);
        this.stem = stem;
    }
    
    void setPk(Integer pk) {
        this.pk = pk;
    }
    public Integer getPk() {
        return pk;
    }
    
    public Category getParent() {
        return category;
    }
    
    public void setStem(String stem) {
        this.stem = stem;
    }
    
    public String getStem() {
        return stem;
    }
    
    public void setSense(String sense) {
        this.sense = sense;
    }
    
    public String getSense() {
        return this.sense;
    }
    
    public void setGloss(String gloss) {
        this.gloss = gloss;
    }
    
    public String getGloss() {
        return gloss;
    }
    
    public Category getCategory() {
        return category;
    }
    
    @Override
    public String toString() {
        return this.stem + "-" + this.sense;
    }
    
    public List<Tag> getTags() {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieveAllTags(this);
    }
    
    public List<Entry> getMappings() {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieveMappedEntries(this);
    }
    
    public List<Entry> getMappings(Language language) {
        List<Entry> result = new ArrayList<>();
        for (Entry mapping : getMappings()) {
            if (language.equals(mapping.getLanguage())) {
                result.add(mapping);
            }
        }
        
        return result;
    }
    
    public void addTag(Tag tag) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.addTag(this, tag);
    }
    
    public void deleteTag(Tag tag) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.deleteTag(this, tag);
    }
    
    public void addMapping(Entry entry) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.createMapping(this, entry);
    }
    
    public void create() {
        // TODO error handling
        if (pk != null)
            return;
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.create(this);
    }
    
    public void delete() {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.delete(this);
    }
    
    public void deleteMapping(Entry entry) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        dao.deleteMapping(this, entry);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Concept))
            return false;
        
        return toString().equals(other.toString()) ? true : false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
