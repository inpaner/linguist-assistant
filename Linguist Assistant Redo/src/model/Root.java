package model;

import java.util.ArrayList;

public class Root {
    private ArrayList<Constituent> constituents;
    
    
    protected Root() {
        constituents = new ArrayList<>();
    }
    
    public ArrayList<Constituent> getConstituents() {
        return constituents;
    }
    
    public void addConstituent(Constituent constituent) {
        constituents.add(constituent);
    }
    
    public void sysout() {
        for (Constituent constituent : constituents) {
            constituent.sysout();
        }
    }

}
