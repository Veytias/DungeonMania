package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
public class BoulderTest {
    @Test
    public void basicBoulderTest() {
        Boulder boulder = new Boulder(new Position(0, 0), "0");
        assertTrue(boulder.getPosition().equals(new Position(0,0)));
        assertTrue(boulder.getEntityId().equals("0"));
        assertTrue(boulder.getJsonPrefix().equals("boulder"));
    }

    @Test
    public void testBoulderMove() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new ConcurrentHashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Boulder boulder = new Boulder(new Position(2, 0), "1");
        all_entities.put("Character", c);
        all_entities.put("1", boulder);
        assertTrue(c.getPosition().equals(new Position(0, 0)));
        assertTrue(boulder.getPosition().equals(new Position(2, 0)));
         
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        //boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(1, 0)));
        assertTrue(boulder.getPosition().equals(new Position(2, 0)));
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(boulder.getPosition().equals(new Position(3, 0)));

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(3, 0)));
        assertTrue(boulder.getPosition().equals(new Position(4, 0)));
    }

    @Test
    public void testBoulderUnmoved() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Boulder boulder = new Boulder(new Position(3, 0), "1");
        Boulder boulder2 = new Boulder(new Position(4, 0), "2");
        all_entities.put("Character", c);
        all_entities.put("1", boulder);
        all_entities.put("2", boulder2);
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder2.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(1, 0)));
        assertTrue(boulder.getPosition().equals(new Position(3, 0)));
        assertTrue(boulder2.getPosition().equals(new Position(4, 0)));
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder2.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(boulder.getPosition().equals(new Position(3, 0)));
        assertTrue(boulder2.getPosition().equals(new Position(4, 0)));

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder2.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(boulder.getPosition().equals(new Position(3, 0)));
        assertTrue(boulder2.getPosition().equals(new Position(4, 0)));
    }

    
}

