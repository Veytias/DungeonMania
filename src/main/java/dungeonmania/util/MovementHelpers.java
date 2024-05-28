package dungeonmania.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;

public class MovementHelpers {
    public static List<Position> getReachableCells(Collection<Entity> all_entities, List<String> obstacles, List<Position> movement_cells) {
        Set<Position> obstacle_positions = all_entities.stream().filter(entity -> obstacles.contains(entity.getClass().getSimpleName())) //is the entity an obstacle
                                                                .filter(entity -> movement_cells.contains(entity.getPosition())) //is the position reachable
                                                                .map(entity -> entity.getPosition()) //get the positions
                                                                .collect(Collectors.toSet()); //out positions in a set
        List<Position> reachable_cells = movement_cells.stream().filter(cell -> !obstacle_positions.contains(cell)) //cells we can reach arent in obstacle positions
                                                                .collect(Collectors.toList()); //collect in a list
        return reachable_cells;
    }
}
