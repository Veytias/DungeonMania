package dungeonmania.goals;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.entities.Entity;


public class goal{
    private boolean isCompleted;
    private ArrayList<goalComponent> goals = new ArrayList<goalComponent>();
    private Map<String,Entity> all_entities;
    public goal(JSONObject goalJSON, Map<String,Entity> all_entities) {
        String type = goalJSON.getString("goal");
        this.all_entities = all_entities;
        addGoal(goalJSON, type);
    }

    /**
     * add goal into goals
     * @param goalJSON
     * @param type
     * @return goal or goal_Composite depends on the goal type
     */
    public goalComponent addGoal(JSONObject goalJSON, String type) {
        if(!type.equals("AND")  && !type.equals("OR")) {     
            goalLeaf goal = creategoalLeaf(type);
            goals.add(goal);
            return goal;
        }
        else if(type.equals("AND") || type.equals("OR")){
            goalComposite goal_Composite = new goalComposite(type);
            JSONArray subgoals = goalJSON.getJSONArray("subgoals");
            for (int i = 0; i < subgoals.length(); i++) {
                JSONObject subgoal = subgoals.getJSONObject(i);
                String sub_type = subgoal.getString("goal");
                if(sub_type.equals("AND") || sub_type.equals("OR")) {
                    goalComponent sub_goal_Composite = addGoal(subgoal, sub_type);
                    goal_Composite.addGoals(sub_goal_Composite);
                }
                else if(!sub_type.equals("AND") && !sub_type.equals("OR")){
                    goalLeaf goal = creategoalLeaf(sub_type);
                    goal_Composite.addGoals(goal);
                }
            }
            goals.add(goal_Composite);
            return goal_Composite;
        }
        return null;
    }

    public goalLeaf creategoalLeaf(String type){
        goalBehavior goal;
        goalLeaf goal_leaf;
        switch(type) {
            case "exit":
                goal = new exitGoal();
                goal_leaf = new goalLeaf(goal);
                return goal_leaf;
            case "treasure":
                goal = new treasureGoal();
                goal_leaf = new goalLeaf(goal);
                return goal_leaf;
            case "enemies":
                goal = new enemiesGoal();
                goal_leaf = new goalLeaf(goal);
                return goal_leaf;
            case "boulders":
                goal = new bouldersGoal();
                goal_leaf = new goalLeaf(goal);
                return goal_leaf;
        }
        return null;
    }
    public boolean getIsComplete(){
        return isCompleted;
    }

    public List<goalComponent> getGoalsList() {
        return goals;
    }
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    public String toString() {
        String result = "";
        for (goalComponent goal: goals) {
            //System.out.println(goals);
            result = goal.toString();
        }
        return result;
    }

    public void updateIsComplete() {
        List<goalComponent> toRemove = new ArrayList<goalComponent>();
        for(goalComponent goal:goals) {
            goal.updateState(all_entities);
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
