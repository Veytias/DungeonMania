package dungeonmania.entities.CollectableEntities.Potions;

import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.util.Position;

public class Potion extends CollectableEntity{
   private boolean picked_up;
   
   public Potion(Position position, String entity_id, boolean is_obstacle, String json_prefix) {
      super(position, entity_id, is_obstacle, json_prefix);
      this.picked_up = false;
   }

   public boolean getPickedUp() {
      return this.picked_up;
   }
}
