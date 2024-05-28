package movingEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class SpiderTest {
    @Test
    public void basicSpiderTest() {
        Spider s = new Spider(new Position(0, 0), "s", "standard");
        assertTrue(s.getDamage() == 1);
        assertTrue(s.getDefence() == 1);
        assertTrue(s.getEntityId().equals("s"));
        assertTrue(s.getHealth() == 5);
        assertTrue(s.getIsBattling() == false);
        assertTrue(s.getIsEnemy() == true);
        assertTrue(s.getOwnedItems().equals(new ArrayList<>()));
        assertTrue(s.getPosition().equals(new Position(0, 0)));
    }

    @Test
    public void testSpiderMovement() {
        /*
        Expected movement:
        8  1  2
        7  0  3
        6  5  4
        */
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        Wall w = new Wall(new Position(1, 0), "w");
        Spider s = new Spider(new Position(1, 1), "s", gamemode);//0
        all_entities.put("s", s);
        all_entities.put("w", w);
        all_entities.put("Character", c);
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 0)));//1
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 0)));//2
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 1)));//3
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 2)));//4
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 2)));//5
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 2)));//6
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 1)));//7
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 0)));//8
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 0)));//1
    }

    @Test
    public void testSpiderMovementObstructed() {
        /*
        Expected movement1 (go right then clockwise, hit boulder go anticlockwise):
        6  B  7
        5  0  1
        4  3  2
        */
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        Boulder b = new Boulder(new Position(1, 0), "b");
        Spider s = new Spider(new Position(1, 1), "s", gamemode);//0
        all_entities.put("s", s);
        all_entities.put("b", b);
        all_entities.put("Character", c);
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 1)));//1
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 2)));//2
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 2)));//3
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 2)));//4
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 1)));//5
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 0)));//6
        s.onTick(gamemode, all_entities, Direction.UP); // Hit the boulder so we should go anticlockwise
        assertTrue(s.getPosition().equals(new Position(0, 1)));//5
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(0, 2)));//4
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(1, 2)));//3
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 2)));//2
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 1)));//1
        s.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(s.getPosition().equals(new Position(2, 0)));//7
        s.onTick(gamemode, all_entities, Direction.UP); //Boulder again, back to clockwise
        assertTrue(s.getPosition().equals(new Position(2, 1)));//1
    }
}
