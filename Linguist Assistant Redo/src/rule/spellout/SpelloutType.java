package rule.spellout;

public enum SpelloutType {
    SIMPLE, TABLE, MORPHOPHONEMIC, FORM, PHRASE;
    
    public static SpelloutType get(String type) {
        switch(type.toLowerCase()) {
            case "simple": return SIMPLE;
            case "table": return TABLE;
            case "morphophonemic": return MORPHOPHONEMIC;
            case "form": return FORM;
            case "phrase": return PHRASE;
            default: return null;
        }
    }
}
