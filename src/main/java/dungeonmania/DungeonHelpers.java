package dungeonmania;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Equipment.Armour;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.util.Position;

public class DungeonHelpers {
    /**
     * Generate a spawn position for spiders
     * @param all_entities
     * @return
     */

    public static Position generateSpawn(Map<String, Entity> all_entities, List<String> obstacles) {
        boolean valid_position = false;
        Random r = new Random();
        Position spawnPos = null;
        while(valid_position == false) {
            int x = r.nextInt(18);
            int y = r.nextInt(18);
            spawnPos = new Position(x, y);
            Set<Position> boulderPos = all_entities.values().stream().filter(entity ->  obstacles.contains(entity.getClass().getSimpleName()))
                                                                     .map(entity -> entity.getPosition()) //get the positions
                                                                     .collect(Collectors.toSet()); //out positions in a set
            if (boulderPos.contains(spawnPos) == false) valid_position = true;
        }
        return spawnPos;
    }

    /**
     * Count how many spiders are in the game
     * @param all_entities
     * @return
     */
    public static int spidersPresent(Map<String, Entity> all_entities) {
        List<Entity> spiders = all_entities.values().stream().filter(entity -> entity instanceof Spider)
                                                             .collect(Collectors.toList());
        return spiders.size();
    }

    /**
     * spawn a spider
     * @param id
     * @param gamemode
     * @param all_entities
     * @return
     */
    public static Boolean spawnSpider(String id, String gamemode, Map<String, Entity> all_entities) {
        Random r = new Random();
        int randomInt = r.nextInt(101);
        if (randomInt <= 5) {
            Spider s = new Spider(new Position(0,0), id, gamemode);
            Position spiderSpawnPosition = DungeonHelpers.generateSpawn(all_entities, s.getObstacles());
            Spider spawnedSpider = new Spider(spiderSpawnPosition, id, gamemode);
            all_entities.put(spawnedSpider.getEntityId(),spawnedSpider);
            return true;
        }
        return false;
    }

    /**
     * Check mercenary/assassin spawn conditions
     * @param all_entities
     * @return
     */
    public static Boolean mercenaryCanSpawn(Map<String, Entity> all_entities) {
        List<MovingEntity> enemies = all_entities.values().stream().filter(entity -> entity instanceof MovingEntity)
                                                                   .map(entity -> (MovingEntity) entity)
                                                                   .filter(entity -> entity.getIsEnemy())
                                                                   .collect(Collectors.toList());
        if (enemies.size() != 0) return true;
        return false;
    }

    /**
     * spawn a mercenary or assassin
     * @param entry
     * @param id
     * @param gamemode
     * @param all_entities
     */
    public static void spawnMercenaryOrAssassin(Position entry, String gamemode, Map<String, Entity> all_entities, Integer entities_spawned) {
        // RNG if merc or assassin
        Random r = new Random();
        int randomInt = r.nextInt(101);
        if (randomInt <= 15) {
            // 15% chance of assassin
            Assassin spawnedAssassin = new Assassin(entry, Integer.toString(entities_spawned), gamemode);
            entities_spawned++;
            if (r.nextInt(101) <= 5) {
                spawnedAssassin.addItem(new Armour(new Position(0, 0), Integer.toString(entities_spawned)));
                entities_spawned++;
            }
            all_entities.put(spawnedAssassin.getEntityId(),spawnedAssassin);
        } else {
            // 85% regular mercenary
            Mercenary spawnedMercenary = new Mercenary(entry, Integer.toString(entities_spawned), gamemode);
            entities_spawned++;
            if (r.nextInt(101) <= 5) {
                spawnedMercenary.addItem(new Armour(new Position(0, 0), Integer.toString(entities_spawned)));
                entities_spawned++;
            }
            all_entities.put(spawnedMercenary.getEntityId(),spawnedMercenary);
        }
    }

    /**
     * count number of mercenaries/assassins
     * @param all_entities
     * @return
     */
    public static int mercenariesPresent(Map<String, Entity> all_entities) {
        List<Entity> mercenaries = all_entities.values().stream().filter(entity -> entity instanceof Mercenary || entity instanceof Assassin)
                                                                 .collect(Collectors.toList());
        return mercenaries.size();
    }

    public static void spawnHydra(String id, String gamemode, Map<String, Entity> all_entities) {
        Hydra h = new Hydra(new Position(0,0), id, gamemode);
        Position hydraSpawnPosition = DungeonHelpers.generateSpawn(all_entities, h.getObstacles());
        Hydra spawnedHydra = new Hydra(hydraSpawnPosition, id, gamemode);
        all_entities.put(spawnedHydra.getEntityId(),spawnedHydra);
    }
}
