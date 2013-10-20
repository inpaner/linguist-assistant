package grammar.view;

import grammar.model.Constituent;

public interface AddConstituentListener {
    public abstract void clickedOk(Constituent constituent);
    public abstract void clickedCancel();
}
