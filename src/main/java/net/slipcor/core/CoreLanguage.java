package net.slipcor.core;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CoreLanguage {
    protected final CorePlugin plugin;

    public CoreLanguage(CorePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Attempt to load a language file
     *
     * @param fileName the file name without .yml extension
     * @return an error, null if everything went fine
     */
    public String load(String fileName) {
        plugin.getDataFolder().mkdir();
        final File configFile = new File(plugin.getDataFolder(), fileName + ".yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (final Exception e) {
                e.printStackTrace();
                return "Error when creating language file:\n" + e.getMessage();
            }
        }
        final YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (final Exception e) {
            e.printStackTrace();
            return "Error when loading language file:\n" + e.getMessage();
        }

        for (final LanguageEntry m : getAllNodes()) {
            config.addDefault(m.getNode(), m.toString());
        }

        config.options().copyDefaults(true);
        try {
            config.save(configFile);
        } catch (final Exception e) {
            e.printStackTrace();
            return "Error when saving language file:\n" + e.getMessage();
        }

        for (final LanguageEntry m : getAllNodes()) {
            m.setValue(config.getString(m.getNode()));
        }
        return null;
    }

    /**
     * @return all LanguageEntry enums
     */
    protected abstract LanguageEntry[] getAllNodes();

    private final static Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");

    public static String colorize(String message) {
        if (message.contains("<#")) {
            try {
                Matcher matcher = hexPattern.matcher(message);
                while (matcher.find()) {
                    final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
                    final String before = message.substring(0, matcher.start());
                    final String after = message.substring(matcher.end());
                    message = before + hexColor + after;
                    matcher = hexPattern.matcher(message);
                }
            } catch (NoSuchMethodError e) {
                // we do not know this yet!
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
