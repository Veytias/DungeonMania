package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Arrow;
import dungeonmania.entities.CollectableEntities.Bomb;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.CollectableEntities.TheOneRing;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.CollectableEntities.Potions.HealthPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvisibilityPotion;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;


public class CollectableEntityTest {
    


    @Test
    public void testArrow() {
        //create player and entity
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);
        Arrow arrow = new Arrow(new Position(0,-1), "arrow");
        all_entities.put("arrow", arrow);
        //perform move
        player.onTick("standard", all_entities, Direction.UP);
        //check arrow is collected
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("arrow"));
        assertNull(all_entities.get("arrow"));
    }

    @Test
    public void testBomb() {
        //create player and entity
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);
        
        Bomb bomb = new Bomb(new Position(0,-1), "bomb");
        all_entities.put("bomb", bomb);
        //perform move
        player.onTick("standard", all_entities, Direction.UP);
        //check bomb is collected
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("bomb"));
        assertNull(all_entities.get("bomb"));
    }

    @Test
    public void testKey() {
        //create player and entity
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);
        
        Key key = new Key(new Position(0,-1), "key", "1");
        all_entities.put("key", key);
        //perform move
        player.onTick("standard", all_entities, Direction.UP);
         //check key is collected
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("key"));
        assertTrue(key.getKey_id().equals("1"));
        assertNull(all_entities.get("key"));
    }

    @Test
    public void testTreasure() {
         //create player and entity
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);
        
        Treasure treasure = new Treasure(new Position(0,1), "treasure");
        all_entities.put("treasure", treasure);

        //perform move
        player.onTick("standard", all_entities, Direction.DOWN);
         //check treasure is collected
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("treasure"));
        assertNull(all_entities.get("treasure"));
    }

    @Test
    public void testWood() {
        //create player and entity
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        Wood wood = new Wood(new Position(-1,0), "wood");
        all_entities.put("wood", wood);
        //perform move
        player.onTick("standard", all_entities, Direction.LEFT);
        //check wood is collected
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("wood"));
        assertNull(all_entities.get("wood"));
    }

    @Test
    public void testHealthPotion() {
        //create player and entity
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        HealthPotion health_potion = new HealthPotion(new Position(0,-1), "health_potion");
        all_entities.put("health_potion", health_potion);
        
        //perform move
        player.onTick("standard", all_entities, Direction.UP);
        //check health_potion is collected
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("health_potion"));
        assertNull(all_entities.get("health_potion"));
    }

    @Test
    public void testInvincibilityPotion() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        InvincibilityPotion invincibility_potion = new InvincibilityPotion(new Position(0,-1), "invincibility_potion");
        all_entities.put("invincibility_potion", invincibility_potion);

        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("invincibility_potion"));
        assertNull(all_entities.get("invincibility_potion"));
    }

    @Test
    public void testInvisibilityPotion() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        InvisibilityPotion invisibility_potion = new InvisibilityPotion(new Position(0,-1), "invisibility_potion");
        all_entities.put("invisibility_potion", invisibility_potion);

        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("invisibility_potion"));
        assertNull(all_entities.get("invisibility_potion"));
    }

    @Test
    public void testTheOneRing() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        TheOneRing ring = new TheOneRing(new Position(0,-1), "one_ring");
        all_entities.put("one_ring", ring);
        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("one_ring"));
        assertNull(all_entities.get("one_ring"));
    }

    @Test
    public void testSunStone() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        SunStone sunStone = new SunStone(new Position(0,-1), "sunStone");
        all_entities.put("sunStone", sunStone);

        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("sun_stone"));
        assertNull(all_entities.get("sunStone"));
    }
}
