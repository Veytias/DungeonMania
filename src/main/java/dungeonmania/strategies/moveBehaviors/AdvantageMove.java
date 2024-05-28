package dungeonmania.strategies.moveBehaviors;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class AdvantageMove extends Move {
    public AdvantageMove() {
        super("AdvantageMove");
    }

    MoveBehaviour moveToward = new MoveTowardCharacter();
    
    @Override
    public void move(String entity_id, Position current_postion, Map<String,Entity> all_entities, Position spawn_position, List<String> obstacles) {
        moveToward.move(entity_id, current_postion, all_entities, spawn_position, obstacles);
        Entity thisEntity = all_entities.get(entity_id);
        moveToward.move(entity_id, thisEntity.getPosition(), all_entities, spawn_position, obstacles);
    }

    
}
