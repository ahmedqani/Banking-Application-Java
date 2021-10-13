package controller;

import dao.AccountsDao;
import dao.DAOUtilities;
import dao.UserDao;
import exceptions.InvalidCredentialsException;
import logging.Logging;
import model.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class UserController {

    //Call DAO method
    UserDao dao = DAOUtilities.getUserDao();
    AccountsDao accountsDao = DAOUtilities.getAccountDao();


    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }
    public User getUser(int userId){
        User user = dao.getUser(userId);
        user.setAccounts(accountsDao.getUserAccounts(user.getUsername()));
        return user;
    }
    public User getUserByName(String userName) throws InvalidCredentialsException {
        try {
            User user = dao.getUserByName(userName);
            user.setAccounts(accountsDao.getUserAccounts(user.getUsername()));
            return user;
        }catch (NullPointerException e){
            throw new InvalidCredentialsException();
        }

    }
    public User loginUser(String username, String password) throws InvalidCredentialsException {
            User user = dao.getUserByName(username);
            user.setAccounts(accountsDao.getUserAccounts(user.getUsername()));
        if (user.getPassword().equals(password)){
            Logging.logger.info("User: " + username + " was logged in");
                return user;
            }else {
                throw new InvalidCredentialsException();
            }
    }


    public void updateUserRole(User user) throws Exception {
        dao.updateUserRole(user);
        Logging.logger.warn("User: " + user.getUsername() + " 's Role has been changed to "+ user.getUserRole());
    }


    public void deleteUser(int userId){
        dao.deleteUser(userId);
    }
    public User saveUser(User userToSave){
        try {
            dao.saveUser(userToSave);
            Logging.logger.info("User: " + userToSave.getUsername() + " was Created");
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
