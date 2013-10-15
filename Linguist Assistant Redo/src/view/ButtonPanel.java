package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Constituent;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {
	//List<Constituent> defaultCons;
    private final int ROW_WIDTH = 5;

    public ButtonPanel() {
        // listeners = new ArrayList<>();
        setLayout(new MigLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new MigLayout("wrap " + ROW_WIDTH, "fill"));
        for(Constituent constituent : Constituent.getAllConstituents()) {
            innerPanel.add(new DraggableButton(constituent));
        }
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        add(scrollPane);
    }
    
    public static void main(String[] args) {
        System.out.println("Started");
        MainFrame frame = new MainFrame();
        ButtonPanel panel = new ButtonPanel();
        frame.setPanel(panel);
    }
}
