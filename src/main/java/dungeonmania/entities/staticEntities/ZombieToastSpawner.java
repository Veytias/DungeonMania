package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.CollectableEntities.Equipment.Armour;
import dungeonmania.entities.CollectableEntities.Equipment.Sword;
import dungeonmania.entities.interfaces.Interactable;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.Character;


public class ZombieToastSpawner extends StaticEntity implements Interactable{

    private boolean is_destroyed = false;
    private int tick_track = 0;
    
    public ZombieToastSpawner(Position position, String entity_id) {
        super(position, entity_id, "zombie_toast_spawner");
    }
    
    public void destroySpawner() { 
        this.is_destroyed = true; 
    }

    public boolean getIsDestroyed() {
        return this.is_destroyed;
    }

    public void spawn_zombie_tick(Map<String, Entity> all_entities, String gamemode) {
        
        Position spawner_pos = this.getPosition();
        int entity_id = all_entities.size() + 1;
        Position position_spawn; 
        
        List<Position> adjacentPositions = spawner_pos.getAdjacentPositions();
        ArrayList<Entity> entities_north = getEntitiesInPos(adjacentPositions.get(1), all_entities);
        ArrayList<Entity> entities_south = getEntitiesInPos(adjacentPositions.get(5), all_entities);
        ArrayList<Entity> entities_east = getEntitiesInPos(adjacentPositions.get(3), all_entities);
        ArrayList<Entity> entities_west = getEntitiesInPos(adjacentPositions.get(7), all_entities);
        
        // 0 1 2
        // 7 p 3
        // 6 5 4
        if (entities_north.size() == 0) { // If north cell free, spawn there
            position_spawn = adjacentPositions.get(1);
        } else if (entities_east.size() == 0) { // Else if east cell free, spawn there
            position_spawn = adjacentPositions.get(3);
        } else if (entities_south.size() == 0) { // Else if south cell free, spawn there
            position_spawn = adjacentPositions.get(5);
        } else if (entities_west.size() == 0) { // Else if west cell free, spawn there
            position_spawn = adjacentPositions.get(7);
        } else { // If all cells free then spawn in north cell by default
            position_spawn = adjacentPositions.get(1);
        } 

        System.out.println("Zombie spawned in: " + position_spawn);
        ZombieToast spawn_zombie = new ZombieToast(position_spawn, Integer.toString(entity_id), gamemode); // Spawn zombie with certain position and entity
        Random r = new Random();
        if (r.nextInt(101) <= 5) {
            spawn_zombie.addItem(new Armour(new Position(0, 0), Integer.toString(entity_id+1)));
        }
        all_entities.put(Integer.toString(entity_id), spawn_zombie);
    }

    @Override  // This should only check for the exception and destroy the spawner
    public void interact(Map<String, Entity> all_entities) throws InvalidActionException {
        Position spawner_pos = this.getPosition();
        Character character = (Character) all_entities.get("Character");
        Position character_pos = character.getPosition();
        List<CollectableEntity> ownedItems = character.getOwnedItems();
        
        boolean contains_sword = false;
        for (CollectableEntity curr : ownedItems) {
            if (curr instanceof Sword) {
                contains_sword = true;
            }
        }
        
        List<Position> adjacentPositions = spawner_pos.getAdjacentPositions();
        // 0 1 2
        // 7 p 3
        // 6 5 4
        if (
            (character_pos.equals(adjacentPositions.get(5))) ||
            (character_pos.equals(adjacentPositions.get(1))) ||
            (character_pos.equals(adjacentPositions.get(3))) ||
            (character_pos.equals(adjacentPositions.get(7)))
         ) {
            if (contains_sword == false) {
                throw new InvalidActionException("Can't destroy without a sword");
            }
            destroySpawner();
            all_entities.remove(this.getEntityId());
            System.out.println("Zombie IS DESTROYED");
        } else {
            throw new InvalidActionException("Player not cardinally adjacent to Zombie Toast Spawner");
        }

    }

    @Override
    public void onTick(String gamemode, Map<String, Entity> all_entities, Direction moveDirection) {
        System.out.println("Tick count is: " + tick_track);
        System.out.println("Gamemode is: " + gamemode);
        tick_track++;
        if (tick_track != 0) {
            System.out.println("TICK ENTERED FIRST IF BLOCK");
            if (gamemode.equals("hard") && tick_track % 15 == 0) { 
                System.out.println("Execution has entered the hard branch");
                spawn_zombie_tick(all_entities, gamemode);
            } else if (gamemode.equals("peaceful") || gamemode.equals("standard")) {
                if (tick_track % 20 == 0) {
                    spawn_zombie_tick(all_entities, gamemode);
                }                
            }
        }
    }

    public int getTickTrack() {
        return this.tick_track;
    }



}
