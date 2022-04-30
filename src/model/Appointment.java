package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class creates, modifies, and access appointments.
 * @author Aaron Wolf
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customer_id;
    private int user_id;
    private int contact_id;
    private String contactName;

    /**
     * Creates and appointment object.
     * @param id Appointment ID.
     * @param title Title of appointment.
     * @param description Description of appointment.
     * @param location Location of appointment.
     * @param type Type of appointment.
     * @param start Start Date and Time of appointment.
     * @param end End Date and Time of appointment.
     * @param customer_id ID of customer associated with appointment.
     * @param user_id ID of User who created the appointment.
     * @param contact_id ID of contact associated with appointment.
     */
    public Appointment(int id, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
    }

    /**
     * Creates and appointment object that includes the name of a contact.
     * @param id Appointment ID.
     * @param title Title of appointment.
     * @param description Description of appointment.
     * @param location Location of appointment.
     * @param type Type of appointment.
     * @param start Start Date and Time of appointment.
     * @param end End Date and Time of appointment.
     * @param customer_id ID of customer associated with appointment.
     * @param user_id ID of User who created the appointment.
     * @param contact_id ID of contact associated with appointment.
     * @param contactName Name of contact associated with appointment.
     */
    public Appointment(int id, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = customer_id;
        this.user_id = user_id;
        this.contact_id = contact_id;
        this.contactName = contactName;
    }

    /**
     * Gets an appointment ID
     * @return The appointment ID as an integer.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets appointment ID
     * @param id An integer appointment ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets an appointment title
     * @return A string representing the appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of an appointment
     * @param title A string representing the appointment title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description for an appointment.
     * @return A string representing an appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets an appointment description.
     * @param description A string representing an appointment description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location os an appointment.
     * @return A string representing an appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of an appointment.
     * @param location A string representing an appointment location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get the appointment type.
     * @return A string representing the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of appointment.
     * @param type A string representing appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the appointment starting time and location
     * @return A LocalDateTime representing an appointment start date and time converted from UTC to the local timezone.
     */
    public LocalDateTime getStart() {
        ZonedDateTime dt = start.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
        return dt.toLocalDateTime();
    }

    /**
     * Returns a user readable LocalDateTime representing appointment start date and time.
     * @return A formatted LocalDateTime representing an appointment start date and time.
     */
    public String getReadableStart() {
        ZonedDateTime dt = start.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("h:mm a  LLLL d, yyyy");
        return df.format(dt.toLocalDateTime());
    }

    /**
     * Sets an appointment start date and time
     * @param start A LocalDateTime representing the appointment start daten and time.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the appointment end date and time.
     * @return a LocalDateTime representing an appointment end date and time.
     */
    public LocalDateTime getEnd() {
        ZonedDateTime dt = end.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
        return dt.toLocalDateTime();
    }

    /**
     * Gets a user readable LocalDateTime representing an appointment end date and time.
     * @return a formatted LocalDateTime representing an appointment end date and time.
     */
    public String getReadableEnd() {
        ZonedDateTime dt = end.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("h:mm a  LLLL d, yyyy");
        return df.format(dt.toLocalDateTime());
    }

    /**
     * Sets the ending date and time of an appointemnt.
     * @param end A LocalDateTime representing an appointment end date and time.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Get the customer ID associated with and appointment.
     * @return An integer customer id.
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * Sets a customer ID associated with an appointment.
     * @param customer_id An integer customer ID.
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets the ID the user that created the appointment.
     * @return An integer repressing the ID of the user who created the appointment.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * Sets the ID of a user who created the appointment.
     * @param user_id An integer representing the ID of the user who created the appointment.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets the contact ID associated with the appointment.
     * @return An integer representing the ID of the contact associated with the appointment.
     */
    public int getContact_id() {
        return contact_id;
    }

    /**
     * Set the ID of the contact associated with the appointment.
     * @param contact_id An integer representing the ID of a contact associated with an appointment.
     */
    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    /**
     * Gets the Name of the contact associated with the appointment.
     * @return A string representing the name of the contact associated with an appointment.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the name of a contact associated with an appointment.
     * @param contact A string representing the name of the contact associated with an appointment.
     */
    public void setContact(String contact) {
        this.contactName = contactName;
    }
}
