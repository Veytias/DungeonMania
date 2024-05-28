package dungeonmania;

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
import dungeonmania.entities.CollectableEntities.Equipment.MidnightArmour;
import dungeonmania.entities.CollectableEntities.Equipment.Sceptre;
import dungeonmania.entities.CollectableEntities.Equipment.Sword;
import dungeonmania.entities.CollectableEntities.Potions.HealthPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvincibilityPotion;
import dungeonmania.entities.CollectableEntities.Potions.InvisibilityPotion;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mercenary;
import dungeonmania.entities.movingEntities.Spider;
import dungeonmania.entities.movingEntities.ZombieToast;
import dungeonmania.entities.staticEntities.Boulder;
import dungeonmania.entities.staticEntities.Door;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.entities.staticEntities.Portal;
import dungeonmania.entities.staticEntities.SwampTile;
import dungeonmania.entities.staticEntities.Wall;
import dungeonmania.entities.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Position;

public class EntityFactory {
    public Entity getEntity(String jsonPrefix, int x, int y, String key, String colour, int movement_factor, String gamemode, String entity_id) {
        if (jsonPrefix.equals("wall")) {
            return new Wall(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("exit")) {
            return new Exit(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("boulder")) {
            return new Boulder(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("switch")) {
            return new FloorSwitch(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("door")) {
            return new Door(new Position(x, y), entity_id, key);
        } else if (jsonPrefix.equals("portal")) {
            return new Portal(new Position(x, y), entity_id, colour);
        } else if (jsonPrefix.equals("zombie_toast_spawner")) {
            return new ZombieToastSpawner(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("spider")) {
            return new Spider(new Position(x, y), entity_id, gamemode);
        } else if (jsonPrefix.equals("zombie_toast")) {
            return new ZombieToast(new Position(x, y), entity_id, gamemode);
        } else if (jsonPrefix.equals("mercenary")) {
            return new Mercenary(new Position(x, y), entity_id, gamemode);
        } else if (jsonPrefix.equals("treasure")) {
            return new Treasure(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("key")) {
            return new Key(new Position(x, y), entity_id, key);
        } else if (jsonPrefix.equals("health_potion")) {
            return new HealthPotion(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("invincibility_potion")) {
            return new InvincibilityPotion(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("invisibility_potion")) {
            return new InvisibilityPotion(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("wood")) {
            return new Wood(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("arrow")) {
            return new Arrow(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("bomb")) {
            return new Bomb(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("sword")) {
            return new Sword(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("armour")) {
            return new Armour(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("one_ring")) {
            return new TheOneRing(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("sun_stone")) {
            return new SunStone(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("assassin")) {
            return new Assassin(new Position(x, y), entity_id, gamemode);
        } else if (jsonPrefix.equals("hydra")) {
            return new Hydra(new Position(x, y), entity_id, gamemode);
        } else if (jsonPrefix.equals("swamp_tile")) {
            return new SwampTile(new Position(x, y), entity_id, movement_factor);
        } else if (jsonPrefix.equals("anduril")) {
            return new Anduril(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("sceptre")) {
            return new Sceptre(new Position(x, y), entity_id);
        } else if (jsonPrefix.equals("midnight_armour")) {
            return new MidnightArmour(new Position(x, y), entity_id);
        } else {
            return null;
        }
    }
}
