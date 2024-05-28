package dungeonmania.entities.CollectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;


public class CollectableEntity extends Entity{
    
    protected boolean is_obstacle;

    public CollectableEntity(Position position, String entity_id, boolean is_obstacle, String json_prefix) {
        super(position, entity_id, json_prefix);
        this.is_obstacle = is_obstacle;
    }

    public boolean getIsObstacle() {
        return is_obstacle;
    }
}
