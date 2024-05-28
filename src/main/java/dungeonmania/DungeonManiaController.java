package dungeonmania;

import dungeonmania.DungeonPresetStructure.EntityData;
import dungeonmania.entities.Entity;
import dungeonmania.entities.CollectableEntities.Arrow;
import dungeonmania.entities.CollectableEntities.Bomb;
import dungeonmania.entities.CollectableEntities.Key;
import dungeonmania.entities.CollectableEntities.SunStone;
import dungeonmania.entities.CollectableEntities.TheOneRing;
import dungeonmania.entities.CollectableEntities.Treasure;
import dungeonmania.entities.CollectableEntities.Wood;
import dungeonmania.entities.CollectableEntities.Equipment.Anduril;
import dungeonmania.entities.CollectableEntities.Equipment.Armour;
import dungeonmania.entities.CollectableEntities.Equipment.Bow;
import dungeonmania.entities.CollectableEntities.Equipment.MidnightArmour;
import dungeonmania.entities.CollectableEntities.Equipment.Sceptre;
import dungeonmania.entities.CollectableEntities.Equipment.Shield;
import dungeonmania.entities.CollectableEntities.Equipment.Sword;
import dungeonmania.entities.CollectableEntities.Potions.HealthPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvisibilityPotion;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Character;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.entities.staticEntities.Door;
import dungeonmania.entities.staticEntities.DoorClosed;
import dungeonmania.entities.staticEntities.DoorOpen;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.entities.staticEntities.Portal;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.entities.staticEntities.ZombieToastSpawner;
import dungeonmania.entities.Useable;
import dungeonmania.entities.CollectableEntities.CollectableEntity;
import dungeonmania.entities.interfaces.Interactable;
import dungeonmania.entities.interfaces.Observer;
import dungeonmania.entities.movingEntities.MovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.goal;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.BattleBehaviors.Battle;
import dungeonmania.strategies.BattleBehaviors.InvincibleBattle;
import dungeonmania.strategies.BattleBehaviors.NoBattle;
import dungeonmania.strategies.BattleBehaviors.RegularBattle;
import dungeonmania.strategies.moveBehaviors.AdvantageMove;
import dungeonmania.strategies.moveBehaviors.Move;
import dungeonmania.strategies.moveBehaviors.MoveAwayFromCharacter;
import dungeonmania.strategies.moveBehaviors.MoveCircular;
import dungeonmania.strategies.moveBehaviors.MoveDijkstra;
import dungeonmania.strategies.moveBehaviors.MoveOut;
import dungeonmania.strategies.moveBehaviors.MoveRandom;
import dungeonmania.strategies.moveBehaviors.MoveTowardCharacter;
import dungeonmania.strategies.moveBehaviors.StandStill;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DungeonManiaController {
    private final static int max_spiders = 4;
    private final static int max_merc = 2;
    private Integer entities_spawned = 0;
    private Map<String, Entity> all_entities = new ConcurrentHashMap<>(); 
    private String gamemode;
    private String dungeonId;
    private String dungeonName;
    private int ticks;
    private Position entry = null;
    private transient goal goal;

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("standard", "peaceful", "hard");
    }

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }
    
    public Map<String, Entity> getAll_entities() {
        return all_entities;
    }
    
    public void setAll_entities(Map<String, Entity> all_entities) {
        this.all_entities = all_entities;
    }
    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        if (getGameModes().contains(gameMode) == false) throw new IllegalArgumentException("Invalid gamemode");
        this.gamemode = gameMode;
        this.ticks = 0;
        this.entities_spawned = 0;
        try {
            String json = FileLoader.loadResourceFile("/dungeons/"+dungeonName+".json");
            Gson gson = new Gson();
            DungeonPresetStructure presets = gson.fromJson(json, new TypeToken<DungeonPresetStructure>(){}.getType());
            List<EntityData> entityData = presets.getEntities();
            EntityFactory ef = new EntityFactory();
            this.entry = null;
            this.goal = null;
            this.all_entities = new ConcurrentHashMap<>(); 
            for (EntityData eData : entityData) {
                String jsonPrefix = eData.getType();
                int x = eData.getX();
                int y = eData.getY();
                String key = eData.getKey();
                String colour = eData.getColour();
                int movement_factor = eData.getMovementFactor();
                if (jsonPrefix.equals("player")) {
                    Character c = new Character(new Position(x,y), "Character", gamemode);
                    all_entities.put(c.getEntityId(), c);
                } else {
                    Entity spawned = ef.getEntity(jsonPrefix, x, y, key, colour, movement_factor, gamemode, Integer.toString(entities_spawned));
                    // If we spawn a mercenary, that location is the entry point
                    if (spawned instanceof Mercenary) {
                        this.entry = spawned.getPosition();
                    }
                    all_entities.put(spawned.getEntityId(), spawned);
                }
                entities_spawned++;
            }
        } catch(Exception e) {
            throw new IllegalArgumentException("Invalid Dungeon");
        }
        this.dungeonName = dungeonName;
        List<String> saves;
        try {
            saves = FileLoader.listFileNamesInResourceDirectory("/saves");
        } catch (IOException e) {
            saves = new ArrayList<>();
        }
        this.dungeonId = String.valueOf(saves.size()); // Id is number of saves
        // Read in goal-condition from json file
        String name = null;
        try {
            name = FileLoader.loadResourceFile("/dungeons/"+dungeonName+".json");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject(name);
        try{
            JSONObject goal_string = obj.getJSONObject("goal-condition");
            // create goal object
            goal goal = new goal(goal_string,all_entities);
            this.goal = goal;
        } catch (Exception e) {
            this.goal = null;
        }
        
        return createDungeonResponse();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        
        try {
            Writer writer = new FileWriter("saves/" + name +".json");
            Gson gson = new GsonBuilder().create();
            gson.toJson(this, writer);
            writer.close();

        } catch(Exception e) {
            throw new IllegalArgumentException("Saving error: " + e.toString());
        }

        return createDungeonResponse();
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {

        Map<String, Class<? extends Entity>> c_map = new HashMap<>();
		c_map.put("anduril", Anduril.class);
        c_map.put("armour", Armour.class);
        c_map.put("bow", Bow.class);
        c_map.put("midnight_armour", MidnightArmour.class);
        c_map.put("sceptre", Sceptre.class);
        c_map.put("shield", Shield.class);
        c_map.put("sword", Sword.class);

        c_map.put("health_potion", HealthPotion.class);
		c_map.put("invincibility_potion", InvincibilityPotion.class);
		c_map.put("invisibility_potion", InvisibilityPotion.class);

        c_map.put("one_ring", TheOneRing.class);
		
        c_map.put("arrow", Arrow.class);
		c_map.put("bomb", Bomb.class);
		c_map.put("key", Key.class);
        c_map.put("sun_stone", SunStone.class);
		c_map.put("treasure", Treasure.class);
		c_map.put("wood", Wood.class);
		
		c_map.put("assassin", Assassin.class);
		c_map.put("player", Character.class);
        c_map.put("hydra", Hydra.class);
        c_map.put("mercenary", Mercenary.class);
		c_map.put("spider", Spider.class);
		c_map.put("zombie_toast", ZombieToast.class);

		c_map.put("boulder", Boulder.class);
        c_map.put("door", Door.class);
		c_map.put("exit", Exit.class);
		c_map.put("switch", FloorSwitch.class);
		c_map.put("portal", Portal.class);
        c_map.put("swamp_tile", SwampTile.class);
		c_map.put("wall", Wall.class);
		c_map.put("zombie_toast_spawner", ZombieToastSpawner.class);

        Map<String, Class<? extends Move>> m_map = new HashMap<>();
        m_map.put("AdvantageMove", AdvantageMove.class);
        m_map.put("MoveAwayFromCharacter", MoveAwayFromCharacter.class);
        m_map.put("MoveCircular", MoveCircular.class);
        m_map.put("MoveDijkstra", MoveDijkstra.class);
        m_map.put("MoveOut", MoveOut.class);
        m_map.put("MoveRandom", MoveRandom.class);
        m_map.put("MoveTowardCharacter", MoveTowardCharacter.class);
        m_map.put("StandStill", StandStill.class);

        Map<String, Class<? extends Battle>> b_map = new HashMap<>();
        b_map.put("InvincibleBattle", InvincibleBattle.class);
        b_map.put("NoBattle", NoBattle.class);
        b_map.put("RegularBattle", RegularBattle.class);

        try {
            Gson gson = new GsonBuilder().create();
            String content = Files.readString(Paths.get("saves/" + name +".json"), StandardCharsets.US_ASCII);
            JSONObject obj = new JSONObject(content);

            JSONObject all_entities_json = obj.getJSONObject("all_entities");
            obj.remove("all_entities");

            DungeonManiaController d_temp = gson.fromJson(obj.toString(), DungeonManiaController.class);
            if (d_temp.entities_spawned != null) {
                this.entities_spawned = d_temp.entities_spawned;
            }
            if (d_temp.dungeonId != null) {
                this.dungeonId = d_temp.dungeonId;
            }
            if (d_temp.dungeonName != null) {
                this.dungeonName = d_temp.dungeonName;
            }
            if (d_temp.gamemode != null) {
                this.gamemode = d_temp.gamemode;
            }
            this.ticks = d_temp.ticks;
            
            // goals have multiple/circular dependencies

            // JSONArray observers_temp = new JSONArray();
            // JSONArray allies_temp = new JSONArray();

            Iterator<String> keys = all_entities_json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject temp_entity = (JSONObject) all_entities_json.get(key);
                String json_prefix = temp_entity.getString("json_prefix");

                if (Door.class.isAssignableFrom(c_map.get(json_prefix))) {
                    // JSONObject temp_door_open = temp_entity.getJSONObject("open");
                    // JSONObject temp_door_close = temp_entity.getJSONObject("closed");
                    JSONObject temp_door_state = temp_entity.getJSONObject("state");
                    temp_entity.remove("open");
                    temp_entity.remove("closed");
                    temp_entity.remove("state");
                    
                    Door door_temp = gson.fromJson(temp_entity.toString(), Door.class);
                    DoorOpen door_open_temp = new DoorOpen(door_temp);
                    DoorClosed doorclosed_temp = new DoorClosed(door_temp);

                    door_temp.setOpenState(door_open_temp);
                    door_temp.setClosedState(doorclosed_temp);

                    if (temp_door_state.getString("json_prefix").equals("door_open")) {
                        door_temp.setState(door_open_temp);
                    } else {
                        door_temp.setState(doorclosed_temp);
                    }
                    
                    all_entities.put(door_temp.getJsonPrefix(), door_temp);

                } else if (MovingEntity.class.isAssignableFrom(c_map.get(json_prefix))) {

                    JSONArray temp_inventory = temp_entity.getJSONArray("owned_items");
                    JSONObject move_temp = temp_entity.getJSONObject("move_behaviour");
                    temp_entity.remove("owned_items");
                    temp_entity.remove("move_behaviour");
                    
                    JSONObject battle_temp = null;
                    if (temp_entity.has("battle_behaviour")) {
                        battle_temp = temp_entity.getJSONObject("battle_behaviour");
                        temp_entity.remove("battle_behaviour");
                    }
                    
                    // if (Character.class.isAssignableFrom(c_map.get(json_prefix))) {
                    //     observers_temp = temp_entity.getJSONArray("observers");
                    //     allies_temp = temp_entity.getJSONArray("allies");
                        temp_entity.remove("observers");
                        temp_entity.remove("allies");
                    // }

                    MovingEntity my_entity = (MovingEntity) gson.fromJson(temp_entity.toString(), c_map.get(json_prefix));
                    my_entity.setMoveBehaviour(gson.fromJson(move_temp.toString(), m_map.get(move_temp.getString("json_prefix"))));
                    all_entities.put(my_entity.getEntityId(), my_entity);
                    
                    if (battle_temp != null) {
                        my_entity.setBattleBehaviour(gson.fromJson(battle_temp.toString(), b_map.get(battle_temp.getString("json_prefix"))));
                    }

                    my_entity.setOwnedItems(new ArrayList<>());
                    
                    for (int i = 0; i < temp_inventory.length(); i ++) {
                        JSONObject temp_inv_entity = (JSONObject) temp_inventory.get(i);
                        String json_prefix_inv = temp_inv_entity.getString("json_prefix");
                        my_entity.addItem((CollectableEntity) gson.fromJson(temp_inv_entity.toString(), c_map.get(json_prefix_inv)));
                    }

                } else {
                    all_entities.put(key, gson.fromJson(temp_entity.toString(), c_map.get(json_prefix)));
                }                        
            }
            
            Character c = (Character) all_entities.get("Character");
            c.setObservers(new ArrayList<>());
            c.setAllies(new ArrayList<>());

        } catch(Exception e) {
            throw new IllegalArgumentException("saveGame error: " + e.toString());
        }

        return createDungeonResponse();
    }

    /**
     * Lists file names (without extension) within a specified non-resource directory.
     * 
     * @param directory A normal directory such as "mydirectory", relative to current working directory
     * 
     * @return A list of *only* filenames with no extensions nor relative/absolute paths i.e. [maze, otherFile]
     * 
     * @throws IOException If directory path is invalid or some other sort of IO issue occurred.
     */
    public static List<String> listFileNamesInDirectoryOutsideOfResources(String directory) throws IOException {
        Path root = Paths.get(directory);
        return Files.walk(root).filter(Files::isRegularFile).map(x -> {
            String nameAndExt = x.toFile().getName();
            int extIndex = nameAndExt.lastIndexOf('.');
            return nameAndExt.substring(0, extIndex > -1 ? extIndex : nameAndExt.length());
        }).collect(Collectors.toList());
    }

    public List<String> allGames() {
        List<String> l = new ArrayList<>();
        try {
            l = listFileNamesInDirectoryOutsideOfResources("saves/");
        } catch (Exception e) {
            l = new ArrayList<>();
        }
        return l;
    }

    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        Character c = (Character) this.all_entities.get("Character");
        //use Item
        boolean in_inventory = false;
        boolean is_usable = false;
        List<Entity> toRemove = new ArrayList<Entity>();
        for (Entity e: c.getOwnedItems()) {
            if(e.getEntityId().equals(itemUsed)) {
                in_inventory = true;
                if(e instanceof Useable) {
                    is_usable = true;
                    ((Useable) e).use(c,gamemode);
                    toRemove.add(e);
                }
                else if(e instanceof Bomb) {
                    is_usable = true;
                    c.plantBomb(all_entities);
                    toRemove.add(e);
                }
            }
        }
        for (Entity e: all_entities.values()) {
            if(e.getEntityId().equals(itemUsed)){
                is_usable = true;
            }
        }
        c.getOwnedItems().removeAll(toRemove);
        // check itemId validity
        if (is_usable == false && itemUsed != null) {
            throw new IllegalArgumentException("Invalid itemID");
        }
        // check is player own this item or not
        if (in_inventory == false && itemUsed != null) {
            throw new InvalidActionException("It isn't in character's inventory");
        }
        // Character moves first
        c.onTick(this.gamemode, this.all_entities, movementDirection);
        // update observers and update them
        updateObservers(c, all_entities);

        // Spawn spider
        if (DungeonHelpers.spidersPresent(all_entities) < max_spiders) {
            if (DungeonHelpers.spawnSpider(Integer.toString(entities_spawned), gamemode, all_entities)) {
                entities_spawned++;
            }
        }

        // Spawn hydra
        if (gamemode.equals("hard") && this.ticks % 50 == 0 && this.ticks != 0) {
            DungeonHelpers.spawnHydra(Integer.toString(entities_spawned), gamemode, all_entities);
            entities_spawned++;
        }

        // Spawn mercenary/assassin
        if (DungeonHelpers.mercenaryCanSpawn(all_entities) && this.ticks % 20 == 0 && this.ticks != 0) {
            // enemies exist means can spawn if not at cap
            if (DungeonHelpers.mercenariesPresent(all_entities) < max_merc) {
                if (Objects.isNull(this.entry)) {
                    this.entry = c.getSpawnPosition();
                }
                DungeonHelpers.spawnMercenaryOrAssassin(this.entry, gamemode, all_entities, entities_spawned);
            }
        }

        // All enemies move second
        for (Entity e : this.all_entities.values()) {
            if (e instanceof Character) continue;
            e.onTick(gamemode, all_entities, movementDirection);
        }
        // update observers and update them again since might happen battles after enemies move.
        updateObservers(c, all_entities);
        // hardmode ticks zombies faster


        // battle happens
        c.performBattle(all_entities, gamemode);


        List<Entity> destoryList = new ArrayList<>();
        // check bomb is triggered or not, if yes, explode
        for (Entity e: all_entities.values()) {
            if (e instanceof Bomb) {
                destoryList = ((Bomb)e).onTick(all_entities);
            }
        }
        all_entities.values().removeAll(destoryList);
        //update goals
        if(goal != null) {
            goal.updateIsComplete();
        }
        this.ticks++;
        return createDungeonResponse();
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity e = all_entities.get(entityId);
        if (Objects.isNull(e)) throw new IllegalArgumentException("Invalid entityID");
        // check if entity implements interactable
        if (e instanceof Interactable) {
            Interactable thing = (Interactable) e;
            thing.interact(all_entities);
        }
        return createDungeonResponse();
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {

        MovingEntity c = (MovingEntity) all_entities.get("Character");
        if (buildable.equals("midnight_armour")) {
            long zombie_count = all_entities.values().stream()
                .filter(e -> ZombieToast.class.isAssignableFrom(e.getClass()))
                .map(e -> (ZombieToast) e)
                .count();
            
            if (zombie_count != 0) {
                // cannot build midnight armor
                throw new InvalidActionException("Cannot build when zombies are present: " + buildable);
            }
        }
        c.build_item(buildable, entities_spawned);
        entities_spawned++;

        return createDungeonResponse();
    }

    /**
     * Gets information about the dungeon
     */
    public DungeonResponse createDungeonResponse() {

        ArrayList<EntityResponse> entities_in_dungeon = new ArrayList<EntityResponse>();
        EntityResponse entity_obj = null;
        List<CollectableEntity> items_owned = new ArrayList<CollectableEntity>();
        ArrayList<ItemResponse> inventory = new ArrayList<ItemResponse>();
        ArrayList<String> buildables = new ArrayList<String>();
        ItemResponse item_obj = null; 
        boolean isInteractable;
        Character character = null;
        
        for (Entity curr : all_entities.values()) { // Loops through all entities 
            
            if (curr instanceof Interactable) {
                isInteractable = true;
            } else {
                isInteractable = false;
            }

            entity_obj = new EntityResponse(curr.getEntityId(), curr.getJsonPrefix(), curr.getPosition(), isInteractable);
            entities_in_dungeon.add(entity_obj); // This entity added to entities list
            
            if (curr instanceof Character) {
                character = (Character) curr;
                items_owned = character.getOwnedItems();
                for (CollectableEntity curr_item : items_owned) {
                    item_obj = new ItemResponse(curr_item.getEntityId(), curr_item.getJsonPrefix());
                    inventory.add(item_obj);
                }  
                // buildables to be done inside this loop 
                Bow bow = new Bow(null, null);
                Shield shield = new Shield(null, null);

                if (bow.check_buildable(character)) {
                    buildables.add(bow.getJsonPrefix());
                }

                if (shield.check_buildable(character)) {
                    buildables.add(shield.getJsonPrefix());
                }

                
            }
        } 
        if(goal == null) {
            DungeonResponse dungeon_obj = new DungeonResponse(dungeonId, dungeonName, entities_in_dungeon, inventory, buildables, "");
            return dungeon_obj;
        }
        
        DungeonResponse dungeon_obj = new DungeonResponse(dungeonId, dungeonName, entities_in_dungeon, inventory, buildables, goal.toString());
        return dungeon_obj;
    }


    /** attach new enemies and detach dead enemies then notify observers 
     * @param c
     * @param all_entities
     * */ 

    public void updateObservers(Character c, Map<String,Entity> all_entities) {
         // collect all enemies
         List<MovingEntity> enemies = all_entities.values().stream().filter(entity -> entity.getClass().getName().contains("movingEntities"))
                                                                    .map(entity -> (MovingEntity) entity)
                                                                    .filter(entity -> entity.getIsEnemy())
                                                                    .collect(Collectors.toList());
        // attach new enemies and detach dead enemies
        for (MovingEntity enemy: enemies) {
            if(!c.getObservers().contains((Observer)enemy)) {
                c.attach((Observer)enemy);
            }
            if(enemy.getHealth() <= 0) {
                c.detach((Observer)enemy);
            }
        }
        // notify observer change behavior based on character's state
        c.notify(c.getCurrent_state());
    }
}