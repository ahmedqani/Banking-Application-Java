package dao.impl;

import dao.DAOUtilities;
import dao.TransactionDao;
import model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            stmt = connection.createStatement();

            String sql = "SELECT * FROM TRANSACTION ";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Transaction a = new Transaction();
                a.setId(rs.getLong("transaction_id"));
                a.setAmount(rs.getLong("amount"));
                a.setFromUserName(rs.getString("from_Username"));
                a.setTransactionType(rs.getString("transaction_type"));
                a.setToUserId(rs.getLong("to_UserId"));

                transactions.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return transactions;
    }

    @Override
    public Transaction getTransaction(int transactionid) {
        Transaction transaction = new Transaction();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "SELECT * FROM TRANSACTION WHERE transaction_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, transactionid);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                transaction.setId(rs.getLong("transaction_id"));
                transaction.setAmount(rs.getLong("amount"));
                transaction.setFromUserName(rs.getString("from_username"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setToUserId(rs.getLong("to_userid"));
            }else {
                transaction.setId(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transaction;
    }

    @Override
    public void deleteTransaction(int transactionid) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "DELETE FROM TRANSACTION WHERE transaction_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, transactionid);

            stmt.executeUpdate();
            System.out.println("Transaction Deleted!!");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveTransaction(Transaction transactionToRegister) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO TRANSACTION VALUES (?,?,?,?,?)";

            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setLong(1, transactionToRegister.getId());
            stmt.setLong(2, transactionToRegister.getAmount());
            stmt.setString(3, transactionToRegister.getFromUserName());
            stmt.setString(4, transactionToRegister.getTransactionType());
            stmt.setLong(5, transactionToRegister.getToUserId());



            success = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (success == 0) {
            // then update didn't occur, throw an exception
            throw new Exception("Insert user failed: " + transactionToRegister);
        }

    }


}
