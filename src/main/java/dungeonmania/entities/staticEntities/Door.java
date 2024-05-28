package dungeonmania.entities.staticEntities;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;

public class Door extends StaticEntity{
    
    /** The closed state */
	private DoorState closed;
	
	/** The open state */
	private DoorState open;
	
	/** The current state */  
	private DoorState state;

    // Door's ID
    private String key_id;

    private Key key;

    public Door(Position position, String entity_id, String key_id) {
        super(position, entity_id, "door");
        this.key_id = key_id;
        closed = new DoorClosed(this); // Create a new closed state
        open = new DoorOpen(this);     // Create a new open state
        state = closed;                // Set default state to closed (since doors are always closed initially)
    }

    public void setState(DoorState state){
        this.state = state;		
    }

    public void setOpenState(DoorState open) {
        this.open = open;
    }

    public void setClosedState(DoorState closed) {
        this.closed = closed;
    }

    public DoorState getState() {
        return state; 
    }

    public DoorState getOpenState() {
    	return open;
    } 
  
    public String getKeyId() {
        return this.key_id;
    }

    public void setDoorId(String key_id) {
        this.key_id = key_id;
    }

    public void setIsOpen() {
        state.openDoor();
    }

    public boolean isDoorClosed() {
    	if (state instanceof DoorClosed) {
    		return true;
    	}
    	return false;
    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction direction) {
        open(all_entities, direction);
    }

    public boolean key_check(Map<String, Entity> all_entities) {
        
        Character character = (Character) all_entities.get("Character");
        key = character.getOwnedItems().stream()
            .filter(c -> Key.class.isAssignableFrom(c.getClass()))
            .map(c -> (Key) c)
            .filter(c -> c.getKey_id().equals(getKeyId()))
            .findAny()
            .orElse(null);

        if (key == null) {
            return false;
        } else {
            return true;
        }               
    }
    
    public void open(Map<String, Entity> all_entities, Direction movementDirection) {
        
        Position door_pos = this.getPosition();
        Character character = (Character) all_entities.get("Character");

        Position character_pos = character.getPosition();
        boolean key_track = key_check(all_entities); 
        
        //collect sun stone in owned items list
        List<CollectableEntity> sumStones = character.getOwnedItems().stream().filter(item -> item instanceof SunStone)
                                                                              .collect(Collectors.toList());
        // 0 1 2
        // 7 p 3
        // 6 5 4
        if (key_track == true || sumStones.size() > 0) {// Check if the key is in owned items of character
            if (character_pos.equals(door_pos)) {
                setIsOpen();
                // If we have sun stone, we can open door without any cost
                if(sumStones.size() > 0);
                // otherwise we consume key to open door
                else{
                    character.removeItem(key);        
                } 
                  
            }
        }    
        
    }



}
