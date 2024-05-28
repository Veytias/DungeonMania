package dungeonmania.goals;

import java.util.Map;

import dungeonmania.entities.Entity;

public interface goalBehavior {
    public boolean isCompleted();
    public int checkCompleteState(Map<String,Entity> all_entities);
    public String toString();
}
