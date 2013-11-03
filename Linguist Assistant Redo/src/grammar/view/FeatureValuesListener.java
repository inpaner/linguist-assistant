package grammar.view;

import semantics.model.Constituent;
import grammar.model.Feature;

public interface FeatureValuesListener {
    public abstract void featureValueChanged(
            Constituent constituent, Feature feature, String newValue);
}
