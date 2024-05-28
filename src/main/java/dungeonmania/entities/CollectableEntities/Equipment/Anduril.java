package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.util.Position;

public class Anduril extends Equipment {
    
    public Anduril(Position position, String entity_id) {
        super(5, position, entity_id, "anduril");
    }

    @Override
    public double getDamage(double base_damage) {
        return base_damage + 5;
    }


}