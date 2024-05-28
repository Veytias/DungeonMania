package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.entities.CollectableEntities.Arrow;
import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Bow extends Equipment implements Buildable {

    public Bow(Position position, String entity_id) {
        super(4, position, entity_id, "bow");
    }

    @Override
    public double getDamage(double base_damage) {
        return base_damage + 5;
    }

    public void build_entity(MovingEntity e) throws InvalidActionException {
        double wood_count = e.getOwnedItems().stream()
            .filter(c -> Wood.class.isAssignableFrom(c.getClass()))
            .count();

        double arrow_count = e.getOwnedItems().stream()
            .filter(c -> Arrow.class.isAssignableFrom(c.getClass()))
            .count();

        if (wood_count >= 1 && arrow_count >= 3) {
            e.removeItem(Wood.class);
            e.removeItem(Arrow.class);
            e.removeItem(Arrow.class);
            e.removeItem(Arrow.class);
            e.addItem(this);
            
        } else {
            throw new InvalidActionException("Player does not have sufficient items to craft a bow");
        }
    }

    public boolean check_buildable(MovingEntity e) {
        double wood_count = e.getOwnedItems().stream()
            .filter(c -> Wood.class.isAssignableFrom(c.getClass()))
            .count();

        double arrow_count = e.getOwnedItems().stream()
            .filter(c -> Arrow.class.isAssignableFrom(c.getClass()))
            .count();

        return (wood_count >= 1 && arrow_count >= 3);
    }


}
