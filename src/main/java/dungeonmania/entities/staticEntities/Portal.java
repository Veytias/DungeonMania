package dungeonmania.entities.staticEntities;

import java.util.List;
import java.util.Map;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;


public class Portal extends StaticEntity{
    
    private String colour;

    public Portal(Position position, String entity_id, String linker) {
        super(position, entity_id, "portal");
        this.colour = linker;
    }

    public String getPortalColour() {
        return this.colour;
    }

    public void teleport(Map<String, Entity> all_entities, Direction moveDirection) {
        
        Position portal_pos = this.getPosition();
        Character character = (Character) all_entities.get("Character");

        // find the destination portal
        
        Portal portal = null;
        Portal dest_portal = null;
        for (Entity curr_entity : all_entities.values()) {
            if (curr_entity instanceof Portal) {
                portal = (Portal) curr_entity;
                if (portal.getPortalColour().equals(this.getPortalColour()) && !portal.getEntityId().equals(this.getEntityId())) { // If it is an instance of a portal AND portal colour is the same AND it is not an instance of THIS portal
                    dest_portal = portal;
                    break;
                }    
            }
        }

        Position dest_portal_pos = dest_portal.getPosition();
        int char_pos_x = character.getPosition().getX();
        int char_pos_y = character.getPosition().getY(); 

        List<Position> adjacentPositions = portal_pos.getAdjacentPositions(); 
        
        // 0 1 2
        // 7 p 3
        // 6 5 4
        if (adjacentPositions.get(5).getX() == char_pos_x && adjacentPositions.get(5).getY() == char_pos_y+1 && moveDirection == Direction.UP) { // Moving Up
            character.setPosition(new Position(dest_portal_pos.getX(), dest_portal_pos.getY()-1));
        } else if (adjacentPositions.get(1).getX() == char_pos_x && adjacentPositions.get(1).getY() == char_pos_y-1 && moveDirection == Direction.DOWN) { // Moving Down   
            character.setPosition(new Position(dest_portal_pos.getX(), dest_portal_pos.getY()+1));
        } else if (adjacentPositions.get(3).getX()-1 == char_pos_x && adjacentPositions.get(3).getY() == char_pos_y && moveDirection == Direction.LEFT) { // Moving Left 
            character.setPosition(new Position(dest_portal_pos.getX()-1, dest_portal_pos.getY()));
        } else if (adjacentPositions.get(7).getX()+1 == char_pos_x && adjacentPositions.get(7).getY() == char_pos_y && moveDirection == Direction.RIGHT) { // Moving Right
            character.setPosition(new Position(dest_portal_pos.getX()+1, dest_portal_pos.getY()));
        }                
        
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction direction) {
        teleport(all_entities, direction);
    }



}
