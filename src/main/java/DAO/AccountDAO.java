package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
// Change later for reusability, as of now, just test if it works; needs to refactor
public class AccountDAO {
    // GET usernameExist
    public boolean usernameExist(String username) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }


        } catch(SQLException e) {
            System.out.println("Error checking account: " + e.getMessage());
        }
        return false;
    }

    // POST /register
    public Account registerAccount(Account account) {
        try {
            // Establish connection
            Connection connection = ConnectionUtil.getConnection();

            // SQL Statement
            String sql = "INSERT INTO Account(username, password) VALUES (?, ?)";

            // Prepare Statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set Paramenters
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            // Execute Statement
            preparedStatement.executeUpdate();

            // Result Set
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generated_acount_id = (int) resultSet.getLong(1);
                return new Account(generated_acount_id, account.getUsername(), account.getPassword());
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // POST /login 
    public Account loginAccount(Account account) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT account_id, username, password FROM Account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Retrieve fields
                int accountId = resultSet.getInt("account_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                // Return object
                return new Account(accountId, username, password);
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
