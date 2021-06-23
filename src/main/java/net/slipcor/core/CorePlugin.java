package net.slipcor.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;

public abstract class CorePlugin extends JavaPlugin {

    // Debugger Variables
    boolean debugEverything;
    private Logger debugLogger;

    final Set<Integer> debugFilterIDs   = new HashSet<>(); // class IDs to filter
    final Set<String>  debugFilterNames = new HashSet<>(); // player names to filter

    /**
     * Close the logger
     */
    public void destroyDebugger() {
        if (debugLogger != null) {
            final Handler[] handlers = debugLogger.getHandlers().clone();
            for (final Handler handler : handlers) {
                debugLogger.removeHandler(handler);
                handler.close();
            }
            debugLogger = null;
        }
    }

    /**
     * @return the Logger instance
     */
    Logger getDebugger() {
        if (debugLogger == null) {
            debugLogger = Logger.getAnonymousLogger();
            debugLogger.setLevel(Level.ALL);
            debugLogger.setUseParentHandlers(false);

            for (final Handler handler : debugLogger.getHandlers()) {
                debugLogger.removeHandler(handler);
                handler.close();
            }

            try {
                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

                final File debugFolder = new File(getDataFolder(), "debug");

                debugFolder.mkdirs();
                final File logFile = new File(debugFolder, dateFormat.format(new Date()) + "general.log");
                logFile.createNewFile();

                final FileHandler handler = new FileHandler(logFile.getAbsolutePath());
                handler.setFormatter(LogFileFormatter.newInstance());

                debugLogger.addHandler(handler);
            } catch (final IOException | SecurityException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            }
        }

        return debugLogger;
    }

    /**
     * Load our Debugger
     *
     * @param node   the CoreConfig node to read for debug information
     * @param sender the CommandSender to confirm logging status to
     */
    public void loadDebugger(String node, final CommandSender sender) {
       debugFilterIDs.clear();
       debugFilterNames.clear();
       debugEverything = false;

        final String debugs = getConfig().getString(node, "off");

        if ("off".equalsIgnoreCase(debugs) || "none".equalsIgnoreCase(debugs) || "false".equalsIgnoreCase(debugs)) {
            sendPrefixed(sender, "debugging: off");
        } else {
            if ("on".equalsIgnoreCase(debugs) || "all".equalsIgnoreCase(debugs) || "true".equalsIgnoreCase(debugs)) {
                debugEverything = true;
                sendPrefixed(sender, "debugging on!");
            } else {
                final String[] sIds = debugs.split(",");
                sendPrefixed(sender, "debugging: " + debugs);
                for (final String s : sIds) {
                    try {
                        debugFilterIDs.add(Integer.valueOf(s));
                    } catch (final Exception e) {
                        // assume it is the name of a player we want to debug
                        debugFilterNames.add(s);
                    }
                }
            }
        }
    }

    /**
     * Send a message to a CommandSender, with configurable prefix
     *
     * @param sender  the recipient
     * @param message the message
     */
    public void sendPrefixed(final CommandSender sender, final String message) {
        if ("".equals(message)) {
            return;
        }

        if (sender instanceof Player) {
            sender.sendMessage(getMessagePrefix() + message);
            return;
        }

        this.getLogger().info(message);
    }

    public abstract CoreConfig config();

    protected abstract String getMessagePrefix();

    protected abstract String getDebugPrefix();

    /**
     * The Formatter that defines how the logfile looks like
     */
    private static class LogFileFormatter extends Formatter {

        private final SimpleDateFormat date;

        public static LogFileFormatter newInstance() {
            return new LogFileFormatter();
        }

        private LogFileFormatter() {
            super();
            this.date = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
        }

        @Override
        public String format(final LogRecord record) {
            final StringBuilder builder = new StringBuilder();
            final Throwable exception = record.getThrown();

            builder.append(this.date.format(record.getMillis()));
            builder.append(" [");
            builder.append(record.getLevel().getLocalizedName().toUpperCase());
            builder.append("] ");
            builder.append(record.getMessage());
            builder.append('\n');

            if (exception != null) {
                final StringWriter writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));
                builder.append(writer);
            }

            return builder.toString();
        }
    }
}
