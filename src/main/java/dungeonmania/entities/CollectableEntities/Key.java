package dungeonmania.entities.CollectableEntities;

import dungeonmania.util.Position;

public class Key extends CollectableEntity{
    private String key_id;
    
    public Key(Position position, String entity_id, String key_id){
        super(position, entity_id, false, "key");
        this.key_id = key_id;
    }
    
    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }


    
}
