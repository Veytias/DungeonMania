package dungeonmania.strategies.moveBehaviors;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public interface MoveBehaviour {
    public void move(String entity_id, Position current_postion, Map<String,Entity> all_entities, Position spawn_position, List<String> obstacles);
}
