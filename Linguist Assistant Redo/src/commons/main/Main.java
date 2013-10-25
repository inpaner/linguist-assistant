package commons.main;

import java.util.prefs.Preferences;


public class Main {
    private MainFrame frame;
    private static Preferences prefs; 
    private final String LANGUAGE_KEY = "language"; 
    
    public static Preferences getPrefs() {
        return prefs;
    }
    
    public Main() {
        loadPreferences();
        frame = new MainFrame();
        frame.setPanel(new MainUi(frame));
        
    }
    
    private void loadPreferences() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }
    
    public static void main(String[] args) {
        new Main();
    }
}
