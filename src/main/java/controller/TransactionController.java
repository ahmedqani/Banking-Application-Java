package controller;

import dao.DAOUtilities;
import dao.TransactionDao;
import dao.UserDao;
import exceptions.TransactionGeneralException;
import model.Transaction;
import model.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class TransactionController {
    //Call DAO method
    TransactionDao daoTransaction = DAOUtilities.getTransactionDao();
    UserDao daoUser = DAOUtilities.getUserDao();


    public List<Transaction> getAllTransactions(){
        return daoTransaction.getAllTransactions();
    }
    public List<Transaction> getTransactionsByUser(User user){
        return daoTransaction.getTransactionByUser(user);
    }
    public Transaction getTransaction(int transactionId){
        return daoTransaction.getTransaction(transactionId);
    }
    public void deleteTransaction(int transactionId){
        daoTransaction.deleteTransaction(transactionId);
    }
    public void saveTransaction(Transaction transactionToSave, User userFrom, User userTo){
        try {
            long amount = transactionToSave.getAmount();
            if (transactionToSave.getTransactionType().equalsIgnoreCase("withdraw")
            || transactionToSave.getTransactionType().equalsIgnoreCase("transfer")){
                if ((userFrom.getBalance() - amount) >= 0){
                    daoTransaction.saveTransaction(transactionToSave);
                    userTo.setBalance(userTo.getBalance()+amount);
                    userFrom.setBalance(userFrom.getBalance()-amount);
                    daoUser.updateUserBalance(userTo);
                    daoUser.updateUserBalance(userFrom);
                    System.out.printf("you have successfully %s -ed to %s \n", transactionToSave.getTransactionType(),userTo.getUsername());
                }else {
                    throw new TransactionGeneralException("Please make sure your Balance is enough to cover the Transaction");
                }
            }
            if (transactionToSave.getTransactionType().equalsIgnoreCase("DEPOSIT")){
                daoTransaction.saveTransaction(transactionToSave);
                userFrom.setBalance(userFrom.getBalance()+amount);
                daoUser.updateUserBalance(userFrom);
                System.out.printf("You have Successfully deposited %d \n", amount);

            }
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("ID is already in use");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem creating the Transaction at this time");
        }
    }
}
