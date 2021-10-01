package controller;

import dao.DAOUtilities;
import dao.TransactionDao;
import model.Transaction;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class TransactionController {
    //Call DAO method
    TransactionDao dao = DAOUtilities.getTransactionDao();

    public List<Transaction> getAllTransactions(){
        return dao.getAllTransactions();
    }
    public Transaction getTransaction(int transactionId){
        return dao.getTransaction(transactionId);
    }
    public void deleteTransaction(int transactionId){
        dao.deleteTransaction(transactionId);
    }
    public void saveTransaction(Transaction transactionToSave){
        try {
            dao.saveTransaction(transactionToSave);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("ID is already in use");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem creating the Transaction at this time");
        }
    }
}
