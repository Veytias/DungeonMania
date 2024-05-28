package dungeonmania.entities.interfaces;

public interface Subject {
    public void attach(Observer obs);
    public void detach(Observer obs);
    public void notify(int state);
}
