package main.java.com.corportation.happyqa.app;

import main.java.com.corportation.happyqa.dto.ReleaseDTO;
import main.java.com.corportation.happyqa.persistance.FileManager;
import main.java.com.corportation.happyqa.service.ReleaseSelector;

import java.util.List;

/**
 * The ApplicationHappyQA class serves as the entry point for the HappyQA application.
 * It handles the main functionality of reading, processing, and writing release data.
 *
 * The application performs the following steps:
 *
 *     Reads a list of releases from an input file.
 *     Selects the maximum number of releases that can be validated.
 *     Writes the selected releases to an output file.
 *
 * This application utilizes the  FileManager for file operations and the
 * ReleaseSelector for release selection logic.
 *
 */
public class ApplicationHappyQA {

    /**
     * The main method is the entry point of the HappyQA application.
     * It orchestrates the reading of releases, selecting the maximum number of releases,
     * and writing the selected releases to an output file.
     *
     */

    public static void main(String[] args) {


            // Instantiate services
            FileManager fileHandler = new FileManager();
            ReleaseSelector releaseSelector = new ReleaseSelector();

            // Step 1: Read the list of releases from the input file
            List<ReleaseDTO> releases = fileHandler.readFromFile();

            // Step 2: Select the maximum number of releases Bob can validate
            List<ReleaseDTO> selectedReleases = releaseSelector.selectMaxRelease(releases);

            // Step 3: Write the solution to the output file
            fileHandler.writeToFile( selectedReleases);



    }
}
