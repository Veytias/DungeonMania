package dungeonmania.entities.staticEntities;

import java.util.List;
import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Exit extends StaticEntity{

    private boolean character_reached_exit = false;

    public Exit(Position position, String entity_id) {
        super(position, entity_id, "exit");
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction moveDirection) {
        
        Position exit_pos = this.getPosition();
        Character character = (Character) all_entities.get("Character");
        Position character_pos = character.getPosition();
        int char_pos_x = character_pos.getX();
        int char_pos_y = character_pos.getY();

        // 0 1 2
        // 7 p 3
        // 6 5 4
        List<Position> adjacentPositions = exit_pos.getAdjacentPositions();
        if (adjacentPositions.get(5).getX() == char_pos_x && adjacentPositions.get(5).getY() == char_pos_y+1 && moveDirection == Direction.UP) { // Moving Up
            setCharacterExit(true);
        } else if (adjacentPositions.get(1).getX() == char_pos_x && adjacentPositions.get(1).getY() == char_pos_y-1 && moveDirection == Direction.DOWN) { // Moving Down
            setCharacterExit(true);
        } else if (adjacentPositions.get(3).getX()-1 == char_pos_x && adjacentPositions.get(3).getY() == char_pos_y && moveDirection == Direction.LEFT) { // Moving Left
            setCharacterExit(true);
        } else if (adjacentPositions.get(7).getX()+1 == char_pos_x && adjacentPositions.get(7).getY() == char_pos_y && moveDirection == Direction.RIGHT) { // Moving Right
            setCharacterExit(true);
        }    
        
    }

    public void setCharacterExit(boolean flag) {
        this.character_reached_exit = flag;
    }
 
    public boolean getCharacterExit() {
        return this.character_reached_exit;
    }




}
