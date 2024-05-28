package dungeonmania.strategies.BattleBehaviors;

import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.movingEntities.Character;

import java.util.List;

public interface BattleBehaviour {
    /**
     * A battle between Entities e1 and e2
     * @param e1
     * @param e2
     */
    public void battle(Character e1, MovingEntity e2, List<MovingEntity> allies);
}
