package dungeonmania.entities.CollectableEntities.Potions;

import dungeonmania.util.Position;
import dungeonmania.entities.Useable;
import dungeonmania.entities.movingEntities.Character;


public class HealthPotion extends Potion implements Useable{
    private static int recover_value = 10;

    public HealthPotion(Position position, String entity_id) {
        super(position, entity_id, false, "health_potion");
    }

    public static int getRecover_value() {
        return recover_value;
    }

    @Override
    public void use(Character c, String gamemode) {
        c.useItem(this, gamemode);
    }



}
