package lms;

/**
 * Zekia Beyene
 * CEN 3024 - Software Development 1
 * September 15, 2025
 * Patron.java
 *
 * This class represents a single patron record in the Library Management System.
 * A patron has a 7-digit ID, a full name, a mailing address, and an overdue fine
 * amount. Objects of this class are simple data holders with getters/setters and
 * a formatted toString used by the console UI.
 */
public class Patron {

    // -------------------- Fields --------------------

    private int id;
    private String name;
    private String address;
    private double fine;

    // -------------------- Constructors --------------------

    /**
     * method: Patron
     * purpose: Construct a patron object with all required fields.
     * arguments: id (int), name (String), address (String), fine (double)
     * return: n/a (constructor)
     */
    public Patron(int id, String name, String address, double fine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fine = fine;
    }

    // -------------------- Getters / Setters --------------------

    /**
     * method: getId
     * purpose: Return the unique 7-digit patron ID.
     * arguments: none
     * return: int
     */
    public int getId() {
        return id;
    }

    /**
     * method: getName
     * purpose: Return the patron's full name.
     * arguments: none
     * return: String
     */
    public String getName() {
        return name;
    }

    /**
     * method: getAddress
     * purpose: Return the patron's mailing address.
     * arguments: none
     * return: String
     */
    public String getAddress() {
        return address;
    }

    /**
     * method: getFine
     * purpose: Return the patron's fine amount.
     * arguments: none
     * return: double
     */
    public double getFine() {
        return fine;
    }

    /**
     * method: setName
     * purpose: Update the patron name.
     * arguments: name (String)
     * return: void
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * method: setAddress
     * purpose: Update the patron address.
     * arguments: address (String)
     * return: void
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * method: setFine
     * purpose: Update the patron fine.
     * arguments: fine (double)
     * return: void
     */
    public void setFine(double fine) {
        this.fine = fine;
    }

    // -------------------- Formatting --------------------

    /**
     * method: toString
     * purpose: Produce a single output row for the console list view.
     * arguments: none
     * return: String
     */
    @Override
    public String toString() {
        // ID padded to 7 digits; clean column spacing
        return String.format("%07d | %s | %s | $%.2f", id, name, address, fine);
    }
}
