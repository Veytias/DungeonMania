package dungeonmania.goals;

import java.util.Map;

import dungeonmania.entities.Entity;

public class goalLeaf implements goalComponent{
    private boolean isCompleted;
    private int state;
    private goalBehavior goal;
    public goalLeaf(goalBehavior goal) {
        this.isCompleted = false;
        this.goal = goal;
    }

    public int getState() {
        return state;
    }
    public goalBehavior getGoal() {
        return goal;
    }
    public boolean getIsComplete() {
        return isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return this.goal.toString();
    }

    @Override
    public void checkIsCompleted() {
        isCompleted = this.goal.isCompleted();
        
    }

    @Override
    public void updateState(Map<String,Entity> all_entities) {
        state = this.goal.checkCompleteState(all_entities);
        if(this.goal.isCompleted()) {
            setCompleted(true);
        }
    }
    
}
