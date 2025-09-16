package lms;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Zekia Beyene
 * CEN 3024 - Software Development 1
 * September 15, 2025
 * LibraryApp.java
 *
 * This class is the console UI for the Library Management System. It launches
 * the application, optionally imports patrons from a text file, and provides a
 * simple menu to add patrons, remove patrons by ID, and list all patrons.
 */
public class LibraryApp {

    // -------------------- Entry Point --------------------

    /**
     * method: main
     * purpose: Start the console program by creating a LibraryApp instance and running it.
     * arguments: args (String[])
     * return: void
     */
    public static void main(String[] args) {
        new LibraryApp().run();
    }

    // Manager holds the in-memory collection
    private final PatronManager manager = new PatronManager();

    // -------------------- App Lifecycle --------------------

    /**
     * method: run
     * purpose: Drive the program: initial optional import, then show a menu loop.
     * arguments: none
     * return: void
     */
    private void run() {
        Scanner in = new Scanner(System.in);
        System.out.println("===== Library Management System =====");

        System.out.print("Enter path to import file (or press Enter to skip): ");
        String path = in.nextLine().trim();
        if (!path.isEmpty()) {
            ImportResult r = manager.loadFromFile(path);
            System.out.println(r);
            if (!r.isEmpty()) {
                System.out.println("Errors:");
                r.errors.forEach(e -> System.out.println("  " + e));
            }
            printAll();
        }

        while (true) {
            showMenu();
            String choice = in.nextLine().trim();
            switch (choice) {
                case "1" -> importFromFile(in);
                case "2" -> addManually(in);
                case "3" -> removeById(in);
                case "4" -> printAll();
                case "5" -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Please choose 1-5.");
            }
        }
    }

    // -------------------- Menu Actions --------------------

    /**
     * method: showMenu
     * purpose: Display available operations to the user.
     * arguments: none
     * return: void
     */
    private void showMenu() {
        System.out.println();
        System.out.println("1) Import patrons from file");
        System.out.println("2) Add patron manually");
        System.out.println("3) Remove patron by ID");
        System.out.println("4) List all patrons");
        System.out.println("5) Exit");
        System.out.print("Select an option: ");
    }

    /**
     * method: importFromFile
     * purpose: Prompt the user for a file path and import patrons.
     * arguments: in (Scanner)
     * return: void
     */
    private void importFromFile(Scanner in) {
        System.out.print("Enter file path: ");
        ImportResult r = manager.loadFromFile(in.nextLine().trim());
        System.out.println(r);
        if (!r.isEmpty()) {
            System.out.println("Errors:");
            r.errors.forEach(e -> System.out.println("  " + e));
        }
        printAll();
    }

    /**
     * method: addManually
     * purpose: Collect user input for a single patron and attempt to add it.
     * arguments: in (Scanner)
     * return: void
     */
    private void addManually(Scanner in) {
        try {
            System.out.print("Enter 7-digit ID: ");
            int id = Integer.parseInt(in.nextLine().trim());

            System.out.print("Enter full name: ");
            String name = in.nextLine().trim();

            System.out.print("Enter address: ");
            String address = in.nextLine().trim();

            System.out.print("Enter fine (0-250): ");
            double fine = Double.parseDouble(in.nextLine().trim());

            boolean ok = manager.addPatron(new Patron(id, name, address, fine));
            System.out.println(ok ? "Patron added." : "Patron not added (duplicate/invalid).");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Patron not added.");
        }
        printAll();
    }

    /**
     * method: removeById
     * purpose: Ask the user for an ID and remove that patron if found.
     * arguments: in (Scanner)
     * return: void
     */
    private void removeById(Scanner in) {
        try {
            System.out.print("Enter ID to remove: ");
            int id = Integer.parseInt(in.nextLine().trim());
            boolean ok = manager.removePatron(id);
            System.out.println(ok ? "Patron removed." : "ID not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
        printAll();
    }

    /**
     * method: printAll
     * purpose: Display a formatted table of all patrons sorted by ID.
     * arguments: none
     * return: void
     */
    private void printAll() {
        List<Patron> all = manager.getAll();
        if (all.isEmpty()) {
            System.out.println("\n(No patrons in the system.)");
            return;
        }

        all.sort(Comparator.comparingInt(Patron::getId)); // stable, ascending by ID
        System.out.println("\nID       | Name | Address | Fine");
        System.out.println("-------------------------------------------------------------");
        all.forEach(p -> System.out.println(p.toString()));
    }
}

