package dungeonmania.goals;

import java.util.Map;

import dungeonmania.entities.Entity;

public interface goalComponent {
    public void checkIsCompleted();
    public boolean getIsComplete();
    public void updateState(Map<String,Entity> all_entities);
    public String toString();
}
