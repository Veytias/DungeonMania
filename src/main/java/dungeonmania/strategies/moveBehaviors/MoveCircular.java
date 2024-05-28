package dungeonmania.strategies.moveBehaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.util.Position;
import dungeonmania.util.MovementHelpers;

public class MoveCircular extends Move {
    public MoveCircular() {
        super("MoveCircular");
    }

    private int direction = 0; // Clockwise or anticlockwise (clockwise default)
    @Override
    public void move(String entity_id, Position current_postion, Map<String,Entity> all_entities, Position spawn_position, List<String> obstacles) {
        // Retrieve the cells adjacent to the entity's spawn location
        List<Position> adjacent_cells = spawn_position.getAdjacentPositions();
        // Find index of our position in the adjacent cells of the spawn
        int position_index = adjacent_cells.indexOf(current_postion);
        // Find the clockwise and anticlockwise index and make sure 0 <= index <= 7
        int clockwise_index = position_index + 1;
        int anticlockwise_index = position_index - 1;
        if (clockwise_index > 7) clockwise_index = 0;
        if (anticlockwise_index < 0) anticlockwise_index = 7;
        List<Position> movement_cells = new ArrayList<>();
        if (direction == 0) {
            movement_cells.add(adjacent_cells.get(clockwise_index));
            movement_cells.add(adjacent_cells.get(anticlockwise_index));
        } else {
            movement_cells.add(adjacent_cells.get(anticlockwise_index));
            movement_cells.add(adjacent_cells.get(clockwise_index));
        }
        // Check for obstructions
        List<Position> reachable_cells = MovementHelpers.getReachableCells(all_entities.values(), obstacles, movement_cells);

        if (reachable_cells.size() != 0) {
            // Available move so take it
            Position movement_vector = Position.calculatePositionBetween(current_postion, reachable_cells.get(0));
            Position new_position = current_postion.translateBy(movement_vector);
            MovingEntity thisEntity = (MovingEntity) all_entities.get(entity_id);
            thisEntity.setPosition(new_position);
            // check to see if a direction change occured
            int index = movement_cells.indexOf(reachable_cells.get(0));
            if (index == 1) {
                // Direction change occurs if we aren't taking the first index as the next move
                if (direction == 0) this.direction = 1;
                else if (direction == 1) this.direction = 0;
            } 
        }
    }

    
}
