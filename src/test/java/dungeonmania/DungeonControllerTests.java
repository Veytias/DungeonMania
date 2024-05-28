package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Bomb;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.CollectableEntities.Equipment.Armour;
import dungeonmania.entities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvisibilityPotion;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.movingEntities.ZombieToast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.IllegalArgumentException;
import dungeonmania.util.Position;

import static dungeonmania.TestHelpers.assertListAreEqualIgnoringOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DungeonControllerTests {
    
    @Test
    public void testNewGame() {

        DungeonManiaController controller = new DungeonManiaController();
        assertTrue(controller.getSkin() == "default");
        assertTrue(controller.getLocalisation() == "en_US");  
        assertListAreEqualIgnoringOrder(Arrays.asList("standard", "peaceful", "hard"), controller.getGameModes());
        assertTrue(DungeonManiaController.dungeons().size() > 0);
        assertTrue(DungeonManiaController.dungeons().contains("maze"));
        // loading "advanced" dungeon
        DungeonResponse newDungeon =  controller.newGame("advanced", "standard");
        assertEquals(newDungeon.getDungeonName(),"advanced");
        assertEquals(newDungeon.getGoals(),"(:zombie_toast AND :treasure)");
    }

    @Test
    public void testNewGameFail() {
        DungeonManiaController controller = new DungeonManiaController();
        // loading dungeon which doesn't exist
        assertThrows(IllegalArgumentException.class, () -> {
            controller.newGame("haha", "standard");
        });
    }
    @Test
    public void testSaveLoadGame() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("boulders", "standard");
        // create new entity
        SunStone stone = new SunStone(new Position(100, 100), "stone");
        Map<String, Entity> all_entities = controller.getAll_entities();
        all_entities.put("stone",stone);
        controller.setAll_entities(all_entities);

        // save game
        controller.saveGame("boulders");

        // delete entity
        all_entities.remove("stone");
        controller.setAll_entities(all_entities);
        assertFalse(controller.getAll_entities().containsKey("stone"));

        // load game
        controller.loadGame("boulders");
        assertTrue(controller.getAll_entities().containsKey("stone"));

    }
    
    @Test
    public void testTick() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.setGamemode("standard");
        assertEquals("standard", controller.getGamemode());
        Map<String,Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "Character", "standard");
        Spider spider= new Spider(new Position(1, 1), "spider", "standard");
        InvincibilityPotion potion = new InvincibilityPotion(new Position(0, 1), "potion");
        Bomb bomb = new Bomb(new Position(0, 2), "bomb");
        Bomb bomb2 = new Bomb(new Position(0, 5), "bomb2");
        all_entities.put("potion",potion);
        all_entities.put("Character", player);
        all_entities.put("spider", spider);
        all_entities.put("bomb", bomb);
        all_entities.put("bomb2", bomb2);
        controller.setAll_entities(all_entities);
        //move down, should collect invincibility_potion
        controller.tick(null, Direction.DOWN);
        //spider should also perform move
        assertTrue(player.getPosition().equals(new Position(0, 1)));
        assertTrue(spider.getPosition().equals(new Position(0, 0)));
        assertTrue(player.getOwnedItems().contains(potion));
        assertFalse(controller.getAll_entities().containsKey("potion"));
        assertTrue(player.getCurrent_state() == 0);

        //use invincibility_potion
        controller.tick("potion",  Direction.NONE);
        assertTrue(player.getCurrent_state() == 2);
        assertTrue(Character.getInvincible_remain_time() == 4);
        //move down for collecting bomb
        controller.tick(null, Direction.DOWN);
        assertTrue(player.getPosition().equals(new Position(0,2)));
        assertTrue(player.getOwnedItems().contains(bomb));
        assertFalse(controller.getAll_entities().containsKey("bomb"));
        assertTrue(Character.getInvincible_remain_time() == 3);
        controller.tick("bomb", Direction.NONE);
        assertFalse(player.getOwnedItems().contains(bomb));
        assertTrue(controller.getAll_entities().containsKey("bomb"));
        assertTrue(Character.getInvincible_remain_time() == 2);
        //use unexist item
        assertThrows(IllegalArgumentException.class, () -> {
            controller.tick("haha", Direction.NONE);
        });
        //use item which player don't have
        assertThrows(InvalidActionException.class, () -> {
            controller.tick("bomb2", Direction.NONE);
        });
        Character.setInvincible_remain_time(0);
        InvisibilityPotion potion2 = new InvisibilityPotion(new Position(0, 3), "potion2");
        all_entities.put("potion2",potion2);
        controller.tick(null, Direction.DOWN);
        controller.setAll_entities(all_entities);

        controller.tick("potion2",  Direction.NONE);
        assertTrue(player.getCurrent_state() == 1);
        assertTrue(Character.getInvisible_remain_time() == 4);
        Character.setInvisible_remain_time(0);
    }

    @Test
    public void testTickSpawn() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.setGamemode("hard");
        Map<String,Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(1, 1), "Character", "hard");
        all_entities.put("Character", player);
        controller.setAll_entities(all_entities);
        // move 100 ticks
        for(int i = 0; i <= 50; i++) {
            controller.tick(null, Direction.DOWN);
        }
        //collect enemies
        List<Entity> spiders = controller.getAll_entities().values().stream().filter(item -> item instanceof Spider)
                                                                                    .collect(Collectors.toList());
        List<Entity> hydras = controller.getAll_entities().values().stream().filter(item -> item instanceof Hydra)
                                                                                    .collect(Collectors.toList());
        assertTrue(spiders.size() > 0);
        assertTrue(hydras.size() > 0);                                                                   
    }

    @Test
    public void testInteract() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.setGamemode("hard");
        Map<String,Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(1, 1), "Character", "hard");
        Mercenary mercenary = new Mercenary(new Position(0, 2), "mercenary", "hard");
        Treasure gold = new Treasure(new Position(1, 0), "gold");
        all_entities.put("gold", gold);
        all_entities.put("Character", player);
        all_entities.put("mercenary", mercenary);
        controller.setAll_entities(all_entities);
        controller.tick(null, Direction.UP);

        controller.interact("mercenary");
        Mercenary mercenary2 = controller.getAll_entities().values().stream()
                                        .filter(c -> Mercenary.class.isAssignableFrom(c.getClass()))
                                        .map(c -> (Mercenary) c)
                                        .findAny()
                                        .orElse(null);
        assertFalse(mercenary2.getIsEnemy());

        assertThrows(IllegalArgumentException.class, () -> {
            controller.interact("nope");
        });
    }

    @Test
    public void testBuild1() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.setGamemode("hard");
        Map<String,Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "Character", "hard");
        // create material for bow
        Wood w = new Wood(new Position(0,1), "w");
        Wood w1 = new Wood(new Position(0,1), "w1");
        Key k = new Key(new Position(0,1), "k", "0");

        all_entities.put("Character", player);
        all_entities.put("w", w);
        all_entities.put("w1", w1);
        all_entities.put("k", k);
        controller.setAll_entities(all_entities);
        // player perform move to collect material
        controller.tick(null, Direction.DOWN);
        // build shield
        controller.build("shield");
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("shield"));
    }

    @Test
    public void testBuild2() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.setGamemode("hard");
        Map<String,Entity> all_entities = new HashMap<>();
        Character player = new Character(new Position(0, 0), "Character", "hard");
        ZombieToast zombie = new ZombieToast(new Position(5, 5), "zombie", "hard");
        // create material for bow
        Armour armour = new Armour(new Position(0,1), "armour");
        SunStone stone = new SunStone(new Position(0,1), "stone");

        all_entities.put("Character", player);
        all_entities.put("zombie", zombie);
        all_entities.put("armour", armour);
        all_entities.put("stone", stone);
        controller.setAll_entities(all_entities);
        // player perform move to collect material
        controller.tick(null, Direction.DOWN);
        // cannot build midnight_armour when zombie exist
        assertThrows(InvalidActionException.class, () -> {
            controller.build("midnight_armour");
        });
        
        all_entities.remove("zombie");
        controller.build("midnight_armour");
        assertTrue(player.getOwnedItems().size() == 1);
        assertTrue(player.getOwnedItems().get(0).getJsonPrefix().equals("midnight_armour"));
    }
}

