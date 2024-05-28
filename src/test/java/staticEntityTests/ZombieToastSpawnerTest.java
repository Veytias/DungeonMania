package staticEntityTests;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Equipment.Sword;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.StaticEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class ZombieToastSpawnerTest {
    @Test
    public void basicSpawnerTest() {
        ZombieToastSpawner spawner = new ZombieToastSpawner(new Position(0, 0), "0");
        assertTrue(spawner.getPosition().equals(new Position(0,0)));
        assertTrue(spawner.getEntityId().equals("0"));
        assertTrue(spawner.getJsonPrefix().equals("zombie_toast_spawner"));
    }

    @Test
    public void testZombieSpawnpeaceful() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 0), "Character", gamemode);
        Position spawner_pos = new Position(5,5);
        ZombieToastSpawner spawner = new ZombieToastSpawner(spawner_pos, "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner);
        assertEquals(spawner.getPosition(), spawner_pos);

        List<Position> adjacentPositions = spawner_pos.getAdjacentPositions();
        ArrayList<Entity> entities_north = StaticEntity.getEntitiesInPos(adjacentPositions.get(1), all_entities);
        ArrayList<Entity> entities_south = StaticEntity.getEntitiesInPos(adjacentPositions.get(5), all_entities);
        ArrayList<Entity> entities_east = StaticEntity.getEntitiesInPos(adjacentPositions.get(3), all_entities);
        ArrayList<Entity> entities_west = StaticEntity.getEntitiesInPos(adjacentPositions.get(7), all_entities);
        assertEquals(entities_north.size(), 0);
        assertEquals(entities_south.size(), 0);
        assertEquals(entities_east.size(), 0); 
        assertEquals(entities_west.size(), 0);

        for (int i = 0; i < 100; i++) {
            spawner.onTick(gamemode, all_entities, Direction.UP);
        } 

        entities_north = StaticEntity.getEntitiesInPos(adjacentPositions.get(1), all_entities);
        entities_south = StaticEntity.getEntitiesInPos(adjacentPositions.get(5), all_entities);
        entities_east = StaticEntity.getEntitiesInPos(adjacentPositions.get(3), all_entities);
        entities_west = StaticEntity.getEntitiesInPos(adjacentPositions.get(7), all_entities);
        assertEquals(entities_north.size(), 2);
        assertEquals(entities_south.size(), 1);
        assertEquals(entities_east.size(), 1);
        assertEquals(entities_west.size(), 1);
    }

    @Test
    public void testZombieSpawnStandard() {
        String gamemode = "standard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 0), "Character", gamemode);
        Position spawner_pos = new Position(5,5);
        ZombieToastSpawner spawner = new ZombieToastSpawner(spawner_pos, "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner);
        assertEquals(spawner.getPosition(), spawner_pos);

        List<Position> adjacentPositions = spawner_pos.getAdjacentPositions();
        ArrayList<Entity> entities_north = StaticEntity.getEntitiesInPos(adjacentPositions.get(1), all_entities);
        ArrayList<Entity> entities_south = StaticEntity.getEntitiesInPos(adjacentPositions.get(5), all_entities);
        ArrayList<Entity> entities_east = StaticEntity.getEntitiesInPos(adjacentPositions.get(3), all_entities);
        ArrayList<Entity> entities_west = StaticEntity.getEntitiesInPos(adjacentPositions.get(7), all_entities);
        assertEquals(entities_north.size(), 0);
        assertEquals(entities_south.size(), 0);
        assertEquals(entities_east.size(), 0); 
        assertEquals(entities_west.size(), 0);

        for (int i = 0; i < 100; i++) {
            spawner.onTick(gamemode, all_entities, Direction.UP);
        } 

        entities_north = StaticEntity.getEntitiesInPos(adjacentPositions.get(1), all_entities);
        entities_south = StaticEntity.getEntitiesInPos(adjacentPositions.get(5), all_entities);
        entities_east = StaticEntity.getEntitiesInPos(adjacentPositions.get(3), all_entities);
        entities_west = StaticEntity.getEntitiesInPos(adjacentPositions.get(7), all_entities);
        assertEquals(entities_north.size(), 2);
        assertEquals(entities_south.size(), 1);
        assertEquals(entities_east.size(), 1);
        assertEquals(entities_west.size(), 1);
    }

    @Test
    public void testZombieSpawnHard() {
        String gamemode = "hard";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(1, 0), "Character", gamemode);
        Position spawner_pos = new Position(5,5);
        ZombieToastSpawner spawner = new ZombieToastSpawner(spawner_pos, "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner);
        assertEquals(spawner.getPosition(), spawner_pos);

        List<Position> adjacentPositions = spawner_pos.getAdjacentPositions();
        ArrayList<Entity> entities_north = StaticEntity.getEntitiesInPos(adjacentPositions.get(1), all_entities);
        ArrayList<Entity> entities_south = StaticEntity.getEntitiesInPos(adjacentPositions.get(5), all_entities);
        ArrayList<Entity> entities_east = StaticEntity.getEntitiesInPos(adjacentPositions.get(3), all_entities);
        ArrayList<Entity> entities_west = StaticEntity.getEntitiesInPos(adjacentPositions.get(7), all_entities);
        assertEquals(entities_north.size(), 0);
        assertEquals(entities_south.size(), 0);
        assertEquals(entities_east.size(), 0); 
        assertEquals(entities_west.size(), 0);

        for (int i = 0; i < 75; i++) {
            spawner.onTick(gamemode, all_entities, Direction.UP);
        } 

        entities_north = StaticEntity.getEntitiesInPos(adjacentPositions.get(1), all_entities);
        entities_south = StaticEntity.getEntitiesInPos(adjacentPositions.get(5), all_entities);
        entities_east = StaticEntity.getEntitiesInPos(adjacentPositions.get(3), all_entities);
        entities_west = StaticEntity.getEntitiesInPos(adjacentPositions.get(7), all_entities);
        assertEquals(entities_north.size(), 2);
        assertEquals(entities_south.size(), 1);
        assertEquals(entities_east.size(), 1);
        assertEquals(entities_west.size(), 1);
    }

    @Test
    public void testDestroySpawnerRight() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        Sword sword = new Sword(new Position(0, 0), "1");
        c.addItem(sword);
        ZombieToastSpawner spawner = new ZombieToastSpawner(new Position(3, 0), "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner); 

        c.onTick(gamemode, all_entities, Direction.RIGHT);
        assertEquals(c.getPosition(), new Position(1,0));
        assertEquals(spawner.getIsDestroyed(), false); 
        
        c.onTick(gamemode, all_entities, Direction.RIGHT);
        assertEquals(c.getPosition(), new Position(2,0));
        spawner.interact(all_entities);
        assertEquals(spawner.getIsDestroyed(), true); 
    }

    @Test
    public void testDestroySpawnerLeft() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(6, 0), "Character", gamemode);
        Sword sword = new Sword(new Position(6, 0), "1");
        c.addItem(sword);
        ZombieToastSpawner spawner = new ZombieToastSpawner(new Position(3, 0), "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner); 

        c.onTick(gamemode, all_entities, Direction.LEFT);
        assertEquals(c.getPosition(), new Position(5,0));
        assertEquals(spawner.getIsDestroyed(), false); 
        
        c.onTick(gamemode, all_entities, Direction.LEFT);
        assertEquals(c.getPosition(), new Position(4,0));
        spawner.interact(all_entities);
        assertEquals(spawner.getIsDestroyed(), true); 
    }

    @Test
    public void testDestroySpawnerUp() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(3, 3), "Character", gamemode);
        Sword sword = new Sword(new Position(3, 3), "1");
        c.addItem(sword);
        ZombieToastSpawner spawner = new ZombieToastSpawner(new Position(3, 0), "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner); 

        c.onTick(gamemode, all_entities, Direction.UP);
        assertEquals(c.getPosition(), new Position(3,2));
        assertEquals(spawner.getIsDestroyed(), false); 
        
        c.onTick(gamemode, all_entities, Direction.UP);
        assertEquals(c.getPosition(), new Position(3,1));
        spawner.interact(all_entities);
        assertEquals(spawner.getIsDestroyed(), true); 
    }

    @Test
    public void testDestroySpawnerDown() {
        String gamemode = "peaceful";
        Map<String, Entity> all_entities = new HashMap<>();
        Character c = new Character(new Position(3, 0), "Character", gamemode);
        Sword sword = new Sword(new Position(3, 0), "1");
        c.addItem(sword);
        ZombieToastSpawner spawner = new ZombieToastSpawner(new Position(3, 3), "0");
        all_entities.put("Character", c);
        all_entities.put("1", spawner); 

        c.onTick(gamemode, all_entities, Direction.DOWN); 
        assertEquals(c.getPosition(), new Position(3,1));
        assertEquals(spawner.getIsDestroyed(), false); 
        
        c.onTick(gamemode, all_entities, Direction.DOWN);
        assertEquals(c.getPosition(), new Position(3,2));
        spawner.interact(all_entities);
        assertEquals(spawner.getIsDestroyed(), true); 
    }
     

}

