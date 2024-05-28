package dungeonmania.entities;

import java.util.ArrayList;
import java.util.Map;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Entity {

    protected Position position;
    protected String entity_id;
    protected String json_prefix;

    public Entity(Position position, String entity_id, String json_prefix) {
        this.position = position; 
        this.entity_id = entity_id;
        this.json_prefix = json_prefix;
    }
    
    public Position getPosition() {
        return position;
    }

    public static ArrayList<Entity> getEntitiesInPos(Position p, Map<String, Entity> all_entities) {
        int x = p.getX();
        int y = p.getY();
    	ArrayList<Entity> entities_in_pos = new ArrayList<Entity>();
    	Position direction_vector;
        for (Entity curr_entity : all_entities.values()) {
            Position pos_match = new Position(x,y,0);
			direction_vector = Position.calculatePositionBetween(pos_match, curr_entity.getPosition()); 
            if (direction_vector.getX() == 0 && direction_vector.getY() == 0) { // if they are in the same cell
				entities_in_pos.add(curr_entity);
			}
    	}
    	return entities_in_pos;
    }

    public String getEntityId() {
        return entity_id;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Tick the entity 1 tick forwards 
     * @param gamemode
     * @param all_entities
     * @param moveDirection
     */
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction moveDirection) {
        
    }
    
    public String getJsonPrefix() {
        return json_prefix;
    };
}
