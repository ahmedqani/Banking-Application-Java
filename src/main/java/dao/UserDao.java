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

    void deleteUser(int userid);

    /**
     * Used to persist the user to the datastore
     * @param userToRegister
     */

    void saveUser(User userToRegister) throws Exception;

    /**
     * Used to persist the user to the datastore
     * @param userBalanceToUpdate
     */
    void updateUserBalance(User userBalanceToUpdate) throws Exception;


    void updateUserRole(User userRoleToUpdate) throws Exception;

    void activateUser(User userToActivate) throws Exception;
}
