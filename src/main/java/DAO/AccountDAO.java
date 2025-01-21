package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    // GET usernameExist
    public boolean usernameExist(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM Account WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }


        } catch(SQLException e) {
            System.out.println("Error checking account: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return false;
    }

    // POST /register
    public Account registerAccount(Account account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
            connection = ConnectionUtil.getConnection();

            // SQL Statement
            String sql = "INSERT INTO Account(username, password) VALUES (?, ?)";

            // Prepare Statement
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set Paramenters
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Execute Statement
            preparedStatement.executeUpdate();

            // Result Set
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generated_acount_id = (int) resultSet.getLong(1);
                return new Account(generated_acount_id, account.getUsername(), account.getPassword());
            }


        } catch(SQLException e) {
            System.out.println("Error inserting account: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }

    // POST /login 
    public Account loginAccount(Account account) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
            connection = ConnectionUtil.getConnection();

            // SQL Statement
            String sql = "SELECT account_id, username, password FROM Account WHERE username = ? AND password = ?";

            // Prepare Statement
            preparedStatement = connection.prepareStatement(sql);

            // Set Paramenters
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Result Set
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Retrieve fields
                int accountId = resultSet.getInt("account_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                // Return object
                return new Account(accountId, username, password);
            }


        } catch(SQLException e) {
            System.out.println("Error retrieving: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch(SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        return null;
    }
    
}
