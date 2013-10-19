package view;

import grammar.model.Constituent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {
	private final int ROW_WIDTH = 5;

    public ButtonPanel() {
        setLayout(new MigLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new MigLayout("wrap " + ROW_WIDTH, "fill"));
        for(Constituent constituent : Constituent.getAllConstituents()) {
            innerPanel.add(new DraggableButton(constituent));
        }
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        add(scrollPane);
    }
}
