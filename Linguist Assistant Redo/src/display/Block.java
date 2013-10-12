package display;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Constituent;

//TO DO:
//update y-size dynamically
//color cycling function
public class Block extends JPanel {
	Constituent con;
	private int colorIndex;
	ArrayList<Color>colors=new ArrayList<Color>();
	int xPos;
	int yPos;
	int height=100;
	Dimension size;
	private Color bgColor;
	JLabel lblLabel;
	public Block(Constituent con, int x, int y, int colorIndex) {
		//this.height=height;
		Box box = new Box(BoxLayout.X_AXIS);
		this.setColorIndex(colorIndex);
		this.setOpaque(false);
		fillColorList();
		setBgColor(colors.get(colorIndex));
		//this.setBackground(getBgColor());
		setBorder(BorderFactory.createLineBorder(bgColor));
		size=new Dimension(100,100);
		this.yPos=y;
		this.xPos=x;
		setBounds(xPos,yPos, 100, 100 );
		setPreferredSize(size);
		setVisible(true);
		setLayout(null);
		this.con=con;
		lblLabel = new JLabel("Label");
		lblLabel.setBounds(10, 42, 100, 14);
		add(lblLabel);
		
		JLabel lblConcept = new JLabel("No Concept");
		lblConcept.setBounds(10, 22, 100, 14);
		add(lblConcept);
		
		
		lblLabel.setText(con.getLabel());  //get label and concept from constituent
		if(con.getConcept()!=null)
			lblConcept.setText(con.getConcept().getName());
		
		//create list of blocks, use index to display them in order
		y=0;
		for(Constituent c : con.getChildren())
		{
			
			y+=30;
			addSubBlock(c, 10,y);
			//xPos+=200;
		}
		lblLabel.setVisible(true);
		lblConcept.setVisible(true);
		

	}
	
	public void addSubBlock(Constituent con, int x, int y)
	{
		Block subCon = new Block(con,x,y,getColorIndex()+1);
		
		//subCon.setBgColor(new Color(0,255,255));
		//subCon.setBounds(x, y, 150, 300);
		//subCon.setPreferredSize(new Dimension(164,70));
		height+=100;
		//height+=subCon.getHeight();
		setBounds(xPos, yPos, 100, height);
		add(subCon);
		System.out.println("Subblock added "+subCon.getColorIndex()+" "+subCon.getLabel());
		
		//subCon.setVisible(true);
		
	}
	
	
	public Constituent getConstituent()
	{
		return con;
	}
	public void setConstituent(Constituent con)
	{
		this.con=con;
	}

	Color getBgColor() {
		return bgColor;
	}

	void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
	
	protected void fillColorList()
	{
		colors.add(new Color(255,0,0));
		colors.add(new Color(0,255,0));
		colors.add(new Color(0,0,255));
		colors.add(new Color(255,0,255));
	}

	public int getColorIndex() {
		return colorIndex;
	}

	void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	public String getLabel()
	{
		return lblLabel.getText();
	
	}
}
