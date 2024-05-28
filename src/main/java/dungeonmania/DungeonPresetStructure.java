package dungeonmania;

import java.util.List;

public class DungeonPresetStructure {
    private List<EntityData> entities;
    private GoalCondition goalCondition;

    public List<EntityData> getEntities() {
        return this.entities;
    }

    public GoalCondition getGoalCondition() {
        return this.goalCondition;
    }

    public class EntityData {
        private int x;
        private int y;
        private String type;
        private String key;
        private String colour;
        private int movement_factor; 

        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }
        public String getType() {
            return this.type;
        }
        public String getKey() {
            return this.key;
        }
        public String getColour() {
            return this.colour;
        }
        public int getMovementFactor() {
            return this.movement_factor;
        }


    }

    public class GoalCondition {
        private Goal goal;
        private List<Goal> subGoals;

        public Goal getGoal() {
            return this.goal;
        }
        public List<Goal> getSubGoals() {
            return this.subGoals;
        }
    }

    public class Goal {
        private String goal;

        public String getGoalString() {
            return this.goal;
        }
    }
}
