# Potion Assumptions:
- Invisibility potion lasts for 5 ticks (has limited time)
- Player can only have a (Invisible/Invincible) state at same time
- Once a potion is picked up, it's position isn't updated in the map (it is part of owned items)

# Player Assumptions:
- Player has 10 health and 2 attack
- Sword gives 5 attack
- Bow gives 3 attack

# Zombie Assumptions:
- Zombie has 5 health and 1 attack
- If all cells cardinally adjacent to the spawner are occupied, spawn zombie in the north cell.

# Spider Assumptions:
- Spider has 5 health and 1 attack
- Spides will chase the player if the plater is within 3 tiles of them
- Spiders will have 5% probility for spawning in every tick.

# Mercenary Assumptions:
- Mercenary has 5 health and 2 attack
- Mercenaries are capped at 2 per map
- 2 cardinal tile interact limit is interpreted as eculidean distance <= 2
- Mercenary spawn every 20 ticks

# Assassin Assumptions:
- Assassin has 5 health and 5 attack
- All other assumptions the same as the mercenary

# Hydra Assumptions:
- Hydra has 5 health and 3 attack
- With a 50% chance an attack can result in an increase in health by the same amount

# Mechanics Assumptions:
- Damage amount does not need to be integer
- Stacking of items is allowed but there is an inventory cap

# Portal Assumptions:
- Portal teleports character to the cell adjacent to the direction of movement of the character
- Each map can only have even number of portal.

# Boulder Assumptions:
- Boulders can be pushed into cells which don't have any other entities (Switches are exceptions).

# Building Assumptions:
- Built items are stored directly in the inventory.

# Health Assumptions:
- Health decreases by only 1/defence_multiplier times

# Shield, Armour and Defense Assumptions:
- defence_multiplier = 1.5 for shield
- Armour has a defence value
- Armour works the same for mercenary
- defence_multiplier for an entity is the max of all defence equipment (you can own multiple)

# Sword Assumptions:
- swords have 5 damage value

# Sceptre Assumptions:
- sceptres effect starts as soon as the player gets it

# Bomb Assumptions:
- bomb's blast radius is 3
- bomb's trigger radius is 3
- bomb can't be picked up again once it had been planted

# Swamptile Assumptions:
- There can only be 1 swamptile per cell

# Pathfinding Assumptions:
- Shortest distance is the shortest distance in terms of movements and not euclidean distance

# SunStone Assumptions:
- Player always choose using sunstone frist for bribing/opening the door
- Player always choose sunstone as last consideration for building equipment





