package model;

import junit.framework.TestCase;
import org.junit.Test;

import javax.crypto.spec.PSource;

public class UserTest extends TestCase {

    public User user = new User("TestName","Pass",UserRole.USER,200,true);




    @Test
    public void getId() {
    }

    @Test
    public void setId() {
    }

    @Test
    public void getUsername() {
    }

    @Test
    public void setUsername() {
    }

    @Test
    public void getPassword() {
    }

    @Test
    public void setPassword() {
    }

    @Test
    public void getUserRole() {
    }

    @Test
    public void setUserRole() {
        user.setUserRole("employee");
        System.out.println(user.getUserRole());
        String role = user.getUserRole();
        System.out.println(role);
        assertEquals("EMPLOYEE", role);
    }

    @Test
    public void getBalance() {
        assertEquals(1,1);
    }

    @Test
    public void setBalance() {
    }

    @Test
    public void isAccountIsActive() {
    }

    @Test
    public void setAccountIsActive() {
    }

    @Test
    public void testToString() {
    }
}