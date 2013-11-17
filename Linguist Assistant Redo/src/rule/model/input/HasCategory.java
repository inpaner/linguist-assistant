package rule.model.input;

import semantics.model.Constituent;
import grammar.model.Category;

public class HasCategory extends Input {
    private Category category;
    
    public HasCategory(Category category) {
        setCategory(category);
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public Category getCategory(){
    	return category;
    }

    @Override
    public boolean evaluate(Constituent constituent) {
        boolean result = false;
        
        if (category.equals(constituent.getCategory())) {
            result = true;
        }    
    
        return result;
    }
    
    
}
