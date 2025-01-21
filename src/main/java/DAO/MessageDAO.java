package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;


public class MessageDAO {
    // Check if Posted_by is exist
    public boolean postByExist(Integer postBy) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionUtil.getConnection();
            String sql = "SELECT account_id FROM Account WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postBy);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch(SQLException e) {
            System.out.println("Error retrieving account: " + e.getMessage());
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

    // POST messages
    public Message postMessages(Message message) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionUtil.getConnection();

            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generated_message_id = (int) resultSet.getLong(1);
                return new Message(
                    generated_message_id, 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                    );
            }

        } catch(SQLException e) {
            System.out.println("Error post messages: " + e.getMessage());
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
    // GET all messages
    // GET messages by id
    // DELETE messages by id
    // PATCH messages by id
    // GET messages by accound id
    
}
