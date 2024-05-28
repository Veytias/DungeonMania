package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

/**
     * Instantiates a new swamp tile.
     *
	 * @param x 		The x position
	 * @param y 		The y position
*/
public class SwampTile extends StaticEntity {

    private int movement_factor;
    public SwampTile(Position position, String entity_id, int movement_factor) {
        super(position, entity_id, "swamp_tile");
        this.movement_factor = movement_factor;
    }

    public int getMovementFactor() {
        return this.movement_factor;
    }
    
}
