package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.User;

import static org.junit.Assert.*;

public class UserTest {
    User user = new User("TestName","Pass",UserRole.USER);


    @Test
    public void getUsername() throws Exception {
        String user =this.user.getUsername();
        assertEquals(user,"TestName");
    }

    @Test
    public void setUsername() {
        this.user.setUsername("USERNAME");
        assertEquals(user.getUsername(),"USERNAME");
    }

    @Test
    public void getPassword() {
        String pass =this.user.getPassword();
        assertEquals(pass,"Pass");
    }
    @Test
    public void setPassword() {
        this.user.setPassword("NewPass");
        assertNotEquals(user.getPassword(),"Pass");
    }


}