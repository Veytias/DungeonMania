package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.goals.goal;

public class GoalTest {
    @Test
    public void testExitGoal() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);

        Exit exit = new Exit(new Position(1, 1), "exit");
        all_entities.put("exit", exit);

        JSONObject goal_condition = new JSONObject();
        goal_condition.put("goal", "exit");
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goal_condition);

        goal goal = new goal(goal_condition, all_entities);
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        
        //System.out.println(goal.toString());
        assertTrue(goal.toString().equals(":exit"));
        player.setPosition(exit.getPosition());
        exit.setCharacterExit(true);
        assertTrue(exit.getCharacterExit());
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        assertTrue(goal.toString().equals(""));

    }
    @Test
    public void testTreasuresGoal() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);

        Treasure gold = new Treasure(new Position(1, 1), "gold");
        all_entities.put("gold", gold);

        JSONObject goal_condition = new JSONObject();
        goal_condition.put("goal", "treasure");
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goal_condition);

        goal goal = new goal(goal_condition, all_entities);
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        //System.out.println(goal.toString());
        assertTrue(goal.toString().equals(":treasure"));

        player.addItem(gold);
        all_entities.remove("gold");
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        assert(goal.toString() == "");
    }
    @Test
    public void testenemiesGoal() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);

        Spider spider = new Spider(new Position(2, 2), "spider","");
        all_entities.put("spider", spider);

        JSONObject goal_condition = new JSONObject();
        goal_condition.put("goal", "enemies");
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goal_condition);

        goal goal = new goal(goal_condition, all_entities);
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        //System.out.println(goal.toString());
        assertTrue(goal.toString().equals(":zombie_toast"));

        all_entities.remove("spider");
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        assert(goal.toString() == "");
    }

    @Test
    public void testbouldersGoal() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);

        FloorSwitch switch1 = new FloorSwitch(new Position(2, 2), "switch1");
        all_entities.put("switch1", switch1);

        JSONObject goal_condition = new JSONObject();
        goal_condition.put("goal", "boulders");
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goal_condition);

        goal goal = new goal(goal_condition, all_entities);
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        //System.out.println(goal.toString());
        assertTrue(goal.toString().equals(":boulder"));

        switch1.setIsTriggered(true);
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        assert(goal.toString() == "");
    }

    @Test
    public void testAndGoal() {
        //create player
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);
        //create floorswitch
        FloorSwitch switch1 = new FloorSwitch(new Position(2, 2), "switch1");
        all_entities.put("switch1", switch1);
        //create exit
        Exit exit = new Exit(new Position(2, 2), "exit");
        all_entities.put("exit", exit);
        //add goal to json
        JSONObject subGoal1 = new JSONObject();
        subGoal1.put("goal", "boulders");
        JSONObject subGoal2 = new JSONObject();
        subGoal2.put("goal", "exit");
        JSONArray subgoals = new JSONArray();
        subgoals.put(subGoal1);
        subgoals.put(subGoal2);
        JSONObject goals = new JSONObject();
        goals.put("goal", "AND");
        goals.put("subgoals",subgoals);
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goals); 
        System.out.println(goals);
        //set goal
        goal goal = new goal(goals, all_entities);
        //System.out.println(g.toString());
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        System.out.println(goal.toString());
        assertTrue(goal.toString().equals("(:boulder AND :exit)"));

        //finsh goals
        switch1.setIsTriggered(true);
        player.setPosition(exit.getPosition());
        exit.setCharacterExit(true);
        assertTrue(exit.getCharacterExit());
        //update goal
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        assert(goal.toString() == "");
    }
    @Test
    public void testAndGoal2() {
        //create player
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);
        //create floorswitch
        FloorSwitch switch1 = new FloorSwitch(new Position(2, 2), "switch1");
        all_entities.put("switch1", switch1);
        //create exit
        Exit exit = new Exit(new Position(2, 2), "exit");
        all_entities.put("exit", exit);
        //add goal to json
        JSONObject subGoal1 = new JSONObject();
        subGoal1.put("goal", "enemies");
        JSONObject subGoal2 = new JSONObject();
        subGoal2.put("goal", "treasure");
        JSONArray subgoals = new JSONArray();
        subgoals.put(subGoal1);
        subgoals.put(subGoal2);
        JSONObject goals = new JSONObject();
        goals.put("goal", "AND");
        goals.put("subgoals",subgoals);
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goals); 
        System.out.println(goals);
        //set goal
        goal goal = new goal(goals, all_entities);
        //System.out.println(g.toString());
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        //System.out.println(goal.toString());
        assertTrue(goal.toString().equals("(:zombie_toast AND :treasure)"));

        //finsh goals
        switch1.setIsTriggered(true);
        player.setPosition(exit.getPosition());
        exit.setCharacterExit(true);
        assertTrue(exit.getCharacterExit());
        //update goal
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        assert(goal.toString() == "");
    }
    @Test
    public void testOrGoal() {
        //create player
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);
        //create treasure
        Treasure gold = new Treasure(new Position(1, 1), "gold");
        all_entities.put("gold", gold);
        //create spider
        Spider spider = new Spider(new Position(2, 2), "spider","");
        all_entities.put("spider", spider);
    
        //create json file
        JSONObject subGoal1 = new JSONObject();
        subGoal1.put("goal", "enemies");
        JSONObject subGoal2 = new JSONObject();
        subGoal2.put("goal", "treasure");
        JSONArray subgoals = new JSONArray();
        subgoals.put(subGoal1);
        subgoals.put(subGoal2);
        JSONObject goals = new JSONObject();
        goals.put("goal", "OR");
        goals.put("subgoals",subgoals);
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goals); 
        
        //create goal
        goal goal = new goal(goals, all_entities);
        //System.out.println(goal.toString());
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),1);
        //System.out.println(goal.toString());
        assertTrue(goal.toString().equals("(:zombie_toast/:treasure)"));

        //finish one task
       /*player.addItem(gold);
        all_entities.remove("gold");*/

        all_entities.remove("spider");
        goal.updateIsComplete();
        //update goals
        assertEquals(goal.getGoalsList().size(),0);
        assert(goal.toString() == "");
    }

    @Test
    public void testComplexGoal1() {
        //create player
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "player", "");
        all_entities.put("player", player);
        //create treasure
        Treasure gold = new Treasure(new Position(1, 1), "gold");
        all_entities.put("gold", gold);
        //create spider
        Spider spider = new Spider(new Position(2, 2), "spider","");
        all_entities.put("spider", spider);
        //create exit
        Exit exit = new Exit(new Position(2, 2), "exit");
        all_entities.put("exit", exit);

        //create json file
        JSONObject subGoal1 = new JSONObject();
        subGoal1.put("goal", "enemies");
        JSONObject subGoal2 = new JSONObject();
        subGoal2.put("goal", "treasure");
        JSONArray subgoals = new JSONArray();
        subgoals.put(subGoal1);
        subgoals.put(subGoal2);
        JSONObject goals = new JSONObject();
        goals.put("goal", "AND");
        goals.put("subgoals",subgoals);
        JSONObject goalAnd = new JSONObject();
        goalAnd.put("goal", "AND");
        JSONArray subgoals2 = new JSONArray();
        JSONObject subGoal3 = new JSONObject();
        subGoal3.put("goal", "exit");
        subgoals2.put(subGoal3);
        subgoals2.put(goals);
        goalAnd.put("subgoals",subgoals2);
        System.out.println(goalAnd);
        
        JSONObject inputFile = new JSONObject();
        inputFile.put("goal-condition",goalAnd); 
        
        //create goal
        goal goal = new goal(goalAnd, all_entities);
        
        assertFalse(goal.getIsComplete());
        assertEquals(goal.getGoalsList().size(),2);
        System.out.println(goal.toString());

        assertTrue(goal.toString().equals("(:exit AND (:zombie_toast AND :treasure))"));

        //finish tasks
        player.addItem(gold);
        all_entities.remove("gold");
        all_entities.remove("spider");
        player.setPosition(exit.getPosition());
        exit.setCharacterExit(true);
        //update goals
        goal.updateIsComplete();
        assertEquals(goal.getGoalsList().size(),0);
        System.out.println(goal.toString());
        assert(goal.toString() == "");
    }

}
