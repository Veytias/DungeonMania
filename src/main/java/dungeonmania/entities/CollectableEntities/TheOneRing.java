package dungeonmania.entities.CollectableEntities;

import dungeonmania.entities.Useable;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.util.Position;

public class TheOneRing extends CollectableEntity implements Useable{

    public TheOneRing(Position position, String entity_id) {
        super(position, entity_id, false, "one_ring");
    }

    public void use(Character c, String gamemode) {
        c.setHealth(10);
    }


}
