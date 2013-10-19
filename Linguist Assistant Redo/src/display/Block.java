package display;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import uitextgen.UITEXTGEN;

import model.Constituent;
import model.Feature;

//TO DO:
//update y-size dynamically
//color cycling function
public class Block extends JPanel {
	Constituent con;
	private int colorIndex;
	ArrayList<Color>colors=new ArrayList<Color>();
	ArrayList<Block> subBlocks=new ArrayList<Block>();
	int xPos;
	int yPos;
	UITEXTGEN ui;
	int height=50;
	Dimension size;
	private Color bgColor;
	JLabel lblLabel;
	public MouseListener listener;
	TableModel featureModel;
	int nextY;
	//Block selected;
	Boolean selected=false;
	public Block(Constituent con, int x, int y, int colorIndex, UITEXTGEN ui) {
		
		//this.height=height;
		this.ui=ui;

		this.setColorIndex(colorIndex);
		this.setOpaque(false);
		fillColorList();
		setBgColor(colors.get(colorIndex));
		//this.setBackground(getBgColor());
		setBorder(BorderFactory.createLineBorder(bgColor));
		size=new Dimension(100,50);
		this.yPos=y;
		this.xPos=x;
		setBounds(xPos,yPos, 100, 50 );
		setPreferredSize(size);
		setVisible(true);
		setLayout(null);
		this.con=con;
		lblLabel = new JLabel("Label");
		lblLabel.setBounds(10, 29, 100, 14);
		add(lblLabel);
		
		JLabel lblConcept = new JLabel("No Concept");
		lblConcept.setBounds(10, 11, 100, 14);
		add(lblConcept);
		
		
		lblLabel.setText(con.getLabel());  //get label and concept from constituent
		if(con.getConcept()!=null)
			lblConcept.setText(con.getConcept().getName());
		
		//create list of blocks, use index to display them in order
		nextY=50;
		for(Constituent c : con.getChildren())
		{
			
			//y+=30;
			addSubBlock(c, 10,nextY);
			//xPos+=200;
		}
		lblLabel.setVisible(true);
		lblConcept.setVisible(true);
		populateFeatureModel();
		final UIBridge uibridge=new UIBridge(ui);
		listener=new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println(getLabel());
				uibridge.getUI().setTableModel(getFeatureModel());
				uibridge.getUI().pnlSemRep.validate();
				
				
	
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		this.addMouseListener(listener);
		

	}
	public void populateFeatureModel()
	{
		int i=2;
		
		featureModel=new DefaultTableModel(new String[][] {
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        		{null, null},
        	},
        	new String[] {
        		"Feature", "Value"
        	});
		featureModel.setValueAt("Concept", 0, 0);
		featureModel.setValueAt(con.getConcept(),0,1);
		featureModel.setValueAt("Label", 1, 0);
		featureModel.setValueAt(con.getLabel(),1,1);
		for(Feature f:con.getFeatures())
		{
			featureModel.setValueAt(f.getName(), i, 0);
			featureModel.setValueAt(f.getValue(), i, 1);
			i++;
		}
		
	}
	public void addSubBlock(Constituent con, int x, int y)
	{
		Block subCon = new Block(con,x,y,getColorIndex()+1,ui);
		
		//subCon.setBounds(x, y, 150, 300);
		//subCon.setPreferredSize(new Dimension(164,70));
		height+=(subCon.getHeight()+10);
		
		nextY+=subCon.getHeight()+10;
		
		setBounds(xPos, yPos, 100, height); //change height of parent block
		
		subBlocks.add(subCon); //this may not be necessary
		add(subCon);
		System.out.println("Subblock added "+subCon.getColorIndex()+" "+subCon.getLabel());
		
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
	public TableModel getFeatureModel()
	{
		return featureModel;
	
	}
}
