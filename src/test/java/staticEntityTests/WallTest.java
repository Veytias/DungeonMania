package staticEntityTests;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class WallTest {
    @Test
    public void basicWallTest() {
        Wall wall = new Wall(new Position(0, 0), "0");
        assertTrue(wall.getPosition().equals(new Position(0,0)));
        assertTrue(wall.getEntityId().equals("0"));
        assertTrue(wall.getJsonPrefix().equals("wall"));
    }

    @Test
    public void testWallObstructsCharacter() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(3, 1), "Character", gamemode);
        all_entities.put("Character", c);
        Wall w0 = new Wall(new Position(3, 0), "0");
        Wall w1 = new Wall(new Position(4, 1), "1");
        Wall w2 = new Wall(new Position(3, 2), "2");
        Wall w3 = new Wall(new Position(2, 1), "3");
        all_entities.put("0", w0);
        all_entities.put("1", w1);
        all_entities.put("2", w2);
        all_entities.put("3", w3);
        c.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(3, 1)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(3, 1)));
        c.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(c.getPosition().equals(new Position(3, 1)));
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(c.getPosition().equals(new Position(3, 1)));
    }

}
