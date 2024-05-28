package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class MidnightArmour extends Equipment implements Buildable {

    public MidnightArmour(Position position, String entity_id) {
        super(5, position, entity_id, "midnight_armour");
    }

    @Override
    public double getDefenceDamage(double base_damage) {
        return base_damage * 0.5;
    }

    @Override
    public void build_entity(MovingEntity e) throws InvalidActionException {
        double armour_count = e.getOwnedItems().stream()
            .filter(c -> Armour.class.isAssignableFrom(c.getClass()))
            .count();

        double sun_stone_count = e.getOwnedItems().stream()
            .filter(c -> SunStone.class.isAssignableFrom(c.getClass()))
            .count();
        
        if (armour_count >= 1 && sun_stone_count >= 1) {
            e.removeItem(Armour.class);
            e.removeItem(SunStone.class);
            e.addItem(this);
            
        } else {
            throw new InvalidActionException("Player does not have sufficient items to craft a midnight armor");
        }
    }

    @Override
    public boolean check_buildable(MovingEntity e) {
        double armour_count = e.getOwnedItems().stream()
            .filter(c -> Armour.class.isAssignableFrom(c.getClass()))
            .count();

        double sun_stone_count = e.getOwnedItems().stream()
            .filter(c -> SunStone.class.isAssignableFrom(c.getClass()))
            .count();
        
        return (armour_count >= 1 && sun_stone_count >= 1);
    }

}
