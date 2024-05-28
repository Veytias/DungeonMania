package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;

public class BattleTest {

    @Test
    public void testSimpleBattle() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "standard");
        ZombieToast z = new ZombieToast(new Position(0, -1), "ztoast", "standard");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        
        assertTrue(c.getHealth() > 0);
        assertTrue(z.getHealth() <= 0);
    }

    @Test
    public void testNoBattle() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "peaceful");
        ZombieToast z = new ZombieToast(new Position(0, -1), "ztoast", "peaceful");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);

        c.onTick("peaceful", all_entities, Direction.UP);
        c.performBattle(all_entities, "peaceful");
        
        assertTrue(c.getHealth() > 0);
        assertTrue(z.getHealth() > 0);
    }

    @Test
    public void testMultiBattle() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "standard");
        ZombieToast z = new ZombieToast(new Position(0, -2), "ztoast", "standard");
        Mercenary m = new Mercenary(new Position(0, -3), "merc", "standard");
        Spider s = new Spider(new Position(0, -4), "spid", "standard");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        all_entities.put(m.getEntityId(), m);
        all_entities.put(s.getEntityId(), s);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getHealth() > 0);
        assertTrue(z.getHealth() <= 0);
        assertTrue(m.getHealth() <= 0);
        assertTrue(s.getHealth() <= 0);
    }

    @Test
    public void testMultiBattle2() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "standard");
        ZombieToast z = new ZombieToast(new Position(0, -1), "ztoast", "standard");
        Mercenary m = new Mercenary(new Position(0, -2), "merc", "standard");
        Spider s = new Spider(new Position(0, -3), "spid", "standard");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        all_entities.put(m.getEntityId(), m);
        all_entities.put(s.getEntityId(), s);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        
        assertTrue(c.getHealth() > 0);
        assertTrue(z.getHealth() <= 0);
        assertTrue(m.getHealth() > 0);
        assertTrue(s.getHealth() > 0);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        
        assertTrue(c.getHealth() > 0);
        assertTrue(z.getHealth() <= 0);
        assertTrue(m.getHealth() <= 0);
        assertTrue(s.getHealth() > 0);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        
        assertTrue(c.getHealth() > 0);
        assertTrue(z.getHealth() <= 0);
        assertTrue(m.getHealth() <= 0);
        assertTrue(s.getHealth() <= 0);
    }

    @Test
    public void testHydraBattle() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "standard");
        Hydra h = new Hydra(new Position(0, -1), "h", "standard");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(h.getEntityId(), h);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        
        assertTrue((c.getHealth() > 0 && h.getHealth() <= 0) || (c.getHealth() <= 0 && h.getHealth() > 0));
    }

    @Test
    public void testAssasinBattle() {
        Map<String, Entity> all_entities = new HashMap<>();

        Character c = new Character(new Position(0, 0), "Character", "standard");
        Assassin a = new Assassin(new Position(0, -1), "a", "standard");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(a.getEntityId(), a);

        c.onTick("standard", all_entities, Direction.UP);
        c.performBattle(all_entities, "standard");
        
        assertTrue(c.getHealth() > 0 && a.getHealth() <= 0);
    }

}
