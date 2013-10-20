package grammar.view;

import grammar.model.Feature;

public interface FeatureValuesListener {
    public abstract void featureValueChanged(Feature feature, String newValue);
}
