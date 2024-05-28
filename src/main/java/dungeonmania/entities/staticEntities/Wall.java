package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

public class Wall extends StaticEntity{

    /**
     * Instantiates a new wall.
     *
	 * @param x 		The x position
	 * @param y 		The y position
     */
    public Wall(Position position, String entity_id) {
        super(position, entity_id, "wall");
    }



    
}
