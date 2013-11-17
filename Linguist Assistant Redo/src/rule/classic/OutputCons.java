package rule.classic;

import java.util.ArrayList;
import java.util.List;

import rule.model.output.Output;

public class OutputCons {
    String key = "root";
    
    boolean toAdd = false;
    boolean toDelete = false;
    List<Output> outputs = new ArrayList<>();
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public boolean isToAdd() {
        return toAdd;
    }
    public void setToAdd(boolean toAdd) {
        this.toAdd = toAdd;
    }
    public boolean isToDelete() {
        return toDelete;
    }
    public void setToDelete(boolean toDelete) {
        this.toDelete = toDelete;
    }
    public List<Output> getOutputs() {
        return outputs;
    }
    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }
    
    
}
