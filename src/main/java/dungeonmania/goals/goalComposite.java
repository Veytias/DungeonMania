package dungeonmania.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import dungeonmania.entities.Entity;

public class goalComposite implements goalComponent{
    private List<goalComponent> goals;
    private String type;
    private boolean isCompleted;

    public goalComposite(String type) {
        this.type = type;
        goals = new ArrayList<goalComponent>();
        this.isCompleted = false;
    }

    public boolean getIsComplete() {
        return this.isCompleted;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public void addGoals(goalComponent goal) {
        goals.add(goal);
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String result = "(";

        for(goalComponent goal: goals) {
            if (this.getType().equals("AND")){
                result += goal.toString();
                if (goals.indexOf(goal) != goals.size() - 1) {
                    result += " AND ";
                }
            }
            else if(this.getType().equals("OR")) {
                result += goal.toString();
                if (goals.indexOf(goal) != goals.size() - 1) {
                    result += "/";
                }
            }
        }
        result += ")";
        return result;
    }

    @Override
    public void checkIsCompleted() {
        boolean prev_state = false;
        boolean curr_state = false;
        for(goalComponent goal: goals) {
            prev_state = curr_state;
            if(goal instanceof goalLeaf){
                ((goalLeaf)goal).checkIsCompleted();
                curr_state = goal.getIsComplete();
            }
            else if(goal instanceof goalComposite) {
                goal.checkIsCompleted();
            }
            if (this.getType().equals("AND")) {
                this.setCompleted(curr_state && prev_state);
            }
            else if (this.getType().equals("OR")) {
                this.setCompleted(curr_state || prev_state);
            }
        }
    }

    @Override
    public void updateState(Map<String, Entity> all_entities) {
        List<goalComponent> toRemove = new ArrayList<goalComponent>();
        for(goalComponent goal: goals) {
            goal.updateState(all_entities);
            checkIsCompleted();
            if(goal.getIsComplete() == true) {
                toRemove.add(goal);
            }
        }
        goals.removeAll(toRemove);
        if(goals.size() == 0) {
            setCompleted(true);
        }
    }
}