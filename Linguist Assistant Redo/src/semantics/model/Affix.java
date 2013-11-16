package semantics.model;

public enum Affix {
    PREFIX("Prefix"), 
    SUFFIX("Suffix"), 
    INFIX("Infix"), 
    CIRUMFIX("Circumfix");
    
    public static Affix get(String type) {
        switch(type.toUpperCase()) {
            case "PREFIX": return PREFIX;
            case "SUFFIX": return SUFFIX;
            case "INFIX": return INFIX;
            case "CIRCUMFIX": return CIRUMFIX;
            default: return null;
        }
    }
    
    private final String name;
    Affix(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString() {
        return name;
    }
}
