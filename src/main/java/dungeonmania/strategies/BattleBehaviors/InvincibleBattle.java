package dungeonmania.strategies.BattleBehaviors;

import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.movingEntities.Character;

import java.util.List;

public class InvincibleBattle extends Battle {
    
    public InvincibleBattle() {
        super("InvincibleBattle");
    }
    
    @Override
    public void battle(Character e1, MovingEntity e2, List<MovingEntity> allies) {
        e2.setHealth(0);
    }
}
