package rule.model.output;

import semantics.model.Constituent;
import grammar.model.Category;
import grammar.model.Feature;

public class SetFeature extends Output {
    private Feature feature;
    
    public SetFeature(Category category, String name, String value) {
        this.feature = Feature.getEmpty(category);
        this.feature.setName(name);
        this.feature.setValue(value);
    }
    
    @Override
    public void apply() {
        Constituent constituent = root.getStored(key);
        constituent.updateFeature(feature, feature.getValue());
    }
    
    
}
