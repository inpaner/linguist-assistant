package rule.spellout;

public enum SpelloutType {
    SIMPLE("Simple"), 
    TABLE("Table"), 
    MORPHOPHONEMIC("Morphophonemic"), 
    FORM("Form"), 
    PHRASE("Phrase");
    
    public static SpelloutType get(String type) {
        switch(type.toUpperCase()) {
            case "SIMPLE": return SIMPLE;
            case "TABLE": return TABLE;
            case "MORPHOPHONEMIC": return MORPHOPHONEMIC;
            case "FORM": return FORM;
            case "PHRASE": return PHRASE;
            default: return null;
        }
    }
    
    private final String name;
    SpelloutType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
