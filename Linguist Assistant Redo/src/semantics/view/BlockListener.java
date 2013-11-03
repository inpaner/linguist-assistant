package semantics.view;

import semantics.model.Constituent;

public interface BlockListener {
    public abstract void selectedConstituent(Constituent constituent);
    public abstract void droppedBlock(Constituent dropped, Constituent destination, int index);
    public abstract void droppedButton(Constituent dropped, Constituent destination, int index);
    public abstract void tryDelete(Constituent constituent);
}
