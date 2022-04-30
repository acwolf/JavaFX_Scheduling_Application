package model;

/**
 * This class creates and manipulates User objects.
 *  @author Aaron Wolf
 * */

public class User {
    private int userId;
    private String userName;
    private String password;

    /**
     * Creates a user object
      * @param userId The ID of the user.
     * @param userName The name of the user.
     * @param password The user's password.
     */
    public User(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Gets the users ID.
     * @return An integer representing a user's ID
     */
    public int getUserID() {
        return userId;
    }

    /**
     * Gets the username of a user.
     * @return A string representing the user's username.
     */
    public String getUserName() {
        return userName;
    }
}
