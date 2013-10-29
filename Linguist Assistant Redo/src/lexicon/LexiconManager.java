package lexicon;

import lexicon.view.LexiconList;
import commons.main.MainFrame;

public class LexiconManager {
    private static LexiconList panel;

    public static void run(MainFrame frame) {
        if (panel == null) {
            panel = new LexiconList(frame);
        }
        
        frame.setPanel(panel);
    }

}
