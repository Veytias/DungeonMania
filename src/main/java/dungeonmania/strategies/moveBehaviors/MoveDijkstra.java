package dungeonmania.strategies.moveBehaviors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.entities.staticEntities.Door;
import dungeonmania.entities.staticEntities.StaticEntity;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.entities.staticEntities.Wall;

public class MoveDijkstra extends Move {
    
    public MoveDijkstra() {
        super("MoveDijkstra");
    }
    private Position dijkstra(String entity_id, Position current_postion, Map<String, Entity> all_entities) {
        Position source = current_postion;
        Character character = (Character) all_entities.get("Character");
        Position destination = character.getPosition();

        if (source.equals(destination)) {
            // do nothing
            return source;
        }

        // width and height contain size of grid
        int curr_max_x = 0;
        int curr_max_y = 0;
        for (Entity e : all_entities.values()) {
            if (e.getPosition().getX() > curr_max_x) {
                curr_max_x = e.getPosition().getX(); // width is max x value
            } 
            if (e.getPosition().getY() > curr_max_y) {
                curr_max_y = e.getPosition().getY(); // height is max y value
            }
        } 
        final int width = curr_max_x;
        final int height = curr_max_y;
        Map<Position, Integer> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();
        Queue<Position> queue = new PriorityQueue<>();

        // Initialisation
        for (int x = 0; x <= width; x++) {
            for (int y = 0; y <= height; y++) {
                Position position = new Position(x, y);
                dist.put(position, 10000); // each position has a value of 10000 assigned to it
                prev.put(position, new Position(1000, 1000));  // each position in prev 
                queue.add(position);
            }
        }

        dist.put(source, 0); // Source node set to 0 
        
        int final_cost = 0;
        //while queue is not empty:
        while (!queue.isEmpty()) {
            
            // Go over the distance VALUES (integer field) in the map and find the next smallest distance. Return the key of the map.
            // u := next node in queue with the smallest dist
            Position lowest_dist_pos = getMinKey(dist, queue);
            List<Position> cardinal_pos = new ArrayList<Position>(); // Contains the 4 cardinally adjacent cells
            List<Position> adjacentCells = lowest_dist_pos.getAdjacentPositions();
            cardinal_pos.add(adjacentCells.get(1));
            cardinal_pos.add(adjacentCells.get(3));
            cardinal_pos.add(adjacentCells.get(5));
            cardinal_pos.add(adjacentCells.get(7));
            List<Position> accepted_cardinal = cardinal_pos.stream().filter(pos -> pos.getX() >= 0 && pos.getY() >= 0)
                                                                    .filter(pos -> (pos.getX() <= width) && (pos.getY() <= height))
                                                                    .collect(Collectors.toList());
            //for each cardinal neighbour v of u:
            for (int i = 0; i < accepted_cardinal.size(); i++) {
                boolean continue_flag = false;
                List<Entity> entities_in_pos = StaticEntity.getEntitiesInPos(accepted_cardinal.get(i), all_entities);
                for (Entity e : entities_in_pos) {
                    if (e instanceof Boulder || e instanceof Door || e instanceof Wall) {
                        continue_flag = true;
                    }
                }
                if (continue_flag) {
                    continue;
                }    
                SwampTile swamp_tile = null;
                int movementfactor = 1; // default movement factor is 1 when swamptile not present
                for (Entity swamp_track : entities_in_pos) {
                    if (swamp_track instanceof SwampTile) {
                        swamp_tile = (SwampTile) swamp_track;
                        movementfactor = swamp_tile.getMovementFactor(); // swamptile has a custom movement factor
                    }
                }
                final_cost = movementfactor + dist.get(lowest_dist_pos);
                // if dist[u] + cost(u, v) < dist[v]:
                if (final_cost < dist.get(accepted_cardinal.get(i))) {
                    //int updated_dist = final_cost + movementfactor; 
                    dist.put(accepted_cardinal.get(i), final_cost);  
                    prev.put(accepted_cardinal.get(i), lowest_dist_pos);     
                }
            } 
            queue.remove(lowest_dist_pos);
        }
        List<Position> path = new ArrayList<>();
        path.add(destination);
        Position previousPosition = prev.get(destination);
        if (previousPosition.equals(source)) {
            return destination;
        }
        path.add(previousPosition);
        while (prev.get(previousPosition).equals(source) == false) {
            previousPosition = prev.get(previousPosition);
            path.add(previousPosition);
        }
        if (path.size() != 0) {
            return path.get(path.size() - 1);
        } else {
            return source;
        }

    }
    
    public void move(String entity_id, Position current_postion, Map<String, Entity> all_entities, Position spawn_position, List<String> obstacles) {
        Position nextCell = dijkstra(entity_id, current_postion, all_entities);
        if (nextCell.equals(current_postion)) {
           return;
        } else {
            MovingEntity thisEntity = (MovingEntity) all_entities.get(entity_id);
            thisEntity.setPosition(nextCell);
        }
    }
    // Source: https://stackoverflow.com/questions/21777745/finding-the-key-of-hashmap-which-holds-the-lowest-integer-value/21778704
    private Position getMinKey(Map<Position, Integer> map, Queue<Position> queue) {
        Entry<Position, Integer> min = null;
        for (Entry<Position, Integer> entry : map.entrySet()) {
            if ((min == null || min.getValue() > entry.getValue()) && queue.contains(entry.getKey())) {
                min = entry;
            }
        }
        return min.getKey();
    }
}
