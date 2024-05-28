package dungeonmania.goals;

import java.util.Map;


import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntities.FloorSwitch;

public class bouldersGoal implements goalBehavior{
    private boolean isCompleted;

    public bouldersGoal() {
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
        //go through all_entities find is there have any floorswitches haven't been triggered by boulder
        double switches_left = all_entities.values().stream()
            .filter(e -> FloorSwitch.class.isAssignableFrom(e.getClass()))
            .map(e -> (FloorSwitch) e)
            .filter(e -> !e.getIsTriggered())
            .count();
        
        setIsCompleted(switches_left == 0);
        return (int) switches_left;
    }
    @Override
    public String toString() {
        return ":boulder";
    }
}
