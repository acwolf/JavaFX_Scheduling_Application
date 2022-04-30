package model;

/**
 * Represents a division.
 */
public class Division {
    private int id;
    private String name;
    private int country_id;

    /**
     * Creates a division object.
     * @param id The division ID.
     * @param name The name of a division.
     * @param country_id The country ID of a division.
     */
    public Division(int id, String name, int country_id) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
    }

    /**
     * Gets the ID associated with a division.
     * @return An integer representing the id associated with a division.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of a division.
     * @param id An integer representing a division ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of a Division.
     * @return A string representing the name of a division.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a division.
     * @param name A string representing the name of a division.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the country ID associated with the division.
     * @return An integer associated with a division.
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     * Sets the country ID associated with a division.
     * @param country_id An integer associated with a division.
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    /**
     * Overrides the object's default toString method to return the division name.
     * @return A string representing the name of a division.
     */
    @Override
    public String toString() {
        return name;
    }
}
