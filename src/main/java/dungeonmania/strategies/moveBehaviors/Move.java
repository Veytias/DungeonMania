package dungeonmania.strategies.moveBehaviors;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Move implements MoveBehaviour {
    private String json_prefix;

    public Move(String json_prefix) {
        this.json_prefix = json_prefix;
    }

    public String getJsonPrefix() {
        return this.json_prefix;
    }

    @Override
    public void move(String entity_id, Position current_postion, Map<String, Entity> all_entities,
            Position spawn_position, List<String> obstacles) {
        // TODO Auto-generated method stub
        
    }
}
