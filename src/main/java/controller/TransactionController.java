package controller;

import dao.DAOUtilities;
import dao.TransactionDao;
import dao.UserDao;
import exceptions.TransactionGeneralException;
import logging.Logging;
import model.Transaction;
import model.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class TransactionController {
    //Call DAO method
    TransactionDao daoTransaction = DAOUtilities.getTransactionDao();


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
    public void saveTransaction(Transaction transactionToSave) throws Exception {
        daoTransaction.saveTransaction(transactionToSave);
        Logging.logger.info("A New " +transactionToSave.getTransactionType() +
                " Transaction Has been Created with Amount : "+ transactionToSave.getAmount() + " By "+transactionToSave.getFromUserName());

    }
}
