package dao;

import model.User;

import java.util.List;

public interface UserDao {

    /**
     * Used to retrieve a list of all Animals
     * @return
     */
    List<User> getAllUsers();

    User getUser(int userid);

    User getUserByName(String username);

    void deleteUser(int userid);

    /**
     * Used to persist the user to the datastore
     * @param userToRegister
     */

    User saveUser(User userToRegister) throws Exception;

    /**
     * Used to persist the user to the datastore
     * @param
     */

    void updateUserRole(User userRoleToUpdate) throws Exception;





}
