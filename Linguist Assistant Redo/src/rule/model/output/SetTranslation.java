package rule.model.output;

import java.util.List;

import ontology.model.Concept;
import lexicon.model.Entry;
import lexicon.model.Language;
import semantics.model.Constituent;
import semantics.model.Target;

public class SetTranslation extends Output {
    private String key;
    private Language language;
    
    public SetTranslation(String key, Language language) {
        setKey(key);
        setLanguage(language);
    }
        

    public void setKey(String key) {
        this.key = key;
    }
    
    public void setLanguage(Language language) {
        this.language = language;
    }
    
    /**
     * Retrieves the constituent's concept and assigns a target
     * based on the concept's mapped entry. 
     */
    @Override
    public void apply() {
        Constituent constituent = root.getStored(key);
        Concept concept = constituent.getConcept();
        
        if (concept == null) 
            return;
        
        List<Entry> mappings = concept.getMappings(language);
        if (mappings.isEmpty()) {
            System.err.println("Empty mappings");
            return;
        }
        
        /*
         *  Gets the first mapping only, as program doesn't
         *  handle multiple mappings yet.
         */
        
        Entry firstMapping = mappings.get(0);
        Target target = constituent.getTarget();
        target.setEntry(firstMapping);
    }

	public String getKey() {
		return key;
	}

	public Language getLanguage() {
		return language;
	}

}
