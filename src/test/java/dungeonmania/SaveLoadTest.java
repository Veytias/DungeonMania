package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.util.Position;

public class SaveLoadTest {
    @Test
    public void testSaveLoadGame() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("advanced", "standard");
        // create new entity
        SunStone stone = new SunStone(new Position(100, 100), "stone");
        Map<String, Entity> all_entities = controller.getAll_entities();
        all_entities.put("stone",stone);
        controller.setAll_entities(all_entities);

        // save game
        controller.saveGame("advanced");

        // delete entity
        all_entities.remove("stone");
        controller.setAll_entities(all_entities);
        assertFalse(controller.getAll_entities().containsKey("stone"));

        // load game
        controller.loadGame("advanced");
        assertTrue(controller.getAll_entities().containsKey("stone"));

    }
}
