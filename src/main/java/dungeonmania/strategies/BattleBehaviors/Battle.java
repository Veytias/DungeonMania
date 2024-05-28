package dungeonmania.strategies.BattleBehaviors;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.CollectableEntities.Equipment.Equipment;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.MovingEntity;

public class Battle implements BattleBehaviour {
    String json_prefix;

    public Battle(String json_prefix) {
        this.json_prefix = json_prefix;
    }

    /**
     * Loop through e's equipment and remove scraps (equipment with 0 durability)
     * @param e
     * @return
     */
    public void removeScrapEquipment(MovingEntity e) {
        List<Equipment> scraps = e.getOwnedItems().stream()
            .filter(c -> Equipment.class.isAssignableFrom(c.getClass()))
            .map(c -> (Equipment) c)
            .filter(c -> c.getDurability() <= 0)
            .collect(Collectors.toList());
        
        scraps.stream().forEach(s -> e.removeItem(s));
    }

    /**
     * Loop through e's attack equipment and choose the one with the highest damage
     * @param e
     * @return
     */
    public Equipment getAttackEquipment(MovingEntity e) {
        return e.getOwnedItems().stream()
            .filter(c -> Equipment.class.isAssignableFrom(c.getClass()))
            .map(c -> (Equipment) c)
            .max((o1, o2) -> Integer.compare((int) o1.getDamage(e.getDamage()), (int) o2.getDamage(e.getDamage())))
            .orElse(new Equipment(1, null, null, null));
    }

    /**
     * Loop through e's defence equipment and choose the one with the lowest defence multiplier
     * @param e
     * @return
     */
    public Equipment getDefenceEquipment(MovingEntity e) {
        return e.getOwnedItems().stream()
            .filter(c -> Equipment.class.isAssignableFrom(c.getClass()))
            .map(c -> (Equipment) c)
            .min((o1, o2) -> Double.compare((int) o1.getDefenceDamage(e.getDamage()), (int) o2.getDefenceDamage(e.getDamage())))
            .orElse(new Equipment(1, null, null, null));
    }

    public String getJsonPrefix() {
        return this.json_prefix;
    }

    @Override
    public void battle(Character e1, MovingEntity e2, List<MovingEntity> allies) {
        // TODO Auto-generated method stub
        
    }
}
