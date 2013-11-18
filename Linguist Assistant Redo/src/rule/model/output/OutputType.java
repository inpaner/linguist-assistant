package rule.model.output;

public enum OutputType {
    SET_TRANSLATION("Set Translation"),
    SET_FEATURES("Set Feature");
    
    
    String name;
    OutputType(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
