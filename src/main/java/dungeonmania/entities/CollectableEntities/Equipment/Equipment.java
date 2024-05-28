package dungeonmania.entities.CollectableEntities.Equipment;

import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Equipment extends CollectableEntity {
    int durability;

    public Equipment(int durability, Position position, String entity_id, String json_prefix) {
        super(position, entity_id, false, json_prefix);
        this.durability = durability;
    }

    /**
     * Decrease the duability of the equipment by 1
     */
    public void decreaseDurability() {
        if (this.durability > 0) {
            this.durability -= 1;
        }
    }

    public int getDurability() {
        return this.durability;
    }

    /**
     * Given this owner entity's base damage, how much net damage would
     * this entity aplly if this attack equipment is used
     * @param base_damage
     * @return
     */
    public double getDamage(double base_damage) {
        return base_damage;
    }

    /**
     * Given the opponents base damage, how much equipvalent damage
     * would the opponent apply if this defence equipment is worn
     * @param base_damage
     * @return
     */
    public double getDefenceDamage(double base_damage) {
        return base_damage;
    }


}