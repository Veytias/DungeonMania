package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;

public class Boulder extends StaticEntity{
    
    public Boulder(Position position, String entity_id) {
        super(position, entity_id, "boulder");
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction moveDirection) {
        
        Position boulder_pos = getPosition();
        Character character = (Character) all_entities.get("Character");

        Position character_pos = character.getPosition();
        // 0 1 2
        // 7 p 3
        // 6 5 4
        List<Position> adjacentPositions = boulder_pos.getAdjacentPositions();
        if (character_pos.equals(boulder_pos) && moveDirection == Direction.UP) { // Moving Up
            ArrayList<Entity> entities_destination = getEntitiesInPos(adjacentPositions.get(1), all_entities);
            if (entities_destination.size() == 0) {
                this.setPosition(adjacentPositions.get(1)); 
            } else { 
                for (Entity switch_track : entities_destination) {
                    if (switch_track instanceof FloorSwitch) {
                        this.setPosition(adjacentPositions.get(1));
                    }
                }
            }
        } else if (character_pos.equals(boulder_pos) && moveDirection == Direction.DOWN) { // Moving Down
            ArrayList<Entity> entities_destination = getEntitiesInPos(adjacentPositions.get(5), all_entities);
            if (entities_destination.size() == 0) {
                this.setPosition(adjacentPositions.get(5)); 
            } else { 
                for (Entity switch_track : entities_destination) {
                    if (switch_track instanceof FloorSwitch) {
                        this.setPosition(adjacentPositions.get(5));
                    }
                }
            }
        } else if (character_pos.equals(boulder_pos) && moveDirection == Direction.LEFT) { // Moving Left
            ArrayList<Entity> entities_destination = getEntitiesInPos(adjacentPositions.get(7), all_entities);
            if (entities_destination.size() == 0) {
                this.setPosition(adjacentPositions.get(7)); 
            } else { 
                for (Entity switch_track : entities_destination) {
                    if (switch_track instanceof FloorSwitch) {
                        this.setPosition(adjacentPositions.get(7)); 
                    }
                }
            }    
        } else if (character_pos.equals(boulder_pos) && moveDirection == Direction.RIGHT) { // Moving Right
            ArrayList<Entity> entities_destination = getEntitiesInPos(adjacentPositions.get(3), all_entities);
            if (entities_destination.size() == 0) {
                character.setPosition(boulder_pos);
                this.setPosition(adjacentPositions.get(3)); 
            } else { 
                for (Entity switch_track : entities_destination) {
                    if (switch_track instanceof FloorSwitch) {
                        this.setPosition(adjacentPositions.get(3)); 
                    }
                }
            }
            
        } 
            
        
    }




}
