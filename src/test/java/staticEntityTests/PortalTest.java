package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Portal;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class PortalTest {
    @Test
    public void basicPortalTest() {
        Portal portal = new Portal(new Position(0, 0), "0", "BLUE");
        assertTrue(portal.getPosition().equals(new Position(0,0)));
        assertTrue(portal.getEntityId().equals("0"));
        assertTrue(portal.getJsonPrefix().equals("portal"));
    }

    @Test
    public void testPortalTeleportRight() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 0), "Character", gamemode);
        Portal portal = new Portal(new Position(3, 0), "1", "BLUE");
        Portal portal2 = new Portal(new Position(6, 0), "2", "BLUE"); 
        all_entities.put("Character", c);
        all_entities.put("1", portal);
        all_entities.put("2", portal2);
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));  
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        portal.onTick(gamemode, all_entities, Direction.RIGHT); 
        assertTrue(c.getPosition().equals(new Position(7, 0)));   
    }

    @Test
    public void testPortalTeleportLeft() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(8, 0), "Character", gamemode);
        Portal portal = new Portal(new Position(3, 0), "1", "BLUE");
        Portal portal2 = new Portal(new Position(6, 0), "2", "BLUE"); 
        all_entities.put("Character", c);
        all_entities.put("1", portal);
        all_entities.put("2", portal2);
        
        c.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(7, 0)));  
        
        c.onTick(gamemode, all_entities, Direction.LEFT);
        portal2.onTick(gamemode, all_entities, Direction.LEFT); 
        assertTrue(c.getPosition().equals(new Position(2,0)));   
    }
    
    @Test
    public void testPortalTeleportUp() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(3, 2), "Character", gamemode);
        Portal portal = new Portal(new Position(3, 0), "1", "BLUE");
        Portal portal2 = new Portal(new Position(6, 1), "2", "BLUE"); 
        all_entities.put("Character", c);
        all_entities.put("1", portal);
        all_entities.put("2", portal2);
        
        c.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(3, 1)));  
        
        c.onTick(gamemode, all_entities, Direction.UP);
        portal.onTick(gamemode, all_entities, Direction.UP); 
        assertTrue(c.getPosition().equals(new Position(6, 0)));   
    }
    
    @Test
    public void testPortalTeleportDown() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(3, 0), "Character", gamemode);
        Portal portal = new Portal(new Position(3, 2), "1", "BLUE");
        Portal portal2 = new Portal(new Position(6, 0), "2", "BLUE"); 
        all_entities.put("Character", c);
        all_entities.put("1", portal);
        all_entities.put("2", portal2);
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(3, 1)));  
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        portal.onTick(gamemode, all_entities, Direction.DOWN); 
        assertTrue(c.getPosition().equals(new Position(6, 1)));   
    }
    
    @Test
    public void testPortalTeleportMultiple() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(3, 0), "Character", gamemode);
        Portal portal = new Portal(new Position(3, 2), "1", "BLUE");
        Portal portal2 = new Portal(new Position(6, 2), "2", "BLUE"); 
        all_entities.put("Character", c);
        all_entities.put("1", portal);
        all_entities.put("2", portal2);
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        portal.onTick(gamemode, all_entities, Direction.DOWN);   // Make sure teleport has no effect when in adjacent cell
        assertTrue(c.getPosition().equals(new Position(3, 1)));  // Moves down one cell to (3,1)
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        portal.onTick(gamemode, all_entities, Direction.DOWN); 
        assertTrue(c.getPosition().equals(new Position(6,3))); // Moves into cell and hence gets teleported
        
        portal2.onTick(gamemode, all_entities, Direction.UP); 
        assertTrue(c.getPosition().equals(new Position(6, 3))); // Make sure teleport has no effect when in adjacent cell
        c.onTick(gamemode, all_entities, Direction.UP);         
        portal2.onTick(gamemode, all_entities, Direction.UP);  // Move back up to north of first portal
        assertTrue(c.getPosition().equals(new Position(3, 1))); 
    }
}

