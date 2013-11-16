package rule.spellout;

public enum ModType {
    PREFIX("Prefix"), 
    SUFFIX("Suffix"), 
    INFIX("Infix"), 
    CIRUMFIX("Circumfix"), 
    NEW_TRANSLATION("New Translation"), 
    ADD_WORD("Add Word");
    
    public static ModType get(String type) {
        switch(type.toUpperCase()) {
            case "PREFIX": return PREFIX;
            case "SUFFIX": return SUFFIX;
            case "INFIX": return INFIX;
            case "CIRCUMFIX": return CIRUMFIX;
            case "NEW TRANSLATION":
            case "NEW_TRANSLATION": return NEW_TRANSLATION;
            case "ADD WORD":
            case "ADD_WORD": return ADD_WORD;
            default: return null;
        }
    }
    
    private final String name;
    ModType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
