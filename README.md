LimitSpawnEggs
==============
Prevent spawn eggs from changing spawners.


Commands
--------
 * `/limitspawneggs [help|reload]` - Show help on the `/limitspawneggs` 
   command or reload the configuration.


Permissions
-----------
 * `limitspawneggs.bypass` - Permission to bypass restrictions on the use of
   spawn eggs on mob spawners.
 * `limitspawneggs.console` - Permission to run `/limitspawneggs` and
   thereby reload the configuration (logically requires console access to edit
   the config).


Configuration
-------------
 * `egg-types` - List of material names of spawn eggs that are blocked. Allows
   for future versions of Minecraft to add new mobs without requiring code
   changes.
