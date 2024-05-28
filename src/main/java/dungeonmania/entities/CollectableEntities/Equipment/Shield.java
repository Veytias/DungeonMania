package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Shield extends Equipment implements Buildable {
    
    public Shield(Position position, String entity_id) {
        super(3, position, entity_id, "shield");
    }

    @Override
    public double getDefenceDamage(double base_damage) {
        return base_damage * 0.66;
    }

    public void build_entity(MovingEntity e) throws InvalidActionException {
        long wood_count = e.getOwnedItems().stream()
            .filter(c -> Wood.class.isAssignableFrom(c.getClass()))
            .count();

        long treasure_count = e.getOwnedItems().stream()
            .filter(c -> Treasure.class.isAssignableFrom(c.getClass()))
            .count();
        
        long key_count = e.getOwnedItems().stream()
            .filter(c -> Key.class.isAssignableFrom(c.getClass()))
            .count();

        long sunStone_count = e.getOwnedItems().stream()
            .filter(c -> SunStone.class.isAssignableFrom(c.getClass()))
            .count();

        if (wood_count >= 2 && treasure_count >= 1) {
            e.removeItem(Wood.class);
            e.removeItem(Wood.class);
            e.removeItem(Treasure.class);
            e.addItem(this);
        } else if (wood_count >= 2 && key_count >= 1) {
            e.removeItem(Wood.class);
            e.removeItem(Wood.class);
            e.removeItem(Key.class);
            e.addItem(this);
        } else if (wood_count >= 2 && sunStone_count >= 1) {
            e.removeItem(Wood.class);
            e.removeItem(Wood.class);
            e.removeItem(SunStone.class);
            e.addItem(this);
        } else {
            throw new InvalidActionException("Player does not have sufficient items to craft a shield");
        }
    }

    public boolean check_buildable(MovingEntity e) {
        long wood_count = e.getOwnedItems().stream()
            .filter(c -> Wood.class.isAssignableFrom(c.getClass()))
            .count();

        long treasure_count = e.getOwnedItems().stream()
            .filter(c -> Treasure.class.isAssignableFrom(c.getClass()))
            .count();
        
        long key_count = e.getOwnedItems().stream()
            .filter(c -> Key.class.isAssignableFrom(c.getClass()))
            .count();
        long sunStone_count = e.getOwnedItems().stream()
            .filter(c -> SunStone.class.isAssignableFrom(c.getClass()))
            .count();
        return (wood_count >= 2 && (sunStone_count >= 1 || key_count >= 1 || treasure_count >= 1));
    }


}
