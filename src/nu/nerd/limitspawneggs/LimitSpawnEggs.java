package nu.nerd.limitspawneggs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

// ----------------------------------------------------------------------------
/**
 * Prevent spawn eggs from changing spawners.
 *
 * Right clicking on a spawner with a spawn egg will change its type to the
 * egg's mob, which is undesirable on survival servers. WorldGuard's on-interact
 * in the blacklist prevents players from breaking spawners as well as right
 * clicking on them, and so cannot be used to disable that behaviour.
 */
public class LimitSpawnEggs extends JavaPlugin implements Listener {

    // ------------------------------------------------------------------------
    /**
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    // ------------------------------------------------------------------------
    /**
     * Prevent any player from right-clicking on a mob spawner with a spawn egg.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
            event.getClickedBlock().getType() == Material.MOB_SPAWNER &&
            event.getItem() != null &&
            event.getItem().getType() == Material.MONSTER_EGG) {
            event.setCancelled(true);
        }
    }
} // class LimitSpawnEggs