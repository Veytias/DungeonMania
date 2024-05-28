package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Arrow;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.CollectableEntities.Equipment.Armour;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BuildTest {

    @Test
    public void testBuildBow() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        Arrow a1 = new Arrow(new Position(0,0), "a1");
        Arrow a2 = new Arrow(new Position(0,0), "a2");
        Arrow a3 = new Arrow(new Position(0,0), "a3");
        c.addItem(w);
        c.addItem(a1);
        c.addItem(a2);
        c.addItem(a3);
        entities_spawned += 4;
        
        assertTrue(c.check_buildable("bow"));
        c.build_item("bow", entities_spawned);

        c.onTick("standard", all_entities, Direction.UP);
        c.onTick("standard", all_entities, Direction.UP);

        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("bow"));
    }

    @Test
    public void testBuildBowFail() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        Arrow a1 = new Arrow(new Position(0,0), "a1");
        Arrow a2 = new Arrow(new Position(0,0), "a2");
        c.addItem(w);
        c.addItem(a1);
        c.addItem(a2);
        entities_spawned += 3;

        try {
            c.build_item("bow", entities_spawned);
        } catch (Exception e) {
            // do nothing
        }

        c.onTick("", all_entities, Direction.UP);
        
        assertTrue(!c.check_buildable("bow"));
        assertTrue(c.getOwnedItems().size() == 3);
    }

    @Test
    public void testBuildShield() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        Wood w1 = new Wood(new Position(0,0), "w1");
        Key k = new Key(new Position(0, 0), "k", "0");
        c.addItem(w);
        c.addItem(w1);
        c.addItem(k);
        entities_spawned += 3;
        
        assertTrue(c.check_buildable("shield"));
        c.build_item("shield", entities_spawned);

        c.onTick("standard", all_entities, Direction.UP);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("shield"));
    }

    @Test
    public void testBuildShield2() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        Wood w1 = new Wood(new Position(0,0), "w1");
        Treasure t = new Treasure(new Position(0, 0), "k");
        c.addItem(w);
        c.addItem(w1);
        c.addItem(t);
        entities_spawned += 3;

        assertTrue(c.check_buildable("shield"));
        c.build_item("shield", entities_spawned);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("shield"));
    }

    @Test
    public void testBuildShield3() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        Wood w1 = new Wood(new Position(0,0), "w1");
        SunStone stone = new SunStone(new Position(0, 0), "stone");
        c.addItem(w);
        c.addItem(w1);
        c.addItem(stone);
        entities_spawned += 3;

        assertTrue(c.check_buildable("shield"));
        c.build_item("shield", entities_spawned);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("shield"));
    }

    @Test
    public void testBuildShieldFail() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        Key k = new Key(new Position(0, 0), "k", "0");
        c.addItem(w);
        c.addItem(k);
        entities_spawned += 2;
        
        assertTrue(!c.check_buildable("shield"));
        try {
            c.build_item("shield", entities_spawned);
        } catch (Exception e) {
            // do nothing
        }

        c.onTick("standard", all_entities, Direction.UP);
        assertTrue(c.getOwnedItems().size() == 2);
    }

    @Test
    public void testBuildMidnightArmour() { 
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Armour a = new Armour(new Position(0,0), "w");
        SunStone s = new SunStone(new Position(0,0), "s");
        c.addItem(a);
        c.addItem(s);
        entities_spawned += 2;

        assertTrue(c.check_buildable("midnight_armour"));
        c.build_item("midnight_armour", entities_spawned);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("midnight_armour"));
    }

    @Test
    public void testBuildMidnightArmourFail() { 
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;
        
        SunStone s1 = new SunStone(new Position(0,0), "s1");
        SunStone s2 = new SunStone(new Position(0,0), "s2");
        c.addItem(s1);
        c.addItem(s2);
        entities_spawned += 2;

        assertTrue(!c.check_buildable("midnight_armour"));
        try {
            c.build_item("midnight_armour", entities_spawned);
        } catch (Exception e) {
            // do nothing
        }
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 2);
    }

    @Test
    public void testBuildSceptre1() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        Wood w = new Wood(new Position(0,0), "w");
        // Arrow a1 = new Arrow(new Position(0,0), "a1");
        // Arrow a2 = new Arrow(new Position(0,0), "a2");
        // Treasure t = new Treasure(new Position(0,0), "t");
        Key k = new Key(new Position(0,0), "k", "abc");
        SunStone s = new SunStone(new Position(0,0), "s");
        c.addItem(w);
        // c.addItem(a1);
        // c.addItem(a2);
        // c.addItem(t);
        c.addItem(k);
        c.addItem(s);
        entities_spawned += 3;

        assertTrue(c.check_buildable("sceptre"));
        c.build_item("sceptre", entities_spawned);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("sceptre"));
    }

    @Test
    public void testBuildSceptre2() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        // Wood w = new Wood(new Position(0,0), "w");
        Arrow a1 = new Arrow(new Position(0,0), "a1");
        Arrow a2 = new Arrow(new Position(0,0), "a2");
        // Treasure t = new Treasure(new Position(0,0), "t");
        Key k = new Key(new Position(0,0), "k", "abc");
        SunStone s = new SunStone(new Position(0,0), "s");
        // c.addItem(w);
        c.addItem(a1);
        c.addItem(a2);
        // c.addItem(t);
        c.addItem(k);
        c.addItem(s);
        entities_spawned += 3;

        assertTrue(c.check_buildable("sceptre"));
        c.build_item("sceptre", entities_spawned);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("sceptre"));
    }

    @Test
    public void testBuildSceptre3() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        // Wood w = new Wood(new Position(0,0), "w");
        Arrow a1 = new Arrow(new Position(0,0), "a1");
        Arrow a2 = new Arrow(new Position(0,0), "a2");
        // Treasure t = new Treasure(new Position(0,0), "t");
        Key k = new Key(new Position(0,0), "k", "abc");
        SunStone s = new SunStone(new Position(0,0), "s");
        // c.addItem(w);
        c.addItem(a1);
        c.addItem(a2);
        // c.addItem(t);
        c.addItem(k);
        c.addItem(s);
        entities_spawned += 4;

        assertTrue(c.check_buildable("sceptre"));
        c.build_item("sceptre", entities_spawned);
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 1);
        assertTrue(c.getOwnedItems().get(0).getJsonPrefix().equals("sceptre"));
    }

    @Test
    public void testBuildSceptreFail() {
        Map<String, Entity> all_entities = new HashMap<>();
        int entities_spawned = 0;

        Character c = new Character(new Position(0, 0), "Character", "standard");
        all_entities.put("Character", c);
        entities_spawned += 1;

        // Wood w = new Wood(new Position(0,0), "w");
        // Arrow a1 = new Arrow(new Position(0,0), "a1");
        // Arrow a2 = new Arrow(new Position(0,0), "a2");
        // Treasure t = new Treasure(new Position(0,0), "t");
        Key k = new Key(new Position(0,0), "k", "abc");
        SunStone s = new SunStone(new Position(0,0), "s");
        // c.addItem(w);
        // c.addItem(a1);
        // c.addItem(a2);
        // c.addItem(t);
        c.addItem(k);
        c.addItem(s);
        entities_spawned += 2;

        assertTrue(!c.check_buildable("sceptre"));
        try {
            c.build_item("sceptre", entities_spawned);
        } catch (Exception e) {
            // do nothing
        }
        c.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(c.getOwnedItems().size() == 2);
    }

}
