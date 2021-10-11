package dao;

import model.Transaction;
import model.User;

import java.util.List;

public interface TransactionDao {

    /**
     * Used to retrieve a list of all Transactions
     * @return List<Transaction>
     */
    List<Transaction> getAllTransactions();

    Transaction getTransaction(int transactionid);


    List<Transaction> getTransactionByUser(User user);

    void deleteTransaction(int userid);

    /**
     * Used to persist the user to the datastore
     * @param transactionToRegister
     */

    void saveTransaction(Transaction transactionToRegister) throws Exception;

}
