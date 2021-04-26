package net.slipcor.core;

import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public abstract class CoreCommand {

    final CorePlugin plugin;
    final String permission;
    private final LanguageEntry messe;

    /**
     * Create a Command
     *
     * @param plugin          the CorePlugin that will handle the command
     * @param permission      permission to look for before committing the command
     * @param argumentMessage the message to send when the command arguments do not match up
     */
    protected CoreCommand(final CorePlugin plugin, final String permission, final LanguageEntry argumentMessage) {
        this.plugin = plugin;
        this.permission = permission;
        this.messe = argumentMessage;
    }

    /**
     * Add a match to a List if a command input matches an expected term
     *
     * @param list  the List to add to
     * @param word  the word to match and to add to the list
     * @param check the String that has been typed
     */
    protected void addIfMatches(final List<String> list, final String word, final String check) {
        if (check.equals("") || word.toLowerCase().contains(check.toLowerCase())) {
            list.add(word);
        }
    }

    /**
     * Are the given arguments of valid count?
     *
     * @param sender      the sender issuing the command
     * @param args        the arguments given
     * @param validCounts valid argument counts
     * @return whether the amount of arguments is valid
     */
    protected boolean argCountValid(final CommandSender sender, final String[] args, final Integer[] validCounts) {

        for (final int i : validCounts) {
            if (i == args.length) {
                return true;
            }
        }
        plugin.sendPrefixed(sender, messe.parse(String.valueOf(args.length),
                org.apache.commons.lang.StringUtils.join(validCounts, '|')));

        return false;
    }

    /**
     * Do what the command is supposed to do
     *
     * @param sender the sender issuing the command
     * @param args   the command arguments
     */
    public abstract void commit(CommandSender sender, String[] args);

    /**
     * @return a list of command names
     */
    public abstract List<String> getMain();

    /**
     * @return a list of command shorthand names
     */
    public abstract List<String> getShort();

    /**
     * @return an info text explaining the command
     */
    public abstract String getShortInfo();

    /**
     * Check whether a sender has the permission to use this command
     *
     * @param sender the sender trying to issue the command
     * @return whether they have the permission
     */
    public boolean hasPerms(final CommandSender sender) {
        return(sender.hasPermission(permission));
    }

    /**
     * Load the command into the plugin's command list and map
     *
     * @param list the list to add to
     * @param map  the map to add to
     */
    public void load(final List<CoreCommand> list, final Map<String, CoreCommand> map) {
        for (String sMain : getMain()) {
            map.put(sMain, this);
        }
        list.add(this);
    }

    /**
     * Return tab complete matches
     *
     * @param args the current command progress
     * @return a list of matches to complete with
     */
    public abstract List<String> completeTab(String[] args);
}
