package movingEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MercenaryTest {
    @Test
    public void basicMercenaryTest() {
        Mercenary m = new Mercenary(new Position(0, 0), "m", "peaceful");
        assertTrue(m.getDamage() == 2);
        assertTrue(m.getDefence() == 1);
        assertTrue(m.getEntityId().equals("m"));
        assertTrue(m.getHealth() == 5);
        assertTrue(m.getIsBattling() == false);
        assertTrue(m.getIsEnemy() == true);
        assertTrue(m.getOwnedItems().equals(new ArrayList<>()));
        assertTrue(m.getPosition().equals(new Position(0, 0)));
    }

    @Test
    public void testMercenaryMovement() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        Mercenary m = new Mercenary(new Position(5, 1), "m", gamemode);
        all_entities.put("Character", c);
        all_entities.put("m", m);
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(4, 1)));
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(3, 1)));
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(2, 1)));
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(1, 1)));
    }

    @Test
    public void testMercenaryMovementObstructed() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        Mercenary m = new Mercenary(new Position(5, 0), "m", gamemode);
        Wall w0 = new Wall(new Position(3, 0), "0");
        all_entities.put("Character", c);
        all_entities.put("m", m);
        all_entities.put("0", w0);
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(4, 0)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(1, 2))); // Character move down one so mercenary should be able to move down and towards
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(4, 1)));
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(3, 1)));
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(2, 1)));
    }

    @Test
    public void testMercenaryMovementAdvantage() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Mercenary m = new Mercenary(new Position(3, 1), "m", gamemode);
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position(1, 1), "z", gamemode);
        all_entities.put("Character", c);
        all_entities.put("m", m);
        all_entities.put("z", z);
        c.performBattle(all_entities, gamemode); // Character immediately in battle since zombie and it occupy same cell
        m.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(m.getPosition().equals(new Position(1, 1))); // Mercenary in battle radis (== 3) so it should move twice
    }

    @Test
    public void testMercenaryInteract() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Mercenary m = new Mercenary(new Position(2, 1), "m", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Treasure t = new Treasure(new Position(0, 1), "t");
        all_entities.put("Character", c);
        all_entities.put("t", t);
        all_entities.put("m", m);
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        m.interact(all_entities); // Mercenary is within 2 cardinal tiles and the character has treasure
        List<MovingEntity> expected = new ArrayList<>();
        expected.add(m);
        assertTrue(c.getAllies().equals(expected)); // Mercenary in battle radis (== 3) so it should move twice
        assertTrue(m.getIsEnemy() == false);
        List<CollectableEntity> expected2 = new ArrayList<>();
        assertTrue(c.getOwnedItems().equals(expected2)); // Check items removed from inventory
    }

    @Test
    public void testMercenaryInteract2() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Mercenary m = new Mercenary(new Position(2, 1), "m", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        SunStone stone = new SunStone(new Position(0, 1), "stone");
        all_entities.put("Character", c);
        all_entities.put("stone", stone);
        all_entities.put("m", m);
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        m.interact(all_entities); // Mercenary is within 2 cardinal tiles and the character has treasure
        List<MovingEntity> expected = new ArrayList<>();
        expected.add(m);
        assertTrue(c.getAllies().equals(expected)); // Mercenary in battle radis (== 3) so it should move twice
        assertTrue(m.getIsEnemy() == false);
        List<CollectableEntity> expected2 = new ArrayList<>();
        expected2.add(stone);
        assertTrue(c.getOwnedItems().equals(expected2)); // Check sun stone is still there
    }

    @Test
    public void testMercenaryInteractTooFarExceptions() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Mercenary m = new Mercenary(new Position(3, 4), "m", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Treasure t = new Treasure(new Position(0, 1), "t");
        all_entities.put("Character", c);
        all_entities.put("t", t);
        all_entities.put("m", m);
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        assertThrows(InvalidActionException.class, () -> {
            m.interact(all_entities); // Mercenary is outside 2 cardinal tiles and the character has treasure
        });
    }

    @Test
    public void testMercenaryInteractTooPoorExceptions() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Mercenary m = new Mercenary(new Position(2, 0), "m", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        all_entities.put("Character", c);
        all_entities.put("m", m);
        assertThrows(InvalidActionException.class, () -> {
            m.interact(all_entities); // Mercenary is inside 2 cardinal tiles but no treasure
        });
    }
}
