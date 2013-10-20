package ontology.model;

import grammar.model.Constituent;
import grammar.model.Node;

import java.util.Arrays;
import java.util.List;

import commons.dao.DAOFactory;

public class Concept extends Node {
    private Integer pk;
    private String fStem;
    private String fSense;
    private String fGloss;
    private Constituent fConstituent;
    
    private final static List<String> senseList = 
            Arrays.asList("A", "B", "C", "D", "E", "F", "G",
                    "H", "I", "J", "K", "L", "M", "N", "O",
                    "P", "Q", "R", "S", "T", "U", "V", "W",
                    "X", "Y", "Z", "AA", "AB", "AC", "AD");
    
    public static Concept getInstance(String stem, String gloss, Constituent constituent) {
        DAOFactory factory = DAOFactory.getInstance();
        ConceptDAO dao = new ConceptDAO(factory);
        return dao.retrieve(stem, gloss, constituent);
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
    
    public Concept(Constituent aConstituent) {
        this.fConstituent = aConstituent;
        fLevel = aConstituent.getLevel() + 1;
    }
    
    public Concept(String aStem, Constituent aConstituent) {
        this(aConstituent);
        this.fStem = aStem;
    }
    
    void setPk(Integer pk) {
        this.pk = pk;
    }
    public Integer getPk() {
        return pk;
    }
    
    public Constituent getParent() {
        return fConstituent;
    }
    
    public void setStem(String aStem) {
        fStem = aStem;
    }
    
    public String getStem() {
        return fStem;
    }
    
    public void setGloss(String aGloss) {
        fGloss = aGloss;
    }
    
    public String getGloss() {
        return fGloss;
    }
    
    @Override
    public String toString() {
        return fStem;
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
