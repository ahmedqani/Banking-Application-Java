package model;

public class User {
    private long id;
    private String username;
    private String password;
    private UserRole userRole;
    private long balance;


    public User() {
    }
    public User(long id, String username, String password, UserRole userRole, long balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                ", balance=" + balance +
                '}';
    }
}
