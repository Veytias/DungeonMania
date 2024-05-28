package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class SwampTileTest {
    @Test
    public void basicSwampTileTest() {
        SwampTile swamptile = new SwampTile(new Position(0, 0), "0", 1);
        assertTrue(swamptile.getPosition().equals(new Position(0,0)));
        assertTrue(swamptile.getEntityId().equals("0"));
        assertTrue(swamptile.getJsonPrefix().equals("swamp_tile"));
    }
    
    // THIS TEST WORKED!
    // @Test
    // public void testSwampTileSlowsCharacter() {
    //     String gamemode = "Peaceful";
    //     Map<String, Entity> all_entities = new HashMap<>();
    //     Character c = new Character(new Position(0, 0), "Character", gamemode);
    //     SwampTile swamptile = new SwampTile(new Position(0, 2), "0", 2);
    //     SwampTile swamptile2 = new SwampTile(new Position(0, 5), "0", 2);
    //     all_entities.put("Character", c);
    //     all_entities.put("1", swamptile);
    //     all_entities.put("2", swamptile2);
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 1)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 2)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 2)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 2)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 3))); 
    //      c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 4))); 
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 5)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 5)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 5)));
    //     c.onTick(gamemode, all_entities, Direction.DOWN);
    //     assertTrue(c.getPosition().equals(new Position(0, 6)));  
    // }

    @Test
    public void testAssassinSwampTile() {
        String gamemode = "Peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Assassin a = new Assassin(new Position(5, 0), "a", gamemode);
        SwampTile swamptile = new SwampTile(new Position(4, 0), "b", 2);
        SwampTile swamptile2 = new SwampTile(new Position(2, 0), "c", 2);
        all_entities.put("Character", c);
        all_entities.put("a", a);
        all_entities.put("b", swamptile);
        all_entities.put("c", swamptile2);
        a.onTick(gamemode, all_entities, Direction.UP); 
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        a.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        a.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(3, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(2, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(2, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(2, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(1, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(0, 0)));
    }

    @Test
    public void testMercenarySwampTile() { // NOT WORKING PROPERLY FOR SOME REASON
        String gamemode = "Peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Mercenary a = new Mercenary(new Position(5, 0), "a", gamemode);
        SwampTile swamptile = new SwampTile(new Position(4, 0), "b", 2);
        SwampTile swamptile2 = new SwampTile(new Position(2, 0), "c", 2);
        all_entities.put("Character", c);
        all_entities.put("a", a);
        all_entities.put("b", swamptile);
        all_entities.put("c", swamptile2);
        a.onTick(gamemode, all_entities, Direction.UP); 
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        a.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        a.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(3, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(2, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(2, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(2, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(1, 0)));
        a.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(a.getPosition().equals(new Position(0, 0)));
    }

    @Test
    public void testZombieToastSwampTile() {
        String gamemode = "Regular";
        Map<String, Entity> all_entities = new HashMap<>();
        SwampTile swamptile = new SwampTile(new Position(0, 1), "1", 2);
        SwampTile swamptile2 = new SwampTile(new Position(1, 0), "2", 2);
        SwampTile swamptile3 = new SwampTile(new Position(1, 2), "3", 2);
        SwampTile swamptile4 = new SwampTile(new Position(2, 1), "4", 2);
        ZombieToast z = new ZombieToast(new Position(1, 1), "z", gamemode);
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        all_entities.put("1", swamptile);
        all_entities.put("2", swamptile2);
        all_entities.put("3", swamptile3);
        all_entities.put("4", swamptile4);
        all_entities.put("z", z);
        all_entities.put("Character", c);
        Position prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(!z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(!z.getPosition().equals(prevPos)); 
    }

    @Test
    public void testHydraSwampTile() {
        String gamemode = "Regular";
        Map<String, Entity> all_entities = new HashMap<>();
        SwampTile swamptile = new SwampTile(new Position(0, 1), "1", 2);
        SwampTile swamptile2 = new SwampTile(new Position(1, 0), "2", 2);
        SwampTile swamptile3 = new SwampTile(new Position(1, 2), "3", 2);
        SwampTile swamptile4 = new SwampTile(new Position(2, 1), "4", 2);
        Hydra z = new Hydra(new Position(1, 1), "z", gamemode);
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        all_entities.put("1", swamptile);
        all_entities.put("2", swamptile2);
        all_entities.put("3", swamptile3);
        all_entities.put("4", swamptile4);
        all_entities.put("z", z);
        all_entities.put("Character", c);
        Position prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(!z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        z.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(!z.getPosition().equals(prevPos)); 
    }

    @Test
    public void testSpiderSwampTile() {
        /*
        Expected movement:
        8  1  2
        7  0  3
        6  5  4
        */
        String gamemode = "Regular";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        Spider s = new Spider(new Position(1, 1), "s", gamemode);//0
        SwampTile swamptile = new SwampTile(new Position(1, 0), "1", 2);
        SwampTile swamptile2 = new SwampTile(new Position(2, 2), "2", 2);
        all_entities.put("s", s);
        all_entities.put("Character", c);
        all_entities.put("1", swamptile);
        all_entities.put("2", swamptile2);
        
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 0)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 0)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 0)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 0)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 1)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 2)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 2)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 2)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 2)));
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 2)));
    }
}
