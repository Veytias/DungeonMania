package dungeonmania.strategies.moveBehaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.util.MovementHelpers;
import dungeonmania.util.Position;

public class MoveAwayFromCharacter extends Move {
    public MoveAwayFromCharacter() {
        super("MoveAwayFromCharacter");
    }

    @Override
    public void move(String entity_id, Position current_postion, Map<String, Entity> all_entities, Position spawn_position, List<String> obstacles) {
        // Find the character
        Entity character = all_entities.get("Character");
        // Get the vector from this entity to the character
        Position direction_vector = Position.calculatePositionBetween(current_postion, character.getPosition());
        int x = direction_vector.getX();
        int y = direction_vector.getY();
        List<Position> movement_cells = new ArrayList<>();
        // populating movement_cells with possible moves
        if (y < 0) {
            movement_cells.add(new Position(current_postion.getX(), current_postion.getY()+1));
        } else if (y > 0) {
            movement_cells.add(new Position(current_postion.getX(), current_postion.getY()-1));
        }
        if (x < 0) {
            movement_cells.add(new Position(current_postion.getX()+1, current_postion.getY()));
        } else if (x > 0) {
            movement_cells.add(new Position(current_postion.getX()-1, current_postion.getY()));
        }
        if (movement_cells.size() != 0) {
            // Check for obstructions
            List<Position> reachable_cells = MovementHelpers.getReachableCells(all_entities.values(), obstacles, movement_cells);
            if (reachable_cells.size() != 0) {
                // Take first available move
                Position movement_vector = Position.calculatePositionBetween(current_postion, reachable_cells.get(0));
                Position new_position = current_postion.translateBy(movement_vector);
                MovingEntity thisEntity = (MovingEntity) all_entities.get(entity_id);
                thisEntity.setPosition(new_position);
            }
        }
    }

    
}
