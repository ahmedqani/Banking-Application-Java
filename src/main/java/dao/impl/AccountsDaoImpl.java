package dao.impl;

import dao.AccountsDao;
import dao.DAOUtilities;
import model.Accounts;
import model.Transaction;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDaoImpl implements AccountsDao {
    @Override
    public List<Accounts> getAllAccounts() {
        List<Accounts> accounts = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            stmt = connection.createStatement();

            String sql = "SELECT * FROM ACCOUNTS ";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Accounts a = new Accounts();
                a.setAccount_id(rs.getLong("account_id"));
                a.setAccount_type(rs.getString("account_type"));
                a.setSharedAccount(rs.getString("sharedAccount"));
                a.setBalance(rs.getLong("balance"));
                a.setP_account_owner(rs.getString("p_account_owner"));
                a.setS_account_owner(rs.getString("s_account_owner"));
                a.setActive(rs.getString("isActive"));
                accounts.add(a);
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

        return accounts;
    }

    @Override
    public List<Accounts> getUserAccounts(String accountUser) {
        List<Accounts> accounts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "SELECT * FROM ACCOUNTS WHERE p_account_owner = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, accountUser);


            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Accounts a = new Accounts();
                a.setAccount_id(rs.getLong("account_id"));
                a.setAccount_type(rs.getString("account_type"));
                a.setSharedAccount(rs.getString("sharedAccount"));
                a.setBalance(rs.getLong("balance"));
                a.setP_account_owner(rs.getString("p_account_owner"));
                a.setS_account_owner(rs.getString("s_account_owner"));
                a.setActive(rs.getString("isActive"));
                accounts.add(a);
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
        return accounts;
    }

    @Override
    public Accounts getAccountById(long id) {
        Accounts account = new Accounts();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "SELECT * FROM ACCOUNTS WHERE account_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                account.setAccount_id(rs.getLong("account_id"));
                account.setAccount_type(rs.getString("account_type"));
                account.setSharedAccount(rs.getString("sharedAccount"));
                account.setBalance(rs.getLong("balance"));
                account.setP_account_owner(rs.getString("p_account_owner"));
                account.setS_account_owner(rs.getString("s_account_owner"));
                account.setActive(rs.getString("isActive"));
            }else {
                account.setAccount_id(0);
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
        return account;
    }

    @Override
    public Accounts saveAccount(Accounts accountToSave) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO ACCOUNTS VALUES (?,?,?,?,?,?,?)";

            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setLong(1, accountToSave.getAccount_id());
            stmt.setString(2, accountToSave.getAccount_type());
            stmt.setString(3, accountToSave.isSharedAccount());
            stmt.setLong(4, accountToSave.getBalance());
            stmt.setString(5, accountToSave.getP_account_owner());
            stmt.setString(6, accountToSave.getS_account_owner());
            stmt.setString(7, accountToSave.isActive());

            success = stmt.executeUpdate();
            return accountToSave;
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
            throw new Exception("Insert account failed: " + accountToSave);
        }
        return accountToSave;
    }

    @Override
    public void updateAccountBalance(Accounts accounts) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "update accounts set balance = ? where account_id = ?";


            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setLong(1, accounts.getBalance());
            stmt.setLong(2, accounts.getAccount_id());


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
            throw new Exception("Insert account failed: " + accounts);
        }

    }

    @Override
    public void updateSecondaryOwner(Accounts s_user) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "update accounts set s_account_owner = ?, sharedaccount = ? where account_id = ?";


            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setString(1, s_user.getS_account_owner());
            stmt.setString(2, s_user.isSharedAccount());
            stmt.setLong(3, s_user.getAccount_id());


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
            throw new Exception("Insert account failed: " + s_user);
        }
    }

    @Override
    public void activateAccount(Accounts accountToActivate) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "update accounts set isActive = ? where account_id = ?";


            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setString(1, accountToActivate.isActive());
            stmt.setLong(2, accountToActivate.getAccount_id());


            success = stmt.executeUpdate();
            if (accountToActivate.isAccountIsActive()){
                System.out.println("Account has been Activated ");
            }
            if (!accountToActivate.isAccountIsActive()){
                System.out.println("Account has been Denied Or Closed!");
            }
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
            throw new Exception("Insert account failed: " + accountToActivate);
        }
    }
}
