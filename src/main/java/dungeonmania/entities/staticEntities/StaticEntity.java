package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class StaticEntity extends Entity {

    public StaticEntity(Position position, String entity_id, String json_prefix) {
        super(position, entity_id, json_prefix);
    }
}
