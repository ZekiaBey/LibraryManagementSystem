package lms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Zekia Beyene
 * CEN 3024 - Software Development 1
 * September 15, 2025
 * PatronManager.java
 *
 * This class manages the in-memory list of patrons. It validates and adds
 * patrons, removes them by ID, exposes a read-only view for listing, and
 * loads patron data from a text file where each line is:
 *
 *   id-name-address-fine
 *
 * Validation rules:
 *  - id: exactly 7 digits and unique
 *  - name/address: not empty after trimming
 *  - fine: 0.00 to 250.00 inclusive
 */
public class PatronManager {

    // -------------------- Fields --------------------

    private final List<Patron> patrons = new ArrayList<>();

    // -------------------- Core Operations --------------------

    /**
     * method: addPatron
     * purpose: Validate and add a patron to the collection if not duplicate.
     * arguments: p (Patron)
     * return: boolean (true if added, false if invalid or duplicate)
     */
    public boolean addPatron(Patron p) {
        if (!isValidId(p.getId())) return false;
        if (isDuplicateId(p.getId())) return false;
        if (p.getName() == null || p.getName().trim().isEmpty()) return false;
        if (p.getAddress() == null || p.getAddress().trim().isEmpty()) return false;
        if (!isValidFine(p.getFine())) return false;

        patrons.add(p);
        return true;
    }

    /**
     * method: removePatron
     * purpose: Remove one patron that matches the given ID.
     * arguments: id (int)
     * return: boolean (true if a record was removed)
     */
    public boolean removePatron(int id) {
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getId() == id) {
                patrons.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * method: getAll
     * purpose: Retrieve the current list of patrons for display.
     * arguments: none
     * return: List<Patron> (backed list; caller should not modify structure)
     */
    public List<Patron> getAll() {
        return patrons;
    }

    /**
     * method: loadFromFile
     * purpose: Read a file of patron lines (id-name-address-fine) and attempt
     *          to add each to the system while collecting errors.
     * arguments: path (String)
     * return: ImportResult summary with counts and error details
     */
    public ImportResult loadFromFile(String path) {
        ImportResult result = new ImportResult();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNo = 0;

            while ((line = br.readLine()) != null) {
                lineNo++;
                String raw = line.trim();
                if (raw.isEmpty()) {
                    result.skipped++;
                    result.errors.add("- Line " + lineNo + ": Blank line");
                    continue;
                }

                String[] parts = raw.split("-", 4);
                if (parts.length != 4) {
                    result.skipped++;
                    result.errors.add("- Line " + lineNo + ": Expected 4 fields separated by '-'");
                    continue;
                }

                String idStr = parts[0].trim();
                String name = parts[1].trim();
                String address = parts[2].trim();
                String fineStr = parts[3].trim();

                int id;
                double fine;

                // Validate id format
                if (!idStr.matches("\\d{7}")) {
                    result.skipped++;
                    result.errors.add("- Line " + lineNo + ": ID must be 7 digits");
                    continue;
                }

                try {
                    id = Integer.parseInt(idStr);
                } catch (NumberFormatException nfe) {
                    result.skipped++;
                    result.errors.add("- Line " + lineNo + ": ID is not a number");
                    continue;
                }

                try {
                    fine = Double.parseDouble(fineStr);
                } catch (NumberFormatException nfe) {
                    result.skipped++;
                    result.errors.add("- Line " + lineNo + ": Fine is not a number");
                    continue;
                }

                Patron p = new Patron(id, name, address, fine);
                if (addPatron(p)) {
                    result.added++;
                } else {
                    result.skipped++;
                    // Try to be specific about the failure cause
                    if (isDuplicateId(id)) {
                        result.errors.add("- Line " + lineNo + ": Duplicate ID");
                    } else if (!isValidFine(fine)) {
                        result.errors.add("- Line " + lineNo + ": Fine must be 0..250");
                    } else if (name.isEmpty() || address.isEmpty()) {
                        result.errors.add("- Line " + lineNo + ": Name/Address empty");
                    } else {
                        result.errors.add("- Line " + lineNo + ": Failed validation");
                    }
                }
            }
        } catch (Exception ex) {
            result.errors.add("File not found: " + path);
        }

        return result;
    }

    // -------------------- Validation Helpers --------------------

    /**
     * method: isValidId
     * purpose: Check that an integer is a 7-digit positive ID.
     * arguments: id (int)
     * return: boolean
     */
    private boolean isValidId(int id) {
        return id >= 1_000_000 && id <= 9_999_999;
    }

    /**
     * method: isDuplicateId
     * purpose: Check whether the given ID already exists in the list.
     * arguments: id (int)
     * return: boolean
     */
    private boolean isDuplicateId(int id) {
        for (Patron p : patrons) {
            if (p.getId() == id) return true;
        }
        return false;
    }

    /**
     * method: isValidFine
     * purpose: Validate fine bounds per assignment (0..250 inclusive).
     * arguments: fine (double)
     * return: boolean
     */
    private boolean isValidFine(double fine) {
        return fine >= 0.0 && fine <= 250.0;
    }
}

