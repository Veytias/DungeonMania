package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ExitTest {
     @Test
    public void basicExitTest() {
        Exit exit = new Exit(new Position(0, 0), "0");
        assertTrue(exit.getPosition().equals(new Position(0,0)));
        assertTrue(exit.getEntityId().equals("0"));
        assertTrue(exit.getJsonPrefix().equals("exit"));
    }

    @Test
    public void testCharacterReachesExitRight() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Exit exit = new Exit(new Position(2, 0), "1");
        all_entities.put("Character", c);
        all_entities.put("1", exit);
        assertTrue(exit.getCharacterExit() == false); // exit returns false by default (works as expected)
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        exit.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(1, 0)));
        assertTrue(exit.getCharacterExit() == false);

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        exit.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(exit.getCharacterExit() == true);

        
    }

    @Test
    public void testCharacterReachesExitLeft() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(4, 0), "Character", gamemode);
        Exit exit = new Exit(new Position(2, 0), "1");
        all_entities.put("Character", c);
        all_entities.put("1", exit);
        assertTrue(exit.getCharacterExit() == false); // exit returns false by default (works as expected)
        
        c.onTick(gamemode, all_entities, Direction.LEFT);
        exit.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(3, 0)));
        assertTrue(exit.getCharacterExit() == false);

        c.onTick(gamemode, all_entities, Direction.LEFT);
        exit.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(exit.getCharacterExit() == true);    
    }
    
    @Test
    public void testCharacterReachesExitUp() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(2, 2), "Character", gamemode);
        Exit exit = new Exit(new Position(2, 0), "1");
        all_entities.put("Character", c);
        all_entities.put("1", exit);
        assertTrue(exit.getCharacterExit() == false); // exit returns false by default (works as expected)
        
        c.onTick(gamemode, all_entities, Direction.UP);
        exit.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(2, 1)));
        assertTrue(exit.getCharacterExit() == false);

        c.onTick(gamemode, all_entities, Direction.UP);
        exit.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(exit.getCharacterExit() == true);    
    }

    @Test
    public void testCharacterReachesExitDown() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(2, 0), "Character", gamemode);
        Exit exit = new Exit(new Position(2, 2), "1");
        all_entities.put("Character", c);
        all_entities.put("1", exit);
        assertTrue(exit.getCharacterExit() == false); // exit returns false by default (works as expected)
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        exit.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(2, 1)));
        assertTrue(exit.getCharacterExit() == false);

        c.onTick(gamemode, all_entities, Direction.DOWN);
        exit.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(2, 2)));
        assertTrue(exit.getCharacterExit() == true);    
    }
}


