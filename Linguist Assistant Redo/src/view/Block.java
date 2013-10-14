package view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.Constituent;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ConstBlock extends Box {
    private static ArrayList<Color> colors;
    
    private Constituent constituent;
    private int colorIndex;
    private ArrayList<ConstBlock> children;
    private ArrayList<Box> spacers;
    private ConstBlock parent;
    private JLabel nameLabel;
    private JLabel conceptLabel;
    private Box contentBox;
    private boolean showChildren;
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        Constituent con = new Constituent(null);
        Constituent con2 = new Constituent(null);
        Constituent con3 = new Constituent(null);
        
        con.addChild(con2);
        con.addChild(con3);
        con.setLabel("Noun");
        con2.setLabel("child 1");
        con3.setLabel("child 2");
        
        panel.add(new ConstBlock(con));
        frame.setPanel(panel);
    }
    
    static {
        System.out.println("init colors");
        colors = new ArrayList<>();
        colors.add(new Color(255,0,0));
        colors.add(new Color(0,255,0));
        colors.add(new Color(0,0,255));
        colors.add(new Color(255,0,255));
        
    }
    
    public ConstBlock(Constituent constituent) {
        this(constituent, null, 0);
    }
    
    public ConstBlock(Constituent constituent, int colorIndex) {
        this(constituent, null, colorIndex);
    }
    
    public ConstBlock(final Constituent constituent, ConstBlock parent, int colorIndex) {
        super(BoxLayout.X_AXIS);
        
        children = new ArrayList<>();
        spacers = new ArrayList<>();
        this.constituent = constituent;
        this.parent = parent;
        showChildren = false;

        this.colorIndex = colorIndex % colors.size();
        Border lineEdge = BorderFactory.createLineBorder(colors.get(colorIndex));
        setBorder(lineEdge);
        
        nameLabel = new JLabel(constituent.getLabel());
        conceptLabel = new JLabel();
        if(constituent.getConcept()!=null) {
            conceptLabel.setText(constituent.getConcept().getName());
        } 
        else {
            conceptLabel.setText(" ");
        }
        
        Box textBox = new Box(BoxLayout.Y_AXIS);
        textBox.add(nameLabel);
        textBox.add(conceptLabel);
        
        contentBox = new Box(BoxLayout.X_AXIS);
        Border paneEdge = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        contentBox.setBorder(paneEdge);
        add(contentBox);
        contentBox.add(textBox);
        contentBox.add(newSpacer());
        
        addMouseListener(clickedBox());
        for (Constituent childConst : constituent.getChildren()) {
            addSubBlock(childConst, colorIndex + 1);
        }
    }
    
    private Box newSpacer() {
        Box spacer = new Box(BoxLayout.X_AXIS);
        Border space = BorderFactory.createEmptyBorder(0, 15, 15, 0);
        spacer.setBorder(space);
        spacers.add(spacer);
        return spacer;
    }
    
    private void toggleChildren() {
        for (ConstBlock child : children) {
            if (showChildren) {
                child.setVisible(true);
            } 
            else {
                child.setVisible(false);
            }
        }
        for (Box spacer : spacers) {
            if (showChildren) {
                spacer.setVisible(true);
            } 
            else {
                spacer.setVisible(false);
            }
        }
        showChildren = !showChildren;
    }
    
    public void addSubBlock(Constituent c, int colorIndex) {
           ConstBlock child = new ConstBlock(c,colorIndex+1);
           children.add(child);
           contentBox.add(child);
           contentBox.add(newSpacer());
    }
    
    private MouseListener clickedBox() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    toggleChildren();
                }        
            }

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {}
        };
    }
    
    
}
