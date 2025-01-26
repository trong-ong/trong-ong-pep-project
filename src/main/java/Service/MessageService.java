package Service;
import java.util.*;
import Model.Message;
import DAO.MessageDAO;

// Change later for reusability, as of now, just test if it works; needs to refactor
public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Post Message Service
    public Message postMessages(Message message) {
        // If message is not blank
        if (message.getMessage_text() == null || message.getMessage_text().isBlank()) {
            throw new IllegalArgumentException();
        }
        // if message is greater than 255 char
        if (message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException();
        }
        // if post by account_id does not exist
        if (!messageDAO.postByExist(message.getPosted_by())) {
            throw new IllegalArgumentException();
        }
        return messageDAO.postMessages(message);

    }
    // Get All Message Service
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    } 

    // Get Message By Id
    public Message getMessagesById(int messageId) {
        Message message =  messageDAO.getMessagesById(messageId);
        if (message == null) {
            throw new IllegalArgumentException();
        }
        return message;
    }
    // Delete Message By Id
    // Later on would like to seperate DAO call
    public Message deleteMessagesById(int messageId) {
        Message message =  messageDAO.deleteMessagesById(messageId);
        if (message == null) {
            throw new IllegalArgumentException();
        }
        return message;
    }
    // PATCH messages by id
    // Later on would like to seperate DAO call
    public Message patchMessagesById(int messageId, String messages) {
        if (messages == null || messages.isBlank()) {
            throw new IllegalArgumentException();
        }
        if (messages.length() > 255) {
            throw new IllegalArgumentException();
        }
        
        Message updateMessage = messageDAO.patchMessagesById(messageId, messages);
        if (updateMessage == null) {
            throw new IllegalArgumentException();
        }
        return updateMessage;
    }
    // GET messages by accound id
    // Potentially needs to handle if account_id is not available
    public List<Message> getAccountMessagesById(int accountId) {
        return messageDAO.getAccountMessagesById(accountId);
    } 
    
}
