package grammar.model;

public class Translation {
    private String name;

    protected Translation(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
