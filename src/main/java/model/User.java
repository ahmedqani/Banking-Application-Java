package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long id;
    private String username;
    private String password;
    private UserRole userRole;
    private List<Accounts> accounts = new ArrayList<>();


    public User(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
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

    public UserRole setUserRole(String userRole) {
        if (userRole.equalsIgnoreCase("employee")){
           return this.userRole = UserRole.EMPLOYEE ;
        }
        if (userRole.equalsIgnoreCase("admin")){
            this.userRole = UserRole.ADMIN ;
        }
        if(userRole.equalsIgnoreCase("user")) {
            this.userRole = UserRole.USER ;
        }
        return null;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts.addAll(accounts);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                ", accounts=" + accounts +
                '}';
    }
}
