package movingEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.CollectableEntities.TheOneRing;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Character;
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

public class AssassinTest {
    @Test
    public void basicAssassinTest() {
        Assassin a = new Assassin(new Position(0, 0), "a", "peaceful");
        assertTrue(a.getDamage() == 5);
        assertTrue(a.getDefence() == 1);
        assertTrue(a.getEntityId().equals("a"));
        assertTrue(a.getHealth() == 5);
        assertTrue(a.getIsBattling() == false);
        assertTrue(a.getIsEnemy() == true);
        assertTrue(a.getOwnedItems().equals(new ArrayList<>()));
        assertTrue(a.getPosition().equals(new Position(0, 0)));
    }
    @Test
    public void testAssassinMovement() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        Assassin a = new Assassin(new Position(5, 1), "a", gamemode);
        all_entities.put("Character", c);
        all_entities.put("a", a);
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(4, 1)));
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(3, 1)));
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(2, 1)));
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(1, 1)));
    }

    @Test
    public void testAssassinMovementObstructed() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 1), "Character", gamemode);
        Assassin a = new Assassin(new Position(5, 0), "a", gamemode);
        Wall w0 = new Wall(new Position(3, 0), "0");
        all_entities.put("Character", c);
        all_entities.put("a", a);
        all_entities.put("0", w0);
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(4, 0)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(1, 2))); // Character move down one so mercenary should be able to move down and towards
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(4, 1)));
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(3, 1)));
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(2, 1)));
    }

    @Test
    public void testAssassinMovementAdvantage() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Assassin a = new Assassin(new Position(3, 0), "a", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position(0, 0), "z", gamemode);
        all_entities.put("Character", c);
        all_entities.put("a", a);
        all_entities.put("z", z);
        c.performBattle(all_entities, gamemode); // Character immediately in battle since zombie and it occupy same cell
        a.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(a.getPosition().equals(new Position(1, 0))); // Assassin in battle radis (== 3) so it should move twice
    }

    @Test
    public void testAssassinInteract() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Assassin a = new Assassin(new Position(2, 2), "a", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Treasure t = new Treasure(new Position(0, 1), "t");
        TheOneRing o = new TheOneRing(new Position(0, 2), "o");
        all_entities.put("Character", c);
        all_entities.put("t", t);
        all_entities.put("o", o);
        all_entities.put("a", a);
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 2)));
        a.interact(all_entities); // Assassin is within 2 cardinal tiles and the character has treasure
        List<MovingEntity> expected1 = new ArrayList<>();
        expected1.add(a);
        assertTrue(c.getAllies().equals(expected1));
        assertTrue(a.getIsEnemy() == false);
        List<CollectableEntity> expected2 = new ArrayList<>();
        assertTrue(c.getOwnedItems().equals(expected2)); // Check items removed from inventory
    }

    @Test
    public void testAssassinInteractTooFarExceptions() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Assassin m = new Assassin(new Position(3, 5), "m", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Treasure t = new Treasure(new Position(0, 1), "t");
        TheOneRing o = new TheOneRing(new Position(0, 2), "o");
        all_entities.put("Character", c);
        all_entities.put("t", t);
        all_entities.put("m", m);
        all_entities.put("o", o);
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 2)));
        assertThrows(InvalidActionException.class, () -> {
            m.interact(all_entities); // Assassin is outside 2 cardinal tiles and the character has treasure
        });
    }

    @Test
    public void testAssassinInteractTooPoorExceptions() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Assassin a = new Assassin(new Position(2, 1), "m", gamemode);
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Treasure t = new Treasure(new Position(0, 1), "t");
        all_entities.put("Character", c);
        all_entities.put("t", t);
        all_entities.put("a", a);
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertTrue(c.getPosition().equals(new Position(0, 1)));
        assertThrows(InvalidActionException.class, () -> {
            a.interact(all_entities); // Mercenary is inside 2 cardinal tiles but missing treasures
        });
        c.removeItem(Treasure.class);
        List<CollectableEntity> expected = new ArrayList<>();
        assertTrue(c.getOwnedItems().equals(expected)); // Check item removed from inventory
        TheOneRing o = new TheOneRing(new Position(0, 0), "o");
        all_entities.put("o", o);
        c.onTick(gamemode, all_entities, Direction.UP);
        assertTrue(c.getPosition().equals(new Position(0, 0)));
        assertThrows(InvalidActionException.class, () -> {
            a.interact(all_entities); // Mercenary is inside 2 cardinal tiles but missing treasures
        });
    }
}