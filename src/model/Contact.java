package model;

/**
 * A class representing a contact.
 * @author Aaron Wolf
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * Creates a contact object
     * @param id The ID of the contact.
     * @param name The name of the contact.
     * @param email The email of the contact.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Gets a contact ID
     * @return An integer representing a contact ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the contact ID
     * @param id An integer representing a contact ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of a contact associated with a contact.
     * @return A string representing the name of a contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a contact.
     * @param name A string representing a contace name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the contact's email
     * @return A string representing a contact's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email associated with a contact.
     * @param email A string email associated with a contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the contact name
     * @return A string representing the contact name
     */
    @Override
    public String toString() {
        return name;
    }

}
