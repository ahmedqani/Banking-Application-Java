package dao.impl;

import dao.DAOUtilities;
import dao.UserDao;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            stmt = connection.createStatement();

            String sql = "SELECT * FROM USER";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User a = new User();
                a.setId(rs.getLong("userid"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setUserRole(rs.getString("user_role"));
                a.setBalance(rs.getLong("balance"));
                a.setAccountIsActive(Boolean.parseBoolean(rs.getString("is_active")));

                users.add(a);
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

        return users;
    }

    @Override
    public User getUser(int userid) {
        User user = new User();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "SELECT * FROM USER WHERE userid = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userid);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                user.setId(rs.getLong("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("user_role"));
                user.setBalance(rs.getLong("balance"));
                user.setAccountIsActive(Boolean.parseBoolean(rs.getString("is_active")));
            }else {
                user.setId(0);
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
        return user;
    }

    @Override
    public User getUserByName(String username) {
        User user = new User();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "SELECT * FROM USER WHERE username = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                user.setId(rs.getLong("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("user_role"));
                user.setBalance(rs.getLong("balance"));
                user.setAccountIsActive(Boolean.parseBoolean(rs.getString("is_active")));
            }else {
                user.setId(0);
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
        return user;
    }
    @Override
    public void deleteUser(int userid) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DAOUtilities.getConnection();

            String sql = "DELETE FROM USER WHERE userid = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userid);

            stmt.executeUpdate();
            System.out.println("User Deleted!!");


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
    public User saveUser(User userToRegister) throws Exception {
        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "INSERT INTO USER VALUES (?,?,?,?,?,?)";

            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setLong(1, userToRegister.getId());
            stmt.setString(2, userToRegister.getUsername());
            stmt.setString(3, userToRegister.getPassword());
            stmt.setString(4, userToRegister.getUserRole());
            stmt.setLong(5, userToRegister.getBalance());
            stmt.setString(6, "false");


            success = stmt.executeUpdate();
            return userToRegister;
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
            throw new Exception("Insert user failed: " + userToRegister);
        }

        return userToRegister;
    }

    @Override
    public void updateUserBalance(User userBalanceToUpdate) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "update user set balance = ? where userid = ?";


            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setLong(1, userBalanceToUpdate.getBalance());
            stmt.setLong(2, userBalanceToUpdate.getId());


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
            throw new Exception("Insert user failed: " + userBalanceToUpdate);
        }
    }

    @Override
    public void updateUserRole(User userRoleToUpdate) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "update user set user_role = ? where userid = ?";


            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setString(1, userRoleToUpdate.getUserRole());
            stmt.setLong(2, userRoleToUpdate.getId());


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
            throw new Exception("Insert user failed: " + userRoleToUpdate);
        }
    }

    @Override
    public void activateUser(User userToActivate) throws Exception {

        Connection connection = null;
        PreparedStatement stmt = null;
        int success = 0;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "update user set is_active = ? where userid = ?";


            // Setup PreparedStatement
            stmt = connection.prepareStatement(sql);

            // Add parameters from user into PreparedStatement
            stmt.setString(1, String.valueOf(userToActivate.isAccountIsActive()));
            stmt.setLong(2, userToActivate.getId());


            success = stmt.executeUpdate();
            if (userToActivate.isAccountIsActive()){
                System.out.println("User has been Activated ");
            }
            if (!userToActivate.isAccountIsActive()){
                System.out.println("User account has been Denied Or Closed!");
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
            throw new Exception("Insert user failed: " + userToActivate);
        }
    }


}
