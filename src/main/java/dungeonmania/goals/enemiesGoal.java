package dungeonmania.goals;

import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawner;

public class enemiesGoal implements goalBehavior{
    private boolean isCompleted;

    public enemiesGoal() {
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
     * @return enemiesLefted how many enemies/ZombieToastSpawner that character haven't kill yet.
     */
    public int checkCompleteState(Map<String,Entity> all_entities) {
        //go through all_entities find is there have any enemies left
        double enemies_left = all_entities.values().stream()
            .filter(e ->  MovingEntity.class.isAssignableFrom(e.getClass()))
            .map(e -> (MovingEntity) e)
            .filter(e -> e.getIsEnemy())
            .count();
        
        enemies_left += all_entities.values().stream()
            .filter(e ->  ZombieToastSpawner.class.isAssignableFrom(e.getClass()))
            .count();
        
        setIsCompleted(enemies_left == 0);
        return (int) enemies_left;
    }

    @Override
    public String toString() {
        return ":zombie_toast";
    }
}
