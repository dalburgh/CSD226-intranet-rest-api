package ca.saultcollege.server.data;


public class Account {
    private static int COUNT;
    private int ID;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public Account( String username, String password, String email, String firstName, String lastName ) {
        COUNT++;
        this.ID = COUNT;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static int getCOUNT() {
        return COUNT;
    }

    public int getID() {
        return ID;
    }
}
