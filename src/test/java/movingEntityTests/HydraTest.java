package movingEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class HydraTest {
    @Test
    public void basicHydraTest() {
        Hydra z = new Hydra(new Position(0, 0), "h", "standard");
        assertTrue(z.getDamage() == 3);
        assertTrue(z.getDefence() == 1);
        assertTrue(z.getEntityId().equals("h"));
        assertTrue(z.getHealth() == 5);
        assertTrue(z.getIsBattling() == false);
        assertTrue(z.getIsEnemy() == true);
        assertTrue(z.getOwnedItems().equals(new ArrayList<>()));
        assertTrue(z.getPosition().equals(new Position(0, 0)));
    }

    @Test
    public void testHydraMovement() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Hydra h = new Hydra(new Position(1, 1), "h", gamemode);
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        all_entities.put("h", h);
        all_entities.put("Character", c);
        Position prevPos = h.getPosition();
        h.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(!h.getPosition().equals(prevPos));
        prevPos = h.getPosition();
        h.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(!h.getPosition().equals(prevPos));
        prevPos = h.getPosition();
        h.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(!h.getPosition().equals(prevPos));
        prevPos = h.getPosition();
        h.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(!h.getPosition().equals(prevPos));
    }

    @Test
    public void testHydraMovementObstructed() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Hydra h = new Hydra(new Position(1, 1), "h", gamemode);
        Character c = new Character(new Position(7, 7), "Character", gamemode);
        all_entities.put("h", h);
        all_entities.put("Character", c);
        Wall w0 = new Wall(new Position(1, 0), "0");
        Wall w1 = new Wall(new Position(0, 1), "1");
        Wall w2 = new Wall(new Position(2, 1), "2");
        Wall w3 = new Wall(new Position(1, 2), "3");
        all_entities.put("0", w0);
        all_entities.put("1", w1);
        all_entities.put("2", w2);
        all_entities.put("3", w3);
        h.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(h.getPosition().equals(new Position(1, 1)));
        h.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(h.getPosition().equals(new Position(1, 1)));
        h.onTick(gamemode, all_entities, Direction.LEFT);
        assertTrue(h.getPosition().equals(new Position(1, 1)));
        h.onTick(gamemode, all_entities, Direction.RIGHT);
        assertTrue(h.getPosition().equals(new Position(1, 1)));
    }
}
