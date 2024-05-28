package dungeonmania.goals;

import java.util.Map;


import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntities.Exit;

public class exitGoal implements goalBehavior{
    private boolean isCompleted;

    public exitGoal() {
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
     */
    public int checkCompleteState(Map<String,Entity> all_entities) {
        //go through all_entities find exit has been reached by character or not
        double exit_done = all_entities.values().stream()
            .filter(e ->  Exit.class.isAssignableFrom(e.getClass()))
            .map(e -> (Exit) e)
            .filter(e -> e.getCharacterExit())
            .count();
        
        setIsCompleted(exit_done > 0);
        return 1;
    }

    @Override
    public String toString() {
        return ":exit";
    }
}
