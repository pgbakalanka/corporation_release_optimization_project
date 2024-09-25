package main.java.com.corportation.happyqa.service;

import main.java.com.corportation.happyqa.config.Configuration;
import main.java.com.corportation.happyqa.dto.ReleaseDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The ReleaseSelector class is responsible for selecting the maximum possible
 * releases that can fit within a given sprint size. It uses a configuration loader
 * to fetch the sprint size value from the application's properties.
 *
 * This class is designed to work with  ReleaseDTO objects, which represent
 * releases with start days, end days, and durations.
 */

public class ReleaseSelector {

    private final int SPRINT_SIZE;

    /**
     *
     * This constructor initializes the sprint size by loading it from the configuration.
     * It throws an IllegalArgumentException, if the sprint size is not configured
     * or is invalid
     */

    public ReleaseSelector() {

        // Initialize sprintSize once in the constructor
        Configuration configLoader = new Configuration();

        // Fetch the property directly and handle the Optional correctly
        String sprintSizeStr = configLoader.getProperty("sprintSize").orElseThrow(() ->
                new IllegalArgumentException("sprintSize not configured."));

        // Parse the sprint size, ensuring it's a valid integer
        if (sprintSizeStr != null && !sprintSizeStr.isEmpty()) {
            this.SPRINT_SIZE = Integer.parseInt(sprintSizeStr);
        } else {
            throw new IllegalArgumentException("sprintSize is missing or invalid");
        }

    }

    /**
     * Selects the maximum possible releases that can fit within the configured sprint size.
     *
     * This method filters the provided list of releases to include only those that
     * end within the sprint size. It then sorts the selected releases first by their
     * end day, and then by duration. Finally, it compiles a list of releases that
     * can be accommodated within the sprint size.
     *
     * @param releases A list of ReleaseDTO objects representing the releases
     *                 to be considered for selection.
     * @return A list of ReleaseDTO objects that fit within the sprint size.
     */
    public List<ReleaseDTO> selectMaxRelease(List<ReleaseDTO> releases) {

        List<ReleaseDTO> validReleases = new ArrayList<>();

        // Filter and collect releases that ends within the sprint size
        // validate adding incorrect start date & duration
        for (ReleaseDTO release : releases) {

            if (release.getEndDay() <= SPRINT_SIZE && release.getStartDay() > 0 && release.getDuration() > 0) {
                validReleases.add(release);// adding releases fits to sprint
            }
        }

        /**
         * Sort selected releases by end day first,
         * and by duration to get the earliest possible fastest finished releases
         */
        validReleases.sort(Comparator.comparingInt(ReleaseDTO::getEndDay));

        List<ReleaseDTO> selectedReleases = new ArrayList<>();

        int currentEndDay = 0;

        for (ReleaseDTO release : validReleases) {
            int newEndDay = currentEndDay + release.getDuration();

            // Ensure the new end day does not exceed the sprint size
            if (newEndDay <= SPRINT_SIZE) {
                currentEndDay = newEndDay;
                // create the new Release object with Maximum posible realses fitting to sprint.
               // End day based on earlier release end day
                selectedReleases.add(new ReleaseDTO(release.getStartDay(), release.getDuration(), currentEndDay));
            }
        }

        return selectedReleases; // return the max possible releases
    }

}
