package dungeonmania;

import dungeonmania.DungeonPresetStructure.EntityData;
import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntities.Door;
import dungeonmania.entities.staticEntities.Portal;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EntityFactoryTest {
    @Test
    public void spawnEntityTest() {
        String gamemode = "hard";
        int entities_spawned = 0;
        try {
            String json = FileLoader.loadResourceFile("/dungeons/spawntest.json"); //custom dungeon with everything in it
            Gson gson = new Gson();
            DungeonPresetStructure presets = gson.fromJson(json, new TypeToken<DungeonPresetStructure>(){}.getType());
            List<EntityData> entityData = presets.getEntities();
            EntityFactory ef = new EntityFactory();
            Entity returnEntity = null;
            for (EntityData eData : entityData) {
                String jsonPrefix = eData.getType();
                String[] jsonPrefixParts = jsonPrefix.split("_");
                String joinedJson = String.join("", jsonPrefixParts); //get the prefix as one long string with no underscores
                int x = eData.getX();
                int y = eData.getY();
                String key = eData.getKey();
                String colour = eData.getColour();
                int movement_factor = eData.getMovementFactor();
                returnEntity = ef.getEntity(jsonPrefix, x, y, key, colour, movement_factor, gamemode, Integer.toString(entities_spawned));

                // Check shared fields of all entities have been assigned correctly
                assertTrue(returnEntity.getClass().getSimpleName().toLowerCase().contains(joinedJson));
                assertTrue(returnEntity.getPosition().equals(new Position(x, y)));
                assertTrue(returnEntity.getEntityId().equals(Integer.toString(entities_spawned)));

                // Checks for entities that have special requirements
                if (returnEntity instanceof SwampTile) {
                    SwampTile swamp = (SwampTile) returnEntity;
                    assertTrue(swamp.getMovementFactor() == movement_factor);
                } else if (returnEntity instanceof Door) {
                    Door door = (Door) returnEntity;
                    assertTrue(door.getKeyId().equals(key));
                } else if (returnEntity instanceof Portal) {
                    Portal portal = (Portal) returnEntity;
                    assertTrue(portal.getPortalColour().equals(colour));
                }
                entities_spawned++;
            }
        } catch (Exception e){}
    }
}
