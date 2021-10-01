package controller;

import dao.DAOUtilities;
import dao.UserDao;
import model.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserController {

    //Call DAO method
    UserDao dao = DAOUtilities.getUserDao();

    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }
    public User getUser(int userId){
        return dao.getUser(userId);
    }
    public void deleteUser(int userId){
        dao.deleteUser(userId);
    }
    public void saveUser(User userToSave){
        try {
            dao.saveUser(userToSave);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("ID is already in use");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem creating the User at this time");
        }
    }


}
