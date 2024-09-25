package main.java.com.corportation.happyqa.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Configuration class is responsible for loading and managing
 * application configuration properties from a properties file. It provides
 * methods to retrieve property values by their keys.
 *
 */

public class Configuration {
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());
    private static final String PROPERTIES_FILE_PATH = "main/resources/config/app.properties";

    private Properties properties;

    /**
     * Loads properties from the specified properties file.
     */
    public Configuration() {
        properties = new Properties();

        // Load properties from app.properties file in config
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
            if (input == null) {
                throw new FileNotFoundException("Unable to find " + PROPERTIES_FILE_PATH);
            }
            properties.load(input); // Load properties from the input stream

        } catch (IOException e) {
            // Log error message if properties cannot be loaded
            logger.log(Level.SEVERE, "Error loading properties file: " + PROPERTIES_FILE_PATH, e);
        }
    }

    /**
     * Retrieves the property value by key.
     *
     * @param key the key of the property to retrieve
     * @return an Optional <String> containing the property value
     */
    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(properties.getProperty(key));
    }
}