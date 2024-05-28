package dungeonmania.goals;

import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Treasure;

public class treasureGoal implements goalBehavior{
    private boolean isCompleted;

    public treasureGoal() {
        this.isCompleted = false;
    }


    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(boolean is_completed) {
        this.isCompleted = is_completed;
    }

    /**
     * 
     * @param all_entities
     * @return treasuresLefted how many treasures character haven't collect yet.
     */
    public int checkCompleteState(Map<String,Entity> all_entities) {
        //go through all_entities find is there have any treasures left
        double treasure_left = all_entities.values().stream()
            .filter(e -> Treasure.class.isAssignableFrom(e.getClass()))
            .count();
        
        setIsCompleted(treasure_left == 0);
        return (int) treasure_left;
    }
    @Override
    public String toString() {
        return ":treasure";
    }

}
