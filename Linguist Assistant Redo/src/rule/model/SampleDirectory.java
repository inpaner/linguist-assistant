package rule.model;
import java.io.File;

public class SampleDirectory {
    
     
    public static void main (String args[]) {
 
        display(new File("rule"));
    }
 
    public static void display(File node){
 
        System.out.println(node.getPath());
 
        if(node.isDirectory()){
            String[] subNote = node.list();
            for(String filename : subNote){
                display(new File(node, filename));
            }
        }
 
    }

}
