package dungeonmania.strategies.BattleBehaviors;

import java.util.List;

import dungeonmania.entities.CollectableEntities.Equipment.Anduril;
import dungeonmania.entities.CollectableEntities.Equipment.Bow;
import dungeonmania.entities.CollectableEntities.Equipment.Equipment;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.MovingEntity;

public class RegularBattle extends Battle {
    public RegularBattle() {
        super("RegularBattle");
    }

    @Override
    public void battle(Character e1, MovingEntity e2, List<MovingEntity> allies) {
        boolean battle_finished = false;
        while(battle_finished == false) {
            removeScrapEquipment(e1);
            removeScrapEquipment(e2);

            Equipment a1 = this.getAttackEquipment(e1);
            Equipment d1 = this.getDefenceEquipment(e1);
            Equipment a2 = this.getAttackEquipment(e2);
            Equipment d2 = this.getDefenceEquipment(e2);

            // bow allows you to shoot from a distance and damage the enemy one extra time
            if (a1.getClass().equals(Bow.class)) {
                e2.getHit((int) (e1.getHealth() * d2.getDefenceDamage(a1.getDamage(e1.getDamage())) / 5));
            }

            // all allies attack once
            for (MovingEntity ally : allies) {
                Equipment ally_a1 = this.getAttackEquipment(ally);
                e2.getHit((int) (ally.getHealth() * d2.getDefenceDamage(ally_a1.getDamage(ally.getDamage())) / 5));
            }

            e1.getHit((int) (e2.getHealth() * d1.getDefenceDamage(a2.getDamage(e2.getDamage())) / 10));

            if (Anduril.class.isAssignableFrom(a1.getClass()) &&
                (Assassin.class.isAssignableFrom(a1.getClass()) ||
                Hydra.class.isAssignableFrom(a1.getClass()))) {
                // triple damage against bosses
                e2.getHit((int) (3 * (e1.getHealth() * d2.getDefenceDamage(a1.getDamage(e1.getDamage())) / 5)));
            } else {
                e2.getHit((int) (e1.getHealth() * d2.getDefenceDamage(a1.getDamage(e1.getDamage())) / 5));
            }

            a1.decreaseDurability();
            d1.decreaseDurability();
            a2.decreaseDurability();
            d2.decreaseDurability();

            if (e1.getHealth() <= 0 || e2.getHealth() <= 0) {
                // at least one of them dies
                battle_finished = true;
            }
        }
    }
    
    
}
