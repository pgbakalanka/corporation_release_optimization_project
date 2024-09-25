package main.java.com.corportation.happyqa.persistance;

import main.java.com.corportation.happyqa.config.Configuration;
import main.java.com.corportation.happyqa.dto.ReleaseDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The FileManager class is responsible for managing file operations related to
 * release data. It handles reading release data from a specified input file and writing
 * processed release data to an output file. The class uses a Configuration object
 * to load file names from configuration properties.
 *
 * This class is designed to work with release data represented by the ReleaseDTO
 * objects, where each object contains a start day and a duration.
 *
 * All file operations are conducted within the resource directory located in the
 * project structure, specifically under /src/main/resources/
 *
 * This class uses a logger to log any errors or warnings that occur during file
 * operations.
 */

public class FileManager {

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());
    private final Configuration configLoader;
    private final Path resourceDirectory;

    /**
     *
     * This constructor initializes the configuration loader and sets up the path
     * to the resource directory based on the current working directory.
     */

    public FileManager() {
        configLoader = new Configuration();
        String currentDirectory = System.getProperty("user.dir");
        String filePath = "/src/main/resources/";
        resourceDirectory = Paths.get(currentDirectory, filePath);
    }

    /**
     * Reads a file containing release data (Start date & duration) and parses it into a list of ReleaseDTO objects.
     *
     * @return A list of ReleaseDTO objects, each containing the start day and duration read from the file.
     */
    public List<ReleaseDTO> readFromFile() {

        // Retrieve the input file name or handle the missing value scenario
        String inputFileName = configLoader.getProperty("inputFileName").orElseThrow(() ->
                new IllegalArgumentException("Input file name not configured."));

        Path inputFilePath = resourceDirectory.resolve(inputFileName);

        List<ReleaseDTO> releases = new ArrayList<>();

        // Using try-with-resources for BufferedReader to handle large files efficiently
        try (BufferedReader reader = Files.newBufferedReader(inputFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(" ");
                    if (data.length == 2) {
                        try {
                            int startDay = Integer.parseInt(data[0]);
                            int duration = Integer.parseInt(data[1]);
                            releases.add(new ReleaseDTO(startDay, duration));
                        } catch (NumberFormatException e) {
                            logger.log(Level.WARNING, "Invalid number format in line: {0}", line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, String.format("Error reading the file %s", inputFilePath), e);
        }

        return releases;
    }

    /**
     * Writes the specified list of ReleaseDTO objects to the output file.
     *
     * @param selectedReleases A list of ReleaseDTO objects (Start Day & End Day) to be written.
     */
    public void writeToFile(List<ReleaseDTO> selectedReleases) {

        // Retrieve the output file name or handle the missing value scenario
        String outputFileName = configLoader.getProperty("outputFileName").orElseThrow(() ->
                new IllegalArgumentException("Output file name not configured."));

        Path outputFilePath = resourceDirectory.resolve(outputFileName);

        try {
            // Build final solution  to outputData
            StringBuilder outputData = new StringBuilder(selectedReleases.size() + "\n");
            selectedReleases.forEach(release -> outputData.append(release.getStartDay())
                    .append(" ")
                    .append(release.getEndDay())
                    .append("\n"));

            // Write all data at once
            Files.write(outputFilePath, outputData.toString().getBytes());
            logger.log(Level.INFO, "Solution written to {0}", outputFileName);
        } catch (IOException e) {
            logger.log(Level.SEVERE, String.format("Error writing to file %s", outputFilePath), e);
        }
    }
}
