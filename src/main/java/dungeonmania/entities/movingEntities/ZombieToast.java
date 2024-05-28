package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.Map;


import dungeonmania.entities.Entity;
import dungeonmania.entities.interfaces.Observer;
import dungeonmania.strategies.moveBehaviors.MoveAwayFromCharacter;
import dungeonmania.strategies.moveBehaviors.MoveRandom;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity implements Observer{
    final static int regular_state = 0;
    final static int invisible_state = 1;
    final static int invincible_state = 2; 
    private int swamp_tick = 0; 


    public ZombieToast(Position position, String entity_id, String gamemode) {
        super(true,
            false,
            false,
            5,
            1,
            1,
            position,
            null,
            position,
            new MoveRandom(),
            Arrays.asList(new String[]{"Wall", "Door", "Boulder"}),
            entity_id,
            "zombie_toast");
}

    @Override
    public void performMove(Map<String, Entity> all_entities) {
        boolean swamp_bool = swamp_check(all_entities, swamp_tick);
        if (swamp_bool == true) {
            swamp_tick++;
        } else {
            swamp_tick = 0;
            move_behaviour.move(entity_id, position, all_entities, spawn_position, obstacles);
        }    
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction direction) {
        performMove(all_entities);
        Character character = (Character) all_entities.get("Character");
        is_battling = position.equals(character.getPosition());
    }

    @Override
    public void update(int state) {
        if (state == invincible_state) {
            this.move_behaviour = new MoveAwayFromCharacter();
        } else if (state == invisible_state || state == regular_state) {
            this.move_behaviour = new MoveRandom();
        }
    }
}
