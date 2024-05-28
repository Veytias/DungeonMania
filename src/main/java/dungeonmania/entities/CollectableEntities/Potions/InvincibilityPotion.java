package dungeonmania.entities.CollectableEntities.Potions;

import dungeonmania.entities.Useable;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;

public class InvincibilityPotion extends Potion implements Useable{
    private static int limit_time = 5;
    public InvincibilityPotion(Position position, String entity_id) {
        super(position, entity_id, false, "invincibility_potion");
    }

    public static int getLimit_time() {
        return limit_time;
    }



    @Override
    public void use(Character c, String gamemode) {
        //invincibility potion can only have effet in Peaceful/Standard mode.
        c.useItem(this, gamemode);
        
    }


}

