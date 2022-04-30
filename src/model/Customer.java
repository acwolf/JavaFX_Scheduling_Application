package model;

/**
 * A class representing a customer.
 * @author Aaron Wolf
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal_code;
    private String phone;
    private int division_id;

    /**
     * Creates a customer object
     * @param id The id of a customer.
     * @param name The name of a customer.
     * @param address The address of a customer.
     * @param postal_code The postal code of a customer.
     * @param phone The phone of a customer.
     * @param division_id The division ID associated with a customer.
     */
    public Customer(int id, String name, String address, String postal_code, String phone, int division_id){
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal_code = postal_code;
        this.phone = phone;
        this.division_id = division_id;
    }

    /**
     * Gets the ID of a customer
     * @return An integer representing a customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer ID
     * @param id An integer repressing a customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of a customer.
     * @return A string representing a customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a customer
     * @param name A string representing the name of a customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of a customer.
     * @return A string repressing the address of a customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of a customer.
     * @param address A string representing the address of a customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number of a customer.
     * @return A string repressing the phone number of a customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of a customer.
     * @param phone A string representing the phone number of a customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the division ID associated with a customer.
     * @return An integer representing the division ID of a customer.
     */
    public int getDivision_id() {
        return division_id;
    }

    /**
     * Sets the division ID associated with a customer.
     * @param division_id An integer representing the division ID of a customer.
     */
    public void setDivision_id(int division_id) {
        this.division_id = division_id;
    }

    /**
     * Gets the postal code of a customer.
     * @return A string representing the postal code of a customer.
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Sets the postal code associated with a customer.
     * @param postal_code A string representing the postal code of a customer.
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Overrides the object toString method to return the customers name.
     * @return A string repressing the name of a customer.
     */
    @Override
    public String toString() {
        return name;
    }



}
