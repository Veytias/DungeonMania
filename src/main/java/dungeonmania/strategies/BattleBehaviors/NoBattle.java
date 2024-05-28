package dungeonmania.strategies.BattleBehaviors;

import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.movingEntities.Character;

import java.util.List;

public class NoBattle extends Battle {
    
    public NoBattle() {
        super("NoBattle");
    }
    
    /**
     * No battle occurs
     * @param e1
     * @param e2
     */
    @Override
    public void battle(Character e1, MovingEntity e2, List<MovingEntity> allies) {
        // nothing happens
    }
}
