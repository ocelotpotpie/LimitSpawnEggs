package nu.nerd.limitspawneggs;

import java.util.EnumSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        saveDefaultConfig();
        reloadConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    // ------------------------------------------------------------------------
    /**
     * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender,
     *      org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase(getName())) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help")) {
                    return false;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    sender.sendMessage(ChatColor.GOLD + getName() + " configuration reloaded.");
                    return true;
                }
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------
    /**
     * Prevent any player from right-clicking on a mob spawner with a spawn egg,
     * unless they have the limitspawneggs.bypass permission.
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("limitspawneggs.bypass")) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK &&
            event.getClickedBlock().getType() == Material.SPAWNER &&
            event.getItem() != null &&
            _spawnEggs.contains(event.getItem().getType())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You don't have permission to alter spawners.");
        }
    }

    // ------------------------------------------------------------------------
    /**
     * Load the list of spawn egg materials to block.
     */
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        _spawnEggs.clear();
        for (String eggTypeName : getConfig().getStringList("egg-types")) {
            try {
                _spawnEggs.add(Material.valueOf(eggTypeName));
            } catch (IllegalArgumentException ex) {
                getLogger().warning("\"" + eggTypeName + "\" is not a valid material name.");
            }
        }
    }

    // ------------------------------------------------------------------------
    /**
     * Set of Materials representing spawn eggs that are blocked.
     */
    private final EnumSet<Material> _spawnEggs = EnumSet.noneOf(Material.class);

} // class LimitSpawnEggs