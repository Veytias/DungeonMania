package dungeonmania.entities.staticEntities;

public class DoorOpen implements DoorState {
    private String json_prefix = "door_open";
    
    public DoorOpen(Door door) {
        
    }

    @Override
    public void openDoor() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getJsonPrefix() {
        return this.json_prefix;
    }
}
