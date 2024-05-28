package dungeonmania.strategies.moveBehaviors;

import java.util.List;
import java.util.Map;
import java.util.Random;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.util.Position;
import dungeonmania.util.MovementHelpers;

public class MoveRandom extends Move {
    public MoveRandom() {
        super("MoveRandom");
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
        
        // check there are still reachable cells
        if (reachable_cells.size() != 0) {
            // rng the cell that is moved on
            Random r = new Random();
            int index = r.nextInt(reachable_cells.size());
            Position movement_vector = Position.calculatePositionBetween(current_postion, reachable_cells.get(index));
            Position new_position = current_postion.translateBy(movement_vector);
            MovingEntity thisEntity = (MovingEntity) all_entities.get(entity_id);
            thisEntity.setPosition(new_position);
        }
    }

    
}
