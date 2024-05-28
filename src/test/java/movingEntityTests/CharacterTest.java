package movingEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CharacterTest {
    @Test
    public void basicCharacterTest() {
        Character c = new Character(new Position(0, 0), "Character", "peaceful");
        assertTrue(c.getDamage() == 2);
        assertTrue(c.getDefence() == 1);
        assertTrue(c.getEntityId().equals("Character"));
        assertTrue(c.getHealth() == 10);
        assertTrue(c.getIsBattling() == false);
        assertTrue(c.getIsEnemy() == false);
        assertTrue(c.getOwnedItems().equals(new ArrayList<>()));
        assertTrue(c.getPosition().equals(new Position(0, 0)));
        assertTrue(c.getCurrent_state() == 0);
    }

    @Test
    public void testCharacterMovement() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        all_entities.put("Character", c);
        c.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(1, 0)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(1, 1)));
        c.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(1, 1)));
    }

    @Test
    public void testCharacterMovementObstructed() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        all_entities.put("Character", c);
        Wall w0 = new Wall(new Position(1, 0), "0");
        Wall w1 = new Wall(new Position(0, 1), "1");
        Wall w2 = new Wall(new Position(2, 1), "2");
        Wall w3 = new Wall(new Position(1, 2), "3");
        all_entities.put("0", w0);
        all_entities.put("1", w1);
        all_entities.put("2", w2);
        all_entities.put("3", w3);
        c.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(1, 1)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(1, 1)));
        c.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(1, 1)));
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(1, 1)));
    }
}
