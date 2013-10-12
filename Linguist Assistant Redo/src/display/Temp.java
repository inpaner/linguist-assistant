package display;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Temp extends JFrame {
	private JPanel panel;
	public Temp() {
		setBounds(50,50,500,500);
		getContentPane().setLayout(null);
		
		setPanel(new JPanel());
		panel.setBounds(10, 11, 414, 240);
		getContentPane().add(panel);
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	
}
