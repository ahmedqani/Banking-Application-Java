package model;

public class User {
    private long id;
    private String username;
    private String password;
    private UserRole userRole;
    private long balance;
    private boolean accountIsActive;


    public User(String username, String password, UserRole userRole, long balance, boolean accountIsActive) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.balance = balance;
        this.accountIsActive = accountIsActive;
    }

    public User() {
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

    public String getUserRole() {
        if (userRole == UserRole.ADMIN){
            return "ADMIN";
        }
        if (userRole == UserRole.EMPLOYEE){
            return "EMPLOYEE";
        }else {
            return "USER";
        }
    }

    public void setUserRole(String userRole) {
        if (userRole.equalsIgnoreCase("employee")){
            this.userRole = UserRole.EMPLOYEE ;
        }
        if (userRole.equalsIgnoreCase("admin")){
            this.userRole = UserRole.ADMIN ;
        }else {
            this.userRole = UserRole.USER ;
        }

    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isAccountIsActive() {
        return accountIsActive;
    }

    public void setAccountIsActive(boolean accountIsActive) {
        this.accountIsActive = accountIsActive;
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
