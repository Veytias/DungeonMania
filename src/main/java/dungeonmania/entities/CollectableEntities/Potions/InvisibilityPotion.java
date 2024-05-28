package dungeonmania.entities.CollectableEntities.Potions;

import dungeonmania.entities.Useable;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;

public class InvisibilityPotion extends Potion implements Useable{
    private static int limit_time = 5;

    public InvisibilityPotion(Position position, String entity_id) {
        super(position, entity_id, false, "invisibility_potion");
    }

    public static int getLimit_time() {
        return limit_time;
    }



    @Override
    public void use(Character c, String gamemode) {
        
        c.useItem(this, gamemode);
    }
}

