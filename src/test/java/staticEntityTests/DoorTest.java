package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Door;
import dungeonmania.entities.staticEntities.DoorClosed;
import dungeonmania.entities.staticEntities.DoorOpen;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class DoorTest {
    @Test
    public void basicDoorTest() {
        Door door = new Door(new Position(0, 0), "0", "0");
        assertTrue(door.getPosition().equals(new Position(0,0)));
        assertTrue(door.getEntityId().equals("0"));
        assertTrue(door.getJsonPrefix().equals("door"));
    }

    @Test
    public void testDoorKeyRight() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(2, 0), "Character", gamemode);
        Door door = new Door(new Position(4, 0), "door", "1");
        Key key = new Key(new Position(1,0), "key", "1"); 
        c.addItem(key);
        all_entities.put("Character", c);
        all_entities.put("1", door);
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        door.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(3, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == true);
        assertTrue(door.getState() instanceof DoorClosed);    

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        door.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(4, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == false);
        assertTrue(door.getState() instanceof DoorOpen); 
    }

    @Test
    public void testDoorKeyLeft() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(6, 0), "Character", gamemode);
        Door door = new Door(new Position(4, 0), "door", "1");
        Key key = new Key(new Position(6,0), "key", "1"); 
        c.addItem(key);
        all_entities.put("Character", c);
        all_entities.put("1", door);
        
        c.onTick(gamemode, all_entities, Direction.LEFT);
        door.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(5, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == true);
        assertTrue(door.getState() instanceof DoorClosed);    

        c.onTick(gamemode, all_entities, Direction.LEFT);
        door.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(4, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == false);
        assertTrue(door.getState() instanceof DoorOpen);
    }

    @Test
    public void testDoorKeyUp() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(4, 2), "Character", gamemode);
        Door door = new Door(new Position(4, 0), "0", "1");
        Key key = new Key(new Position(4, 3), "1", "1"); 
        c.addItem(key);
        all_entities.put("Character", c);
        all_entities.put("1", door);
        all_entities.put("2", key);
        
        c.onTick(gamemode, all_entities, Direction.UP); 
        door.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(4, 1)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == true);
        assertTrue(door.getState() instanceof DoorClosed);    

        c.onTick(gamemode, all_entities, Direction.UP);
        door.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(4, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == false);
        assertTrue(door.getState() instanceof DoorOpen);    
    }

    @Test
    public void testDoorKeyDown() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(4, 1), "Character", gamemode);
        Door door = new Door(new Position(4, 3), "0", "1");
        Key key = new Key(new Position(4, 0), "1", "1"); 
        c.addItem(key);
        all_entities.put("Character", c);
        all_entities.put("1", door);
        all_entities.put("2", key);
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        door.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(4, 2)));
        assertTrue(door.getPosition().equals(new Position(4, 3)));
        assertTrue(c.getOwnedItems().contains(key) == true);
        assertTrue(door.getState() instanceof DoorClosed);    

        c.onTick(gamemode, all_entities, Direction.DOWN);
        door.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(4, 3)));
        assertTrue(door.getPosition().equals(new Position(4, 3)));
        assertTrue(c.getOwnedItems().contains(key) == false);
        assertTrue(door.getState() instanceof DoorOpen);    
    }

    @Test
    public void testDoorSunStone() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Door door = new Door(new Position(0, 1), "0", "1");
        SunStone stone = new SunStone(new Position(4, 0), "sunstone"); 
        c.addItem(stone);
        all_entities.put("Character", c);
        all_entities.put("1", door);

        assertTrue(door.getState() instanceof DoorClosed); 
        c.onTick(gamemode, all_entities, Direction.DOWN);
        door.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        assertTrue(door.getPosition().equals(new Position(0, 1)));
        assertTrue(c.getOwnedItems().contains(stone) == true);
        assertTrue(door.getState() instanceof DoorOpen);    
    }
    @Test 
    public void testDoorWrongKey() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(7, 0), "Character", gamemode);
        Door door = new Door(new Position(4, 0), "0", "1");
        Key key = new Key(new Position(7,0), "1", "2"); 
        c.addItem(key);
        all_entities.put("Character", c);
        all_entities.put("1", door);
        all_entities.put("2", key);
        
        c.onTick(gamemode, all_entities, Direction.LEFT);
        door.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(6, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == true);
        assertTrue(door.getState() instanceof DoorClosed);    

        c.onTick(gamemode, all_entities, Direction.LEFT);
        door.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(5, 0)));
        assertTrue(door.getPosition().equals(new Position(4, 0)));
        assertTrue(c.getOwnedItems().contains(key) == true);
        assertTrue(door.getState() instanceof DoorClosed);    
    }
    

}

