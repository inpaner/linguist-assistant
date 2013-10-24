package commons.main;


public class Main {
    MainFrame frame;
    
    public Main() {
        frame = new MainFrame();
        frame.setPanel(new MainUi());
    }
    
    public static void main(String[] args) {
        new Main();
    }
}
