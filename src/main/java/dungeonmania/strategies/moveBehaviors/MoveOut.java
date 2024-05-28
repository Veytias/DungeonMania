package dungeonmania.strategies.moveBehaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import dungeonmania.util.MovementHelpers;

public class MoveOut extends Move {
    public MoveOut() {
        super("MoveOut");
    }

    @Override
    public void move(String entity_id, Position current_postion, Map<String,Entity> all_entities, Position spawn_position, List<String> obstacles) {
        // get surrounding cells
        List<Position> adjacent_cells = current_postion.getAdjacentPositions();
        // Only interested in the odd indexes as they can be immediately moved into
        int x = current_postion.getX();
        int y = current_postion.getY();
        adjacent_cells.remove(new Position(x-1, y-1));
        adjacent_cells.remove(new Position(x+1, y-1));
        adjacent_cells.remove(new Position(x+1, y+1));
        adjacent_cells.remove(new Position(x-1, y+1));
        // Check for obstructions
        List<Position> reachable_cells = MovementHelpers.getReachableCells(all_entities.values(), obstacles, adjacent_cells);
        
        // Check reachable_cells is not empty
        if (reachable_cells.size() != 0) {
            // determine which cell to move to with priority being up, right, down, left
            List<Position> directions = new ArrayList<>();
            for (Position cell : reachable_cells) {
                Position offset = Position.calculatePositionBetween(current_postion,cell);
                directions.add(offset);
            }
            Position new_position = current_postion;
            if (directions.contains(Direction.UP.getOffset())) {
                new_position = current_postion.translateBy(Direction.UP);
            } else if (directions.contains(Direction.RIGHT.getOffset())) {
                new_position = current_postion.translateBy(Direction.RIGHT);
            } else if (directions.contains(Direction.DOWN.getOffset())) {
                new_position = current_postion.translateBy(Direction.DOWN);
            } else if (directions.contains(Direction.LEFT.getOffset())) {
                new_position = current_postion.translateBy(Direction.LEFT);
            }
            MovingEntity thisEntity = (MovingEntity) all_entities.get(entity_id);
            thisEntity.setPosition(new_position);
        }
    }

    
}
