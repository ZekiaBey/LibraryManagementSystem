package lms;

import java.util.ArrayList;
import java.util.List;

/**
 * Zekia Beyene
 * CEN 3024 - Software Development 1
 * September 15, 2025
 * ImportResult.java
 *
 * A simple value object returned by the import process. It reports how many
 * records were added, how many were skipped, and a list of detailed error
 * messages (with line numbers) for anything that could not be imported.
 */
public class ImportResult {

    // -------------------- Fields --------------------

    public int added;
    public int skipped;
    public final List<String> errors = new ArrayList<>();

    // -------------------- Constructors --------------------

    /**
     * method: ImportResult
     * purpose: Create a result container initialized to zero counts.
     * arguments: none
     * return: n/a (constructor)
     */
    public ImportResult() { }

    // -------------------- Helpers --------------------

    /**
     * method: isEmpty
     * purpose: Indicates whether any error messages were collected.
     * arguments: none
     * return: boolean
     */
    public boolean isEmpty() {
        return errors.isEmpty();
    }

    /**
     * method: toString
     * purpose: Compact summary suitable for printing after import.
     * arguments: none
     * return: String
     */
    @Override
    public String toString() {
        return "Added: " + added + ", Skipped: " + skipped;
    }
}
