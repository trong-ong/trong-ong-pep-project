package DAO;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

// Change later for reusability, as of now, just test if it works; needs to refactor
public class MessageDAO {
    // Check if Posted_by is exist
    public boolean postByExist(Integer postBy) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            String sql = "SELECT account_id FROM Account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, postBy);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // POST messages
    public Message postMessages(Message message) {
        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
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
            System.out.println(e.getMessage());
        }
        return null;
    }
    // GET all messages
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // GET messages by id
    public Message getMessagesById(int messageId) {
        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // DELETE messages by id
    public Message deleteMessagesById(int messageId) {
        try {
            Connection connection = ConnectionUtil.getConnection();
            // Retrieve the message
            String sqlSelect = "SELECT * FROM Message Where message_id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(sqlSelect);
            selectStatement.setInt(1, messageId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                Message message =  new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                // Delete the retrieved message
                String sqlDelete = "DELETE FROM Message WHERE message_id = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete);
                deleteStatement.setInt(1, messageId);
    
                return message;
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // PATCH messages by id
    public Message patchMessagesById(int messageId, String message) {

        try {
            Connection connection = ConnectionUtil.getConnection();
            
            // Update the message
            String sqlUpdate = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(sqlUpdate);
            updateStatement.setString(1, message);
            updateStatement.setInt(2, messageId);

            // Execute the query
            // Check if update is sucessful
            int update = updateStatement.executeUpdate();
            if (update == 0) {
                return null;
            }
            // Select the updated message (This already been done, just reuse other, but for now test it)
            String sqlSelect = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(sqlSelect);
            selectStatement.setInt(1, messageId);

            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // GET messages by accound id
    public List<Message> getAccountMessagesById(int accountId) {
        List<Message> messages = new ArrayList<>();

        try {
            Connection connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message =  new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}