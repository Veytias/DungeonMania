package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Bomb;
import dungeonmania.entities.CollectableEntities.Potions.HealthPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvisibilityPotion;
import dungeonmania.entities.interfaces.Observer;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.entities.staticEntities.Portal;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.strategies.moveBehaviors.MoveAwayFromCharacter;
import dungeonmania.strategies.moveBehaviors.MoveCircular;
import dungeonmania.strategies.moveBehaviors.MoveDijkstra;
import dungeonmania.strategies.moveBehaviors.MoveOut;
// import dungeonmania.strategies.moveBehaviors.MoveTowardCharacter;
import dungeonmania.strategies.moveBehaviors.StandStill;


public class UseableEntityTest {
    final static int regular_state = 0;
    final static int invisible_state = 1;
    final static int invincible_state = 2; 
    @Test
    public void testUseHealthPotion() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        HealthPotion health_potion = new HealthPotion(new Position(0,-1), "health_potion");
        all_entities.put("health_potion", health_potion);
        
        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("health_potion"));
        player.setHealth(1);

        assertTrue(player.getHealth() == 1);
        health_potion.use(player, "standard");
        assertTrue(player.getHealth() == 10);
    }

    @Test
    public void testUseInvincibilityPotion1() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char","standard");
        all_entities.put("char", player);

        InvincibilityPotion invincibility_potion = new InvincibilityPotion(new Position(0,-1), "invincibility_potion");
        all_entities.put("invincibility_potion", invincibility_potion);

        Spider spider = new Spider(new Position(2,2), "spider","peaceful");
        all_entities.put("spider", spider);
        player.attach((Observer)spider);
        //perform move
        player.onTick("standard", all_entities, Direction.UP);
        
        assertTrue(spider.getMove_behaviour() instanceof MoveOut);
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("invincibility_potion"));
        assertTrue(player.getCurrent_state() == 0);
        invincibility_potion.use(player, "standard");
        player.notify(player.getCurrent_state());
        assertTrue(player.getCurrent_state() == 2);
        assertTrue(spider.getMove_behaviour() instanceof MoveAwayFromCharacter);
        Character.setInvincible_remain_time(0);
    }

    @Test
    public void testUseInvincibilityPotion2() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "hard");
        all_entities.put("char", player);

        InvincibilityPotion invincibility_potion = new InvincibilityPotion(new Position(0,-1), "invincibility_potion");
        all_entities.put("invincibility_potion", invincibility_potion);

        Spider spider = new Spider(new Position(2,2), "spider","peaceful");
        all_entities.put("spider", spider);
        player.attach((Observer)spider);
        //perform move
        player.onTick("hard", all_entities, Direction.UP);

        assertTrue(spider.getMove_behaviour() instanceof MoveOut);
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("invincibility_potion"));
        assertTrue(player.getCurrent_state() == regular_state);
        invincibility_potion.use(player, "hard");
        player.notify(player.getCurrent_state());
        assertTrue(player.getCurrent_state() == regular_state);
        assertTrue(spider.getMove_behaviour() instanceof MoveCircular);
        Character.setInvincible_remain_time(0);
    }

    @Test
    public void testUseInvisibilityPotion() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);

        InvisibilityPotion invisibility_potion = new InvisibilityPotion(new Position(0,-1), "invisibility_potion");
        all_entities.put("invisibility_potion", invisibility_potion);

        Mercenary mercenary = new Mercenary(new Position(2,2), "mercenary","standard");
        all_entities.put("mercenary", mercenary);
        player.attach((Observer)mercenary);

        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(mercenary.getMove_behaviour() instanceof MoveDijkstra);
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("invisibility_potion"));
        assertTrue(player.getCurrent_state() == regular_state);
        invisibility_potion.use(player,"standard");
        player.notify(player.getCurrent_state());
        assertTrue(player.getCurrent_state() == invisible_state);
        assertTrue(mercenary.getMove_behaviour() instanceof StandStill);
        Character.setInvisible_remain_time(0);
    }
    @Test
    public void testPlantBomb() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);
        
        Bomb bomb = new Bomb(new Position(0,-1), "bomb");
        all_entities.put("bomb", bomb);

        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertNull(all_entities.get("bomb"));
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("bomb"));
        player.plantBomb(all_entities);
        assertNotNull(all_entities.get("bomb"));
    }

    @Test
    public void testBombExplode() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "standard");
        all_entities.put("char", player);
        
        Bomb bomb = new Bomb(new Position(0,-1), "bomb");
        all_entities.put("bomb", bomb);

        Wall wall = new Wall(new Position(1,1), "wall");
        all_entities.put("wall", wall);

        Exit e = new Exit(new Position(-1,1), "e");
        all_entities.put("e", e);

        Portal portal = new Portal(new Position(1,-1), "portal","1");
        all_entities.put("portal", portal);
        
        //perform move
        player.onTick("standard", all_entities, Direction.UP);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("bomb"));
        assertNull(all_entities.get("bomb"));

        //all entity still there
        assertTrue(all_entities.containsKey("char"));
        assertTrue(all_entities.containsKey("wall"));
        assertTrue(all_entities.containsKey("e"));
        assertTrue(all_entities.containsKey("portal"));

        //trigger switch
        FloorSwitch switch1 = new FloorSwitch(new Position(0,-2), "switch");
        switch1.setIsTriggered(true);
        all_entities.put("switch", switch1);
        //expected List with toRemove entities
        List<Entity> toRemove = new ArrayList<>();
        toRemove.add(bomb);
        toRemove.add(wall);
        toRemove.add(switch1);

        //plantBomb and trigger it
        player.plantBomb(all_entities);
        bomb.onTick(all_entities);
        //check does it destory thing as expect
        assertEquals(toRemove, bomb.onTick(all_entities));  
    }

    /** 
    @Test
    public void testUseTheOneRing() {
        Map<String, Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "char", "");
        all_entities.put("char", player);

        TheOneRing ring = new TheOneRing(new Position(0,1), "ring");
        all_entities.put("ring", ring);

        //perform move
        player.onTick(null, all_entities, Direction.DOWN);

        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("one_ring"));
        assertTrue(player.getHealth() == 10);

        player.setHealth(0);
        assertTrue(player.getHealth() <= 0);
        player.onTick("", all_entities, Direction.UP);

        assertTrue(player.getHealth() == 10);
        assertTrue(player.getOwnedItems().size() == 0);
    }*/

}
