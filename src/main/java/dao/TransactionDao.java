package dao;

import model.Transaction;

import java.util.List;

public interface TransactionDao {

    /**
     * Used to retrieve a list of all Animals
     * @return
     */
    List<Transaction> getAllTransactions();

    Transaction getTransaction(int transactionid);

    void deleteTransaction(int userid);

    /**
     * Used to persist the user to the datastore
     * @param transactionToRegister
     */

    void saveTransaction(Transaction transactionToRegister) throws Exception;

}
