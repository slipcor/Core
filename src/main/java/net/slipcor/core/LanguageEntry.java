package net.slipcor.core;

public interface LanguageEntry {
    /**
     * @return the full config node path
     */
    String getNode();

    /**
     * @return the config node content
     */
    String getValue();

    /**
     * Update the config node content
     */
    void setValue(String value);

    /**
     * @return the node colorized content
     */
    String parse();

    /**
     * Return a colorized string with replaced placeholders
     *
     * @param args the placeholders to replace
     * @return the replaced colorized string
     */
    String parse(String... args);
}
