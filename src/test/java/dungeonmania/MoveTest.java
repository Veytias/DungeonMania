package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.strategies.moveBehaviors.AdvantageMove;
import dungeonmania.strategies.moveBehaviors.MoveAwayFromCharacter;
import dungeonmania.strategies.moveBehaviors.MoveBehaviour;
import dungeonmania.strategies.moveBehaviors.MoveCircular;
import dungeonmania.strategies.moveBehaviors.MoveOut;
import dungeonmania.strategies.moveBehaviors.MoveRandom;
import dungeonmania.strategies.moveBehaviors.MoveTowardCharacter;
import dungeonmania.strategies.moveBehaviors.StandStill;
import dungeonmania.util.Position;

public class MoveTest {
    @Test
    public void testMoveAwayFromCharacter() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (1, 1), "z", gamemode);
        Wall w = new Wall(new Position(1, 3), "w");
        Wall w2 = new Wall(new Position(2, 0), "w2");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        all_entities.put(w.getEntityId(), w);
        all_entities.put(w2.getEntityId(), w2);
        MoveBehaviour moveAway = new MoveAwayFromCharacter();
        moveAway.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(1, 2))); // move away in y as priority
        moveAway.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(2, 2))); // blocked so will now move away in x

        c.setPosition(new Position(3, 3)); // move away should now cause y and x to decrease
        moveAway.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(2, 1))); // move away in y as priority
        moveAway.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(1, 1))); // blocked so will now move away in x
    }

    @Test
    public void testTowardCharacter() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (3, 3), "z", gamemode);
        Wall w = new Wall(new Position(3, 1), "w");
        Wall w2 = new Wall(new Position(2, 4), "w2");
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        all_entities.put(w.getEntityId(), w);
        all_entities.put(w2.getEntityId(), w2);

        MoveBehaviour moveToward = new MoveTowardCharacter();
        moveToward.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(3, 2))); // move toward in y as priority
        moveToward.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(2, 2))); // blocked so will now move toward in x

        c.setPosition(new Position(4, 4)); // move toward should now cause y and x to increase
        moveToward.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(2, 3))); // move toward in y as priority
        moveToward.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(3, 3))); // blocked so will now move toward in x
    }

    @Test
    public void testMoveOut() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (3, 3), "z", gamemode);
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);

        MoveBehaviour moveOut = new MoveOut();
        moveOut.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles()); //move up is priority
        assertTrue(z.getPosition().equals(new Position(3, 2)));

        Wall w1 = new Wall(new Position(3, 1), "w1"); //obstruction in up means move right
        all_entities.put(w1.getEntityId(), w1);
        moveOut.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(4, 2)));

        Wall w2 = new Wall(new Position(4, 1), "w2"); //obstruction in up and right means down
        Wall w3 = new Wall(new Position(5, 2), "w3");
        all_entities.put(w2.getEntityId(), w2);
        all_entities.put(w3.getEntityId(), w3);
        moveOut.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(4, 3)));

        Wall w4 = new Wall(new Position(4, 2), "w4");
        Wall w5 = new Wall(new Position(5, 3), "w5");
        Wall w6 = new Wall(new Position(4, 4), "w6");
        all_entities.put(w4.getEntityId(), w4);
        all_entities.put(w5.getEntityId(), w5);
        all_entities.put(w6.getEntityId(), w6);
        moveOut.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(3, 3))); //obstruction in up, right and down means left
    }

    @Test
    public void testStandStill() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (3, 3), "z", gamemode);
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        MoveBehaviour standStill = new StandStill();
        
        standStill.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(3, 3))); //same pos
    }

    @Test
    public void testMoveCircular() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (3, 3), "z", gamemode);
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        MoveBehaviour moveOut = new MoveOut();
        MoveBehaviour moveCircular = new MoveCircular();
    
        moveOut.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles()); //move up
        assertTrue(z.getPosition().equals(new Position(3, 2)));

        moveCircular.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(4, 2))); // Clockwise movement
        moveCircular.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(4, 3))); // Clockwise movement

        Wall w1 = new Wall(new Position(4, 4), "w1"); //obstruction should cause anticlockwise movement
        all_entities.put(w1.getEntityId(), w1);
        moveCircular.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(4, 2))); // anticlockwise movement
        moveCircular.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(3, 2))); // anticlockwise movement
    }

    @Test
    public void testMoveRandom() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (3, 3), "z", gamemode);
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        MoveBehaviour moveRandom = new MoveRandom();
        Position prevPos = z.getPosition();
        // as long as movement goes to some location not the previous it is working
        moveRandom.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(!z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        moveRandom.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(!z.getPosition().equals(prevPos));
        prevPos = z.getPosition();
        moveRandom.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(!z.getPosition().equals(prevPos));
    }

    @Test
    public void testAdvantageMove() {
        Map<String,Entity> all_entities = new ConcurrentHashMap<>();
        String gamemode = "standard";
        Character c = new Character(new Position(0, 0), "Character", gamemode);
        ZombieToast z = new ZombieToast(new Position (3, 3), "z", gamemode);
        all_entities.put(c.getEntityId(), c);
        all_entities.put(z.getEntityId(), z);
        MoveBehaviour advantageMove = new AdvantageMove();

        advantageMove.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(3, 1))); // Should move up twice

        Wall w = new Wall(new Position(3, 0), "w");
        all_entities.put(w.getEntityId(), w);
        advantageMove.move("z", z.getPosition(), all_entities, z.getSpawnPosition(), z.getObstacles());
        assertTrue(z.getPosition().equals(new Position(2, 0))); // Should move right then up since the first move can't be up due to obstruction
    }
}
