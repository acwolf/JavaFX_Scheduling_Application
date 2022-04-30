package model;

/**
 * A class representing a country.
 * @author Aaron Wolf
 */
public class Country {
    private int id;
    private String name;

    /**
     * Creates a country object
     * @param id An integer representing an country ID
     * @param name A string representing a country's name.
     */
    public Country(int id, String name) {
        this.id=id;
        this.name=name;
    }

    /**
     * Gets the ID of a country
     * @return An integer representing a country ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the ID associated wiht a country.
     * @param id An integer ID associated with a country.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of a country.
     * @return A string representing the name of a country.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the country
     * @param name A string represting the name of a country.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Overrides the default toString method to return the country name.
     * @return the country name as a string
     */
    @Override
    public String toString() {
        return name;
    }
}
