package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.*;
import dungeonmania.entities.CollectableEntities.Equipment.Sceptre;
import dungeonmania.entities.CollectableEntities.Potions.*;
import dungeonmania.entities.interfaces.*;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.entities.staticEntities.Door;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.strategies.BattleBehaviors.*;
import dungeonmania.strategies.moveBehaviors.*;
import dungeonmania.util.Direction;
import dungeonmania.util.MovementHelpers;
import dungeonmania.util.Position;

public class Character extends MovingEntity implements Subject{
    final static int regular_state = 0;
    final static int invisible_state = 1;
    final static int invincible_state = 2;
    private int current_state = regular_state;
    private List<Observer> observers = new ArrayList<>();
    private static int invincible_remain_time = 0;
    private static int invisible_remain_time = 0;
    private List<MovingEntity> allies = new ArrayList<>();
    private List<MovingEntity> allies_tier2 = new ArrayList<>();

    public Character(Position position, String entity_id, String gamemode) {
        super(false,
            false,
            false,
            10,
            2,
            1,
            position,
            new RegularBattle(),
            position,
            new MoveAwayFromCharacter(),
            Arrays.asList(new String[]{"Wall"}),
            entity_id,
            "player");
            
        if (gamemode.equals("hard")) {
            this.health = 3;
        } else if (gamemode.equals("peaceful")) {
            this.battle_behaviour = new NoBattle();
        }
    }
    
    /**
     * Unused
     */
    @Override
    public void performMove(Map<String, Entity> all_entities) {

    }
    
    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction direction) {
        performMove(all_entities, direction);
        collectItems(all_entities);
        
        if (invisible_remain_time == 0) {
            if (gamemode.equals("peaceful")) {
                this.battle_behaviour = new NoBattle();
            } else {
                this.battle_behaviour = new RegularBattle();
            }
        }
         //reduce potion remain time
        if(getInvincible_remain_time() > 0) {
            decrease_Invincible_remain_time();
        }
        if(getInvisible_remain_time() > 0) {
            decrease_invisible_remain_time();
        }
        
        sceptreEffect(all_entities);
    }

    public void performMove(Map<String, Entity> all_entities, Direction direction) {
        List<Position> movement_cells = new ArrayList<>();
        Position new_cell = this.position.translateBy(direction);
        // Check if the new_cell has a door in it
        List<Entity> doors_in_new_cell = all_entities.values().stream().filter(entity -> entity.getPosition().equals(new_cell))
                                                                       .filter(entity -> entity instanceof Door)
                                                                       .collect(Collectors.toList());
        // Check if the new_cell has a boulder in it
        List<Entity> boulders_in_new_cell = all_entities.values().stream().filter(entity -> entity.getPosition().equals(new_cell))
                                                                          .filter(entity -> entity instanceof Boulder)
                                                                          .collect(Collectors.toList());
        Position boulder_blocker_direction = new_cell.translateBy(direction);
        movement_cells.add(new_cell);
        List<Position> reachable_cells = MovementHelpers.getReachableCells(all_entities.values(), this.obstacles, movement_cells);
        if (reachable_cells.size() != 0) {
            if (boulders_in_new_cell.size() > 0) {
                List<Position> new_cell_adjacents = new_cell.getAdjacentPositions();
                List<Entity> new_cell_obstacles = all_entities.values().stream().filter(entity -> entity.getPosition().equals(new_cell))
                                                                                .filter(entity -> !(entity instanceof FloorSwitch))
                                                                                .collect(Collectors.toList());
                 List<Position> adjacent_boulder_positions = all_entities.values().stream().filter(entity -> new_cell_adjacents.contains(entity.getPosition()))
                                                                                           .filter(entity -> !(entity instanceof FloorSwitch))
                                                                                           .map(entity -> entity.getPosition())
                                                                                           .filter(position -> position.equals(boulder_blocker_direction))
                                                                                           .collect(Collectors.toList());
                if (adjacent_boulder_positions.size() != 0 && new_cell_obstacles.size() != 0) {
                    return;
                } else {
                    setPosition(new_cell);
                }
            } else if (doors_in_new_cell.size() != 0) {
                Door door = (Door) doors_in_new_cell.get(0);
                List<CollectableEntity> sunStones = getOwnedItems().stream().filter(item -> item instanceof SunStone)
                                                                                        .collect(Collectors.toList());
                // There is a door in the cell we want to move into
                // Check if we own the key for the door
                if (door.key_check(all_entities) || sunStones.size() > 0) {
                    // We own the key so we can move onto the cell of the door
                    setPosition(new_cell);
                } else {
                    // We don't own the key
                    // Check if the door has state open
                    if (door.isDoorClosed() == false) {
                        // Open then we move onto the cell
                        setPosition(new_cell);
                    } else {
                        // Closed we do not move onto the cell
                        return;
                    }
                }
            } else {
                setPosition(new_cell);
            }
        }
    }
 
    public int getCurrent_state() {
        if(invincible_remain_time > 0) {
            current_state = invincible_state;
        }
        else if(invisible_remain_time > 0) {
            current_state = invisible_state;
        } else {
            current_state = regular_state;
        }
        return current_state;
    }

    public void setCurrent_state(int current_state) {
        this.current_state = current_state;
    }

    /**
     * Make the character perform a battle with all suitable entities
     * @param all_entities
     * @param gamemode
     */
    public void performBattle(Map<String, Entity> all_entities, String gamemode) {
        // Check for enemies on same position
        List<MovingEntity> enemies = all_entities.values().stream().filter(entity -> position.equals(entity.getPosition()))
            .filter(entity -> MovingEntity.class.isAssignableFrom(entity.getClass()))
            .map(entity -> (MovingEntity) entity)
            .filter(entity -> entity.getIsEnemy())
            .collect(Collectors.toList());
            
        if (enemies.size() != 0) {
            is_battling = true;
            // battle all enemies
            for (MovingEntity enemy : enemies) {
                battle_behaviour.battle(this, enemy, getAllies());
                if (enemy.getHealth() <= 0) {
                    all_entities.values().removeIf(value->value.equals(enemy));
                    // drop all owned items
                    enemy.getOwnedItems().stream()
                        .forEach(c -> c.setPosition(enemy.getPosition()));
                    enemy.getOwnedItems().stream()
                        .forEach(c -> all_entities.put(c.getJsonPrefix(), c));

                } if (getHealth() <= 0) {
                    // character is dead, use rare items
                    for(Entity item: owned_items) {
                        if(item instanceof TheOneRing) {
                            ((TheOneRing)item).use(this,gamemode);
                            owned_items.remove(item);
                            break;
                        }
                    }
                    if (getHealth() <= 0) {
                        all_entities.remove("Character");
                    }
                    return;
                }
            }

        } else {
            is_battling = false;
        }
    }

    public void addAlly(MovingEntity e) {
        this.allies.add(e);
    }

    public void setAllies(List<MovingEntity> allies) {
        this.allies = allies;
    }

    public List<MovingEntity> getAllies() {
        return this.allies;
    }

    public void sceptreEffect(Map<String, Entity> all_entities) {
        // sceptre has an effect on all potential allies
        Sceptre s = getOwnedItems().stream()
        .filter(c -> Sceptre.class.isAssignableFrom(c.getClass())).map(c -> (Sceptre) c).findAny().orElse(null);
        
        if (s != null) {
            allies_tier2 = all_entities.values().stream()
                    .filter(e -> MovingEntity.class.isAssignableFrom(e.getClass())).map(e -> (MovingEntity) e).filter(e -> !e.getIsEnemy())
                    .filter(e -> Mercenary.class.isAssignableFrom(e.getClass()) || Assassin.class.isAssignableFrom(e.getClass())).collect(Collectors.toList());
            
            if(s.getDurability() > 0) {
                s.decreaseDurability();
                allies_tier2.stream().forEach(a -> a.setIsEnemy(false));
                
            } else {
                removeItem(s);
                allies_tier2.stream().forEach(a -> a.setIsEnemy(true));
                allies_tier2 = new ArrayList<>();
            }
        }
    }
    
    public void useItem(Entity e, String gamemode) {
        if(e instanceof HealthPotion) {
            setHealth(HealthPotion.getRecover_value());
        }
        else if(e instanceof InvincibilityPotion) {
            if (gamemode == "hard") {
            }
            else {
                if (current_state == invincible_state) {
                    invisible_remain_time = 0;
                }
                invincible_remain_time = InvincibilityPotion.getLimit_time();
                current_state = invincible_state;
                battle_behaviour = new InvincibleBattle();
            }
            //only keep latest state
            
        }
        else if (e instanceof InvisibilityPotion) {
            //only keep latest state
            if (current_state == invisible_state) {
                invincible_remain_time = 0;
            }
            invisible_remain_time = InvisibilityPotion.getLimit_time();
            current_state = invisible_state;
            battle_behaviour = new NoBattle();
        }
    }

    public void plantBomb(Map<String, Entity> all_entities) {
        Bomb bomb = getOwnedItems().stream()
            .filter(c -> Bomb.class.isAssignableFrom(c.getClass()))
            .map(c -> (Bomb) c)
            .findAny()
            .orElse(null);
        
        if (bomb != null) {
            bomb.setPosition(this.position);
            bomb.setIs_planted(true);
            all_entities.put(bomb.getEntityId(), bomb);
        }
    }
    
    public static int getInvincible_remain_time() {
        return invincible_remain_time;
    }
    public static int getInvisible_remain_time() {
        return invisible_remain_time;
    }
    public void decrease_Invincible_remain_time() {
        invincible_remain_time -= 1;
    }

    public void decrease_invisible_remain_time() {
        invisible_remain_time -= 1;
    }
    public static void setInvincible_remain_time(int invincible_remain_time) {
        Character.invincible_remain_time = invincible_remain_time;
    }
    public static void setInvisible_remain_time(int invisible_remain_time) {
        Character.invisible_remain_time = invisible_remain_time;
    }
    @Override
    public void attach(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void detach(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notify(int current_state) {
        for (Observer obs: observers) {
            obs.update(current_state);
        }
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
