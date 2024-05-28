package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.exceptions.InvalidActionException;

public interface Buildable {
    /**
     * Build equipment and add it to the an entity's owned items
     * @param owned_items
     * @return
     */
    public void build_entity(MovingEntity e) throws InvalidActionException;

    /**
     * Check if an entity can build this equipment using its owned items
     * @param e
     * @return true if it is e capable of building this and false otherwise
     */
    public boolean check_buildable(MovingEntity e);
}
