package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class FloorSwitchTest {
    @Test
    public void basicSwitchTest() {
        FloorSwitch floorswitch = new FloorSwitch(new Position(0, 0), "0");
        assertTrue(floorswitch.getPosition().equals(new Position(0,0)));
        assertTrue(floorswitch.getEntityId().equals("0"));
        assertTrue(floorswitch.getJsonPrefix().equals("switch"));
    }

    @Test
    public void testSwitchTrigger() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 0), "Character", gamemode);
        Boulder boulder = new Boulder(new Position(3, 0), "1");
        FloorSwitch floorswitch = new FloorSwitch(new Position(4, 0), "2");
        all_entities.put("Character", c);
        all_entities.put("1", boulder);
        all_entities.put("2", floorswitch);
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        //boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        floorswitch.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(2, 0)));
        assertTrue(boulder.getPosition().equals(new Position(3, 0)));
        assertTrue(floorswitch.getPosition().equals(new Position(4, 0)));
        assertTrue(floorswitch.getIsTriggered() == false);
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        floorswitch.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(3, 0)));
        assertTrue(boulder.getPosition().equals(new Position(4, 0)));
        assertTrue(floorswitch.getPosition().equals(new Position(4, 0)));
        assertTrue(floorswitch.getIsTriggered() == true);

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        floorswitch.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(4, 0)));
        assertTrue(boulder.getPosition().equals(new Position(5, 0)));
        assertTrue(floorswitch.getPosition().equals(new Position(4, 0)));
        assertTrue(floorswitch.getIsTriggered() == false);

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        boulder.onTick(gamemode, all_entities, Direction.RIGHT);
        floorswitch.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(5, 0)));
        assertTrue(boulder.getPosition().equals(new Position(6, 0)));
        assertTrue(floorswitch.getPosition().equals(new Position(4, 0)));
        assertTrue(floorswitch.getIsTriggered() == false);
        
    }
}

