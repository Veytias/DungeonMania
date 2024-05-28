package dungeonmania.entities.CollectableEntities;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.entities.staticEntities.Portal;


public class Bomb extends CollectableEntity{
    
    private int explode_radius = 3;
    private boolean is_planted = false;

    public Bomb(Position position, String entity_id) {
        super(position, entity_id, false, "bomb");
    }
    
    public List<Entity> explode(Map<String, Entity> all_entities) {
        //get all entities inside the explode radius of the bomb
        List<Entity> entities = getEntitiesInExplodeRadius(all_entities);
        //remove item
        List<Entity> toRemove = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (!(e instanceof Character || e instanceof Exit || e instanceof Portal) ) {
                toRemove.add(e);
            }
        }
        return toRemove;

    }

    public boolean getIs_Planted() {
        return is_planted;
    }

    public void setIs_planted(boolean is_planted) {
        this.is_planted = is_planted;
    }

    public List<Entity> getEntitiesInExplodeRadius(Map<String, Entity> all_entities) {
        return all_entities.values().stream()
            .filter(e -> getPosition().distanceTo(e.getPosition()) <= explode_radius)
            .collect(Collectors.toList());
    }
    
    public List<Entity> onTick(Map<String, Entity> all_entities) {
        //collect all entities inside detect range
        List<Entity> entities = getEntitiesInExplodeRadius(all_entities);
        List<Entity> toRemove = new ArrayList<>();
        for(Entity e:entities) {
            //found a floor switch and it is been triggerd
            if(e instanceof FloorSwitch && ((FloorSwitch)e).getIsTriggered()) {
                toRemove = explode(all_entities);
            }
        }
        return toRemove;
    }


}
