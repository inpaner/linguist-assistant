package rule.view;

import grammar.model.Feature;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * Derived from:
 * http://www.coderanch.com/t/343795/GUI/java/Check-Box-JTable-header
 */
@SuppressWarnings("serial")
public class FeatureButton extends JButton  
        implements TableCellRenderer, MouseListener {  
    protected FeatureButton rendererComponent;
    protected String title;
    protected int column;  
    protected int row;
    protected boolean mousePressed = false;
    private List<List<Feature>> featuresList;
    
    public FeatureButton(ActionListener listener) {
        title = "";
        rendererComponent = this;  
        rendererComponent.addActionListener(listener);  
    }  
    
    public Component getTableCellRendererComponent(  
            JTable table, Object value,  
            boolean isSelected, boolean hasFocus, int row, int column) {  
        
        if (table != null) {  
            JTableHeader header = table.getTableHeader();  
            if (header != null) {  
                rendererComponent.setForeground(header.getForeground());  
                rendererComponent.setBackground(header.getBackground());  
                rendererComponent.setFont(header.getFont());  
                header.addMouseListener(rendererComponent);  
            }  
        }
        setRow(row);
        setColumn(column); 
        rendererComponent.setText(title);  
        return rendererComponent;  
    }  

    protected void setColumn(int column) {  
        this.column = column;  
    }  

    public int getColumn() {  
        return column;  
    }  
    
    protected void setRow(int row) {
        this.row = row;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public List<List<Feature>> getFeaturesList() {
        return featuresList;
    }
    
    public void setFeaturesList(List<List<Feature>> list) {
        featuresList = list;
    }
    
    protected void handleClickEvent(MouseEvent e) {  
        if (mousePressed) {  
            mousePressed = false;  
            JTableHeader header = (JTableHeader)(e.getSource());  
            JTable tableView = header.getTable();  
            TableColumnModel columnModel = tableView.getColumnModel();  
            int viewColumn = columnModel.getColumnIndexAtX(e.getX());  
            int column = tableView.convertColumnIndexToModel(viewColumn);  

            if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {  
                doClick();  
            }  
        }  
    }  

    public void mouseClicked(MouseEvent e) {  
        handleClickEvent(e);  
        ((JTableHeader) e.getSource()).repaint();  
    }  

    public void mousePressed(MouseEvent e) {  
        mousePressed = true;  
    }  

    public void mouseReleased(MouseEvent e) {  
    }  

    public void mouseEntered(MouseEvent e) {  
    }  

    public void mouseExited(MouseEvent e) {  
    }  
} 

