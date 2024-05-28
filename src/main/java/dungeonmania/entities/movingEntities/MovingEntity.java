package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Bomb;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.Equipment.Bow;
import dungeonmania.entities.CollectableEntities.Equipment.Buildable;
import dungeonmania.entities.CollectableEntities.Equipment.MidnightArmour;
import dungeonmania.entities.CollectableEntities.Equipment.Sceptre;
import dungeonmania.entities.CollectableEntities.Equipment.Shield;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.strategies.BattleBehaviors.BattleBehaviour;
import dungeonmania.strategies.moveBehaviors.MoveBehaviour;
import dungeonmania.util.Position;

public class MovingEntity extends Entity{
    
    protected boolean is_enemy;
    protected boolean is_battling;
    protected boolean is_obstacle;
    protected int health;
    protected int damage;
    protected int defence;
    protected BattleBehaviour battle_behaviour;
    protected Position spawn_position;
    protected List<CollectableEntity> owned_items = new ArrayList<>();
    protected MoveBehaviour move_behaviour;
    protected List<String> obstacles;

    public MovingEntity(boolean is_enemy,
                        boolean is_battling,
                        boolean is_obstacle,
                        int health,
                        int damage,
                        int defence,
                        Position position,
                        BattleBehaviour battle_behaviour,
                        Position spawn_position,
                        MoveBehaviour move_behaviour,
                        List<String> obstacles,
                        String entity_id,
                        String json_prefix) {
        super(position, entity_id, json_prefix);
        this.is_enemy = is_enemy;
        this.is_battling = is_battling;
        this.is_obstacle = is_obstacle;
        this.health = health;
        this.damage = damage;
        this.defence = defence;
        this.battle_behaviour = battle_behaviour;
        this.spawn_position = spawn_position;
        this.move_behaviour = move_behaviour;
        this.obstacles = obstacles;
    }
    public BattleBehaviour getBattle_behaviour() {
        return battle_behaviour;
    }
    public MoveBehaviour getMove_behaviour() {
        return move_behaviour;
    }
    public Position getSpawnPosition() {
        return this.spawn_position;
    }

    public List<String> getObstacles() {
        return this.obstacles;
    }

    public int getDamage() {
        return this.damage;
    }
    
    public int getDefence() {
        return this.defence;
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public boolean getIsBattling() {
        return this.is_battling;
    }
    
    public boolean getIsEnemy() {
        return this.is_enemy;
    }

    public void setIsEnemy(boolean is_enemy) {
        this.is_enemy = is_enemy;
    }
    
    public List<CollectableEntity> getOwnedItems() {
        return this.owned_items;
    }

    public void setOwnedItems(List<CollectableEntity> owned_items) {
        this.owned_items = owned_items;
    }
    
    public void setHealth(int new_health) {
        this.health = new_health;
    }
    
    public void removeItem(CollectableEntity item) {
        if(owned_items.contains(item)) {
            owned_items.remove(item);
        }
    }

    public void removeItem(Class<? extends CollectableEntity> item_class) {
        CollectableEntity item = owned_items.stream()
            .filter(c -> c.getClass().equals(item_class))
            .findAny()
            .orElse(new CollectableEntity(null, null, false, null));
        
        removeItem(item);
    }
    
    public void addItem(CollectableEntity item) {
        owned_items.add(item);
    }

    public void collectItems(Map<String, Entity> all_entities) {
        ArrayList<Entity> entities = getEntitiesInPos(getPosition(), all_entities);

        for (Entity e : entities) {
            if (e instanceof CollectableEntity) {
                if(e instanceof Bomb && ((Bomb)e).getIs_Planted() == true)continue;
                if(e instanceof Key && check_have_key())continue;
                owned_items.add((CollectableEntity) e);
                all_entities.values().removeIf(value->value.equals(e));
            }
        }
    }
    
    public void build_item(String item, int entities_spawned) throws IllegalArgumentException {
        if (item.equals("bow")) {
            build_item(new Bow(new Position(0, 0), Integer.toString(entities_spawned)));

        } else if (item.equals("shield")) {
            build_item(new Shield(new Position(0, 0), Integer.toString(entities_spawned)));

        } else if (item.equals("sceptre")) {
            build_item(new Sceptre(new Position(0, 0), Integer.toString(entities_spawned)));

        } else if (item.equals("midnight_armour")) {
            build_item(new MidnightArmour(new Position(0, 0), Integer.toString(entities_spawned)));

        } else {
            throw new IllegalArgumentException("Is not a buildable item: " + item);
        }
    }

    public boolean check_buildable(String item) throws IllegalArgumentException {
        if (item.equals("bow")) {
            Bow b = new Bow(new Position(0, 0), "0");
            return b.check_buildable(this);

        } else if (item.equals("shield")) {
            Shield s = new Shield(new Position(0, 0), "0");
            return s.check_buildable(this);

        } else if (item.equals("sceptre")) {
            Sceptre s = new Sceptre(new Position(0, 0), "0");
            return s.check_buildable(this);

        } else if (item.equals("midnight_armour")) {
            MidnightArmour m = new MidnightArmour(new Position(0, 0), "0");
            return m.check_buildable(this);

        } else {
            throw new IllegalArgumentException("Is not a buildable item: " + item);
        }
    }


    public boolean swamp_check(Map<String, Entity> all_entities, int swamp_tick) {
        List<Entity> swamptile_in_current_cell = getEntitiesInPos(this.getPosition(), all_entities);                                                                
        SwampTile swamp_tile = null;
        int swamp_movementfactor = 0;
        for (Entity swamp_track : swamptile_in_current_cell) {
            if (swamp_track instanceof SwampTile) {
                swamp_tile = (SwampTile) swamp_track;
                swamp_movementfactor = swamp_tile.getMovementFactor();
            }
        }

        if (swamp_tick < swamp_movementfactor) {
            return true;
        }
        return false;
    }

    public void build_item(Buildable item) {
        item.build_entity(this);
    }

    public BattleBehaviour getBattleBehaviour() {
        return this.battle_behaviour;
    }
    
    public void performMove(Map<String, Entity> all_entities) {
        
    };

    public void setMoveBehaviour(MoveBehaviour move_behaviour) {
        this.move_behaviour = move_behaviour;
    }

    public void setBattleBehaviour(BattleBehaviour battle_behaviour) {
        this.battle_behaviour = battle_behaviour;
    }

    public void getHit(int health_hit) {
        this.health -= health_hit;
    }

    public boolean check_have_key() {
        List<CollectableEntity> keys = getOwnedItems().stream().filter(item -> item instanceof Key)
                                                                                        .collect(Collectors.toList());
        if(keys.size() == 0){
            return false;
        }
        else {
            return true;
        }
    }
}
