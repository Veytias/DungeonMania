package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Equipment.Anduril;
import dungeonmania.entities.CollectableEntities.Equipment.Armour;
import dungeonmania.entities.CollectableEntities.Equipment.Bow;
import dungeonmania.entities.CollectableEntities.Equipment.MidnightArmour;
import dungeonmania.entities.CollectableEntities.Equipment.Sceptre;
import dungeonmania.entities.CollectableEntities.Equipment.Shield;
import dungeonmania.entities.CollectableEntities.Equipment.Sword;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class EquipmentTest {
    @Test
    public void testSanity() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "Standard");
        Anduril a = new Anduril(new Position(0, -1), "a");
        Armour ar = new Armour(new Position(0, -1), "ar");
        Bow b = new Bow(new Position(0, -1), "b");
        MidnightArmour m = new MidnightArmour(new Position(0, -1), "mna");
        Sceptre s = new Sceptre(new Position(0, -1), "s");
        Shield sh = new Shield(new Position(0, -1), "sh");
        Sword sw = new Sword(new Position(0, -1), "sw");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(a.getEntityId(), a);
        all_entities.put(ar.getEntityId(), ar);
        all_entities.put(b.getEntityId(), b);
        all_entities.put(m.getEntityId(), m);
        all_entities.put(s.getEntityId(), s);
        all_entities.put(sh.getEntityId(), sh);
        all_entities.put(sw.getEntityId(), sw);

        c.onTick("Standard", all_entities, Direction.UP);
        assertTrue(c.getOwnedItems().size() == 7);
    }
}
