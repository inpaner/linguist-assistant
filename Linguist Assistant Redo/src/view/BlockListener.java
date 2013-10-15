package view;

import model.Constituent;

public interface BlockListener {
    public abstract void selectedConstituent(Constituent constituent);
    public abstract void droppedConstituent(Constituent source, Constituent destination, int index);
}
