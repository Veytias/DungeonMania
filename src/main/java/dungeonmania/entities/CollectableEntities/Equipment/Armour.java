package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.util.Position;

public class Armour extends Equipment {

    public Armour(Position position, String entity_id) {
        super(3, position, entity_id, "armour");
    }

    @Override
    public double getDefenceDamage(double base_damage) {
        return base_damage * 0.5;
    }


}
