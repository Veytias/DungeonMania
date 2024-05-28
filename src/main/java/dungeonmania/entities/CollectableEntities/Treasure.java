package dungeonmania.entities.CollectableEntities;

import dungeonmania.util.Position;

public class Treasure extends CollectableEntity{

    public Treasure(Position position, String entity_id){
        super(position, entity_id, false, "treasure");
    }
    

}