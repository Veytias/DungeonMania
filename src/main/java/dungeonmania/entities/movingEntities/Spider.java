package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.interfaces.Observer;
import dungeonmania.strategies.moveBehaviors.MoveAwayFromCharacter;
import dungeonmania.strategies.moveBehaviors.MoveCircular;
import dungeonmania.strategies.moveBehaviors.MoveOut;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends MovingEntity implements Observer{
    private boolean just_spawned;
    final static int regular_state = 0;
    final static int invisible_state = 1;
    final static int invincible_state = 2;
    private boolean isFleeing = false;
    private int swamp_tick = 0; 

    public Spider(Position position, String entity_id, String gamemode) {
        super(true,
            false,
            false,
            5,
            1,
            1,
            position,
            null,
            position,
            new MoveOut(),
            Arrays.asList(new String[]{"Boulder"}),
            entity_id,
            "spider");
        this.just_spawned = true;
    }

    @Override
    public void performMove(Map<String, Entity> all_entities) {
        boolean swamp_bool = swamp_check(all_entities, swamp_tick);
        if (swamp_bool == true) {
            swamp_tick++;
        } else {
            swamp_tick = 0;
            if (position.equals(spawn_position) == false && this.just_spawned == true) {
                move_behaviour = new MoveCircular();
                this.just_spawned = false;
            }
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
            this.isFleeing = true;
        } else if (state == invisible_state || state == regular_state) {
            if (this.isFleeing) {
                this.spawn_position = this.getPosition();
                this.setJustSpawned(true);
                this.move_behaviour = new MoveOut();
                this.isFleeing = false;
            } else {
                this.move_behaviour = new MoveCircular();
            }
        }
    }

    public void setJustSpawned(boolean just_spawned) {
        this.just_spawned = just_spawned;
    }
}

