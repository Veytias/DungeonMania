package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.CollectableEntities.Arrow;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class Sceptre extends Equipment implements Buildable {

    public Sceptre(Position position, String entity_id) {
        super(10, position, entity_id, "sceptre");
    }

    @Override
    public double getDamage(double base_damage) {
        return base_damage + 3;
    }

    @Override
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
        
        long arrow_count = e.getOwnedItems().stream()
            .filter(c -> Arrow.class.isAssignableFrom(c.getClass()))
            .count();
        
        long sun_stone_count = e.getOwnedItems().stream()
            .filter(c -> SunStone.class.isAssignableFrom(c.getClass()))
            .count();
        
        if (wood_count >= 1 && key_count >= 1 && sun_stone_count >= 1) {
            e.removeItem(Wood.class);
            e.removeItem(Key.class);
            e.removeItem(SunStone.class);
            e.addItem(this);
            
        } else if (arrow_count >= 2 && key_count >= 1 && sun_stone_count >= 1) {
            e.removeItem(Arrow.class);
            e.removeItem(Arrow.class);
            e.removeItem(Key.class);
            e.removeItem(SunStone.class);
            e.addItem(this);

        } else if (wood_count >= 1 && treasure_count >= 1 && sun_stone_count >= 1) {
            e.removeItem(Wood.class);
            e.removeItem(Treasure.class);
            e.removeItem(SunStone.class);
            e.addItem(this);

        } else if (arrow_count >= 2 && treasure_count >= 1 && sun_stone_count >= 1) {
            e.removeItem(Arrow.class);
            e.removeItem(Arrow.class);
            e.removeItem(Treasure.class);
            e.removeItem(SunStone.class);
            e.addItem(this);
            
        } else {
            throw new InvalidActionException("Player does not have sufficient items to craft a scptre");
        }
    }

    @Override
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
        
        long arrow_count = e.getOwnedItems().stream()
            .filter(c -> Arrow.class.isAssignableFrom(c.getClass()))
            .count();

        long sun_stone_count = e.getOwnedItems().stream()
            .filter(c -> SunStone.class.isAssignableFrom(c.getClass()))
            .count();
        
        return ((wood_count >= 1 || arrow_count >= 2) && (treasure_count >= 1 || key_count >= 1) && (sun_stone_count >= 1));
    }
    
}
