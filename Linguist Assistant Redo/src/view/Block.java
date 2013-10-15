package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
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

//TODO drag, drop, selection border
//TODO don't show selection if can't drag onto

@SuppressWarnings("serial")
public class Block extends Box {
    private static ArrayList<Color> colors;
    private static DataFlavor blockFlavor;    
    private Constituent constituent;
    private int colorIndex;
    private ArrayList<Block> children;
    private ArrayList<Box> spacers;
    private Block parent;
    private JLabel nameLabel;
    private JLabel conceptLabel;
    private Box contentBox;
    private boolean showChildren;
    private ArrayList<BlockListener> listeners;
    
    static {
        try { // unsure why needed since Block.class is this
            blockFlavor = new DataFlavor(
                    DataFlavor.javaJVMLocalObjectMimeType + 
                    ";class=\"" + Block.class.getName() + "\"");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        colors = new ArrayList<>();
        colors.add(new Color(255,0,0));
        colors.add(new Color(0,255,0));
        colors.add(new Color(0,0,255));
        colors.add(new Color(255,0,255));
    }
    
    static DataFlavor getFlavor() {
        return blockFlavor;
    }
    
    public Block(Constituent constituent) {
        this(constituent, null, 0);
    }
    
    public Block(Constituent constituent, int colorIndex) {
        this(constituent, null, colorIndex);
    }
    
    protected void transferListeners(Block transferFrom) {
        listeners = transferFrom.listeners;
        for (Block child : children) {
            child.transferListeners(transferFrom);
        }
    }
    
    public Block(final Constituent constituent, Block parent, int colorIndex) {
        super(BoxLayout.X_AXIS);

        children = new ArrayList<>();
        spacers = new ArrayList<>();
        listeners = new ArrayList<>();
        this.constituent = constituent;
        this.parent = parent;
        showChildren = false;

        colorIndex = colorIndex % colors.size();
        Border lineEdge = BorderFactory.createLineBorder(colors.get(colorIndex));
        setBorder(lineEdge);
        
        nameLabel = new JLabel(constituent.getLabel());
        conceptLabel = new JLabel();
        if(constituent.getConcept() != null) {
            conceptLabel.setText(constituent.getConcept().getName());
        } 
        else {
            conceptLabel.setText(" w i w");
        }
        
        JPanel textPanel = new JPanel();
        //textPanel.setLayout(new MigLayout());
        textPanel.add(nameLabel);
        textPanel.add(conceptLabel);
        
        Box textBox = Box.createVerticalBox();
        textBox.add(nameLabel);
        textBox.add(conceptLabel);
        
        contentBox = Box.createHorizontalBox();
        Border paneEdge = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        contentBox.setBorder(paneEdge);
        contentBox.add(textBox);
        contentBox.add(newSpacer());
        add(contentBox);
        
        addMouseListener(new ClickedBox());
        for (Constituent childConst : constituent.getChildren()) {
            addSubBlock(childConst, colorIndex + 1);
        }
        
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(
                this, DnDConstants.ACTION_COPY, new DragGestureListenerImp());
    }
    
    // TODO make Spacer its own class
    private Box newSpacer() {
        Box spacer = Box.createHorizontalBox();
        spacer.add(Box.createRigidArea(new Dimension(10, 30)));
        Border space = BorderFactory.createEmptyBorder(10, 5, 10, 5);
        //spacer.setBorder(space);
        spacers.add(spacer);
        spacer.setOpaque(true);
        new SpacerDropListener(spacer);
        return spacer;
    }
    
    private void toggleChildren() {
        for (Block child : children) {
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
           Block child = new Block(c,colorIndex+1);
           children.add(child);
           contentBox.add(child);
           contentBox.add(newSpacer());
    }
    
    public void addBlockListener(BlockListener listener) {
        listeners.add(listener);
        for (Block child : children) {
            child.addBlockListener(listener);
        }
    }
    
    private class ClickedBox implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) {
                for (BlockListener listener : listeners) {
                    listener.selectedConstituent(constituent);
                }
            }
            if (e.getClickCount() == 2) {
                toggleChildren();
            }        
        }

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}
    }
    
    private class TransferableBlock implements Transferable {
        private Block block;
        public TransferableBlock(Block block) {
            this.block = block;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] {blockFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(blockFlavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {

            if (flavor.equals(blockFlavor))
                return block;
            else
                throw new UnsupportedFlavorException(flavor);
        }
    }

    private class DragGestureListenerImp implements DragGestureListener {
        @Override
        public void dragGestureRecognized(DragGestureEvent event) {
            Cursor cursor = null;
            Block source = (Block) event.getComponent();

            if (event.getDragAction() == DnDConstants.ACTION_COPY) {
                cursor = DragSource.DefaultCopyDrop;
            }
            event.startDrag(cursor, new TransferableBlock(source));
        }
    }
    
    private class SpacerDropListener extends DropTargetAdapter 
            implements DropTargetListener {
    
        private Box spacer;
        
        public SpacerDropListener(Box spacer) {
            this.spacer = spacer;
            new DropTarget(spacer, DnDConstants.ACTION_COPY, this, true, null);
        }
        
        public void drop(DropTargetDropEvent event) {
            try {
                Transferable tr = event.getTransferable();
                int index = spacers.indexOf(spacer);
                
                // dragged a Block
                if (event.isDataFlavorSupported(blockFlavor)) { 
                    Block source = (Block) tr.getTransferData(blockFlavor);
                    if (!Block.this.equals(source)) {
                        spacer.setBackground(null);
                        for (BlockListener listener : listeners) {
                            listener.droppedConstituent(source.constituent, constituent, index);
                        }
                        event.acceptDrop(DnDConstants.ACTION_COPY);
                    }
                    else {
                        event.rejectDrop();
                    }
                }
                // dragged a Button
                else if (event.isDataFlavorSupported(DraggableButton.getFlavor())) { 
                    System.out.println("Fin");
                }
                
                spacer.setBackground(null);
            } 
            catch (Exception e) {
                e.printStackTrace();
                event.rejectDrop();
            }
        }
        
        public void dragOver(DropTargetDragEvent event) {
            try {
                Transferable tr = event.getTransferable();
                if (event.isDataFlavorSupported(blockFlavor)) { // dragged a block
                    Block source = (Block) tr.getTransferData(blockFlavor);
                    if (Block.this.equals(source)) {
                        System.out.println("Hover self");
                    }
                    else {
                     // System.out.println("Hover other");
                        spacer.setBackground(Color.BLACK);    
                    }
                }
                else if (event.isDataFlavorSupported(DraggableButton.getFlavor())) { // dragged a Button
                    spacer.setBackground(Color.BLACK);
                    System.out.println("Dragged a button right here");
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        @Override
        public void dragExit(DropTargetEvent event) {
            spacer.setBackground(null);   
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Block))
            return false;
        
        Block otherBlock = (Block) other;
        return constituent.equals(otherBlock.constituent);
    }
}
