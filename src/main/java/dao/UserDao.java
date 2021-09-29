package dao;

import model.User;

import java.util.List;

public interface UserDao {

    /**
     * Used to retrieve a list of all Animals
     * @return
     */
    List<User> getAllUsers();

    /**
     * Used to persist the user to the datastore
     * @param userToRegister
     */
    void saveAnimal(User userToRegister) throws Exception;
}
