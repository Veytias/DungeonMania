package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.Math;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.CollectableEntities.TheOneRing;
import dungeonmania.entities.interfaces.Interactable;
import dungeonmania.entities.interfaces.Observer;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.strategies.moveBehaviors.AdvantageMove;
import dungeonmania.strategies.moveBehaviors.MoveAwayFromCharacter;
import dungeonmania.strategies.moveBehaviors.MoveDijkstra;
import dungeonmania.strategies.moveBehaviors.StandStill;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends MovingEntity implements Interactable, Observer {
    private int battle_radius = 3;
    final static int regular_state = 0;
    final static int invisible_state = 1;
    final static int invincible_state = 2;
    private int swamp_tick = 0; 

    public Assassin(Position position, String entity_id, String gamemode) {
        super(true,
            false,
            false,
            5,
            5,
            1,
            position,
            null,
            position,
            new MoveDijkstra(),
            Arrays.asList(new String[]{"Wall","Door","Boulder"}),
            entity_id,
            "assassin");
    }

    @Override
    public void update(int state) {
        if (state == invincible_state) {
            this.move_behaviour = new MoveAwayFromCharacter();
        }
        else if (state == invisible_state) {
            this.move_behaviour = new StandStill();
        } else if (state == regular_state) {
            this.move_behaviour = new MoveDijkstra();
        }
    }

    @Override
    public void interact(Map<String, Entity> all_entities) throws InvalidActionException {
        Character character = (Character) all_entities.get("Character");
        // Check character within 2 cardinal tiles
        Position distance = Position.calculatePositionBetween(this.position, character.getPosition());
        if (Math.abs(distance.getX()) > 2 && Math.abs(distance.getY()) > 2) {
            throw new InvalidActionException("Player not within 2 tiles cardinaly");
        }
        // Check character owns sufficient items to bribe
        List<CollectableEntity> c_items = character.getOwnedItems();
        List<CollectableEntity> coins = c_items.stream().filter(item -> item instanceof Treasure)
                                                        .collect(Collectors.toList());
        List<CollectableEntity> rings = c_items.stream().filter(item -> item instanceof TheOneRing)
                                                        .collect(Collectors.toList());
        if (coins.size() < 1) {
            throw new InvalidActionException("Not enough treasure to bribe");
        } else if (rings.size() < 1) {
            throw new InvalidActionException("Not enough One Rings to bribe");
        } else {
            // Assassin now ally and remove coins and One Ring from character
            this.is_enemy = false;
            for (int i = 0; i < 1; i++) {
                character.removeItem(Treasure.class);
            }
            for (int i = 0; i < 1; i++) {
                character.removeItem(TheOneRing.class);
            }
            character.addAlly(this);
        }
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction direction) {
        performMove(all_entities);
        Character character = (Character) all_entities.get("Character");
        is_battling = position.equals(character.getPosition());
    }

    @Override
    public void performMove(Map<String, Entity> all_entities) {
        boolean swamp_bool = swamp_check(all_entities, swamp_tick);
        if (swamp_bool == true) {
            swamp_tick++;
        } else {
            swamp_tick = 0;
            Character character = (Character) all_entities.get("Character");
            Position character_positon = character.getPosition();
            Position distance_vector = Position.calculatePositionBetween(character_positon, this.position);
            boolean in_battle_radius = false;
            if (Math.abs(distance_vector.getX()) <= this.battle_radius || Math.abs(distance_vector.getY()) <= this.battle_radius) {
                in_battle_radius = true;
            }
            if (character.getIsBattling() && in_battle_radius) {
                this.move_behaviour = new AdvantageMove();
                this.move_behaviour.move(entity_id, position, all_entities, spawn_position, obstacles);
            } else {
                this.move_behaviour.move(entity_id, position, all_entities, spawn_position, obstacles);
            }
        }    
    }
}
