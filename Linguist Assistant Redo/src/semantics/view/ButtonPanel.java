package semantics.view;

import grammar.model.Category;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import semantics.model.Constituent;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {
	private final int ROW_WIDTH = 5;

    public ButtonPanel() {
        setLayout(new MigLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new MigLayout("wrap " + ROW_WIDTH, "fill"));
        for(Category category : Category.getAll()) {
            Constituent constituent = new Constituent();
            constituent.setCategory(category);
            innerPanel.add(new DraggableButton(constituent));
        }
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        add(scrollPane);
    }
}
