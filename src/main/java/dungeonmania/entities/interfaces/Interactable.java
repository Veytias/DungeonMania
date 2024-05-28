package dungeonmania.entities.interfaces;

import java.util.Map;

import dungeonmania.entities.Entity;
import dungeonmania.exceptions.InvalidActionException;

public interface Interactable {
    public void interact(Map<String, Entity> all_entities) throws InvalidActionException;
}
