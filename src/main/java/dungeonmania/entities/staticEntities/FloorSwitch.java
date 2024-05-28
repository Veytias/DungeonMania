package dungeonmania.entities.staticEntities;

import java.util.Map;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity{

    private boolean is_triggered;

    public FloorSwitch(Position position, String entity_id) {
        super(position, entity_id, "switch");
    }

    public boolean getIsTriggered() {
        return this.is_triggered;
    }

    public void setIsTriggered(boolean trigger) {
        this.is_triggered = trigger;
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction moveDirection) {
        double boulder_count = getEntitiesInPos(getPosition(), all_entities)
                .stream()
                .filter(e -> Boulder.class.isAssignableFrom(e.getClass()))
                .count();
        
        setIsTriggered(boulder_count > 0);
    }
    



}
