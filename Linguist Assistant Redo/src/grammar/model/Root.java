package grammar.model;

import java.util.ArrayList;

public class Root {
    private ArrayList<Category> categories;
    
    
    protected Root() {
        categories = new ArrayList<>();
    }
    
    public ArrayList<Category> getConstituents() {
        return categories;
    }
    
    public void addConstituent(Category category) {
        categories.add(category);
    }


}
