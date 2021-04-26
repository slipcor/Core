package net.slipcor.core;

import org.bukkit.command.CommandSender;

/**
 * Debugger class, provides methods for logging when in debug mode
 */
public class CoreDebugger {
    private final int debugID;       // the Debugger instance ID
    private final CorePlugin plugin;
    private final String prefix;

    /**
     * Create a Debugger instance
     *
     * @param plugin  the CorePlugin to debug
     * @param debugID the Debugger instance ID
     */
    public CoreDebugger(CorePlugin plugin, final int debugID) {
        this.debugID = debugID;
        this.plugin = plugin;
        this.prefix = plugin.getDebugPrefix();
    }

    /**
     * @return whether this Debugger is logging
     */
    private boolean debugs() {
        return plugin.debugEverything || plugin.debugFilterIDs.contains(debugID);
    }

    /**
     * @param player the Player name to look for
     * @return whether the plugin is debugging the player
     */
    private boolean debugs(final String player) {
        return plugin.debugEverything || plugin.debugFilterNames.contains(player);
    }

    /**
     * Log a message
     *
     * @param string the message
     */
    public void i(final String string) {
        if (!debugs()) {
            return;
        }
        plugin.getDebugger().info(prefix + System.currentTimeMillis() % 1000 + ' ' + string);
    }

    /**
     * Log a message that belongs to a CommandSender
     *
     * @param string the message
     * @param sender the CommandSender to maybe filter
     */
    public void i(final String string, final CommandSender sender) {
        if (sender == null) {
            i(string, "null");
            return;
        }
        if (!debugs(sender.getName())) {
            return;
        }
        plugin.getDebugger().info(prefix + "[p:" + sender.getName() + ']' + System.currentTimeMillis() % 1000 + ' ' + string);
    }

    /**
     * Log a message that might belong to a player name
     *
     * @param string the message
     * @param player the name to maybe filter
     */
    public void i(final String string, final String player) {
        if (!debugs(player)) {
            return;
        }

        plugin.getDebugger().info(prefix + System.currentTimeMillis() % 1000 + ' ' + string);
    }
}
