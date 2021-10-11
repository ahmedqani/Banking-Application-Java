package controller;

import dao.DAOUtilities;
import dao.UserDao;
import exceptions.InvalidCredentialsException;
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
    public User getUserByName(String userName){
        return dao.getUserByName(userName);
    }
    public User loginUser(String username, String password) throws InvalidCredentialsException {
            User user = dao.getUserByName(username);
            if (user.getPassword().equals(password)){
                return user;
            }else {
                return null;
            }
    }

    public void isActive(User user) throws Exception {
        dao.activateUser(user);
    }


    public void deleteUser(int userId){
        dao.deleteUser(userId);
    }
    public User saveUser(User userToSave){
        try {
            dao.saveUser(userToSave);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("ID is already in use");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem creating the User at this time");
        }
        return userToSave;
    }


}
