package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.util.Position;

public class Sword extends Equipment {
    
    public Sword(Position position, String entity_id) {
        super(5, position, entity_id, "sword");
    }

    @Override
    public double getDamage(double base_damage) {
        return base_damage + 3;
    }


}
