package dungeonmania.entities.staticEntities;

public class DoorClosed implements DoorState {
    private transient Door door;
    private String json_prefix = "door_closed";

    public DoorClosed(Door door) {
        this.door = door;
    }

    @Override
    public void openDoor() {
        door.setState(door.getOpenState());
    }

    @Override
    public String getJsonPrefix() {
        return this.json_prefix;
    }
}
