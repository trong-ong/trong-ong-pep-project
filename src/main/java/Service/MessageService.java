package Service;

import Model.Message;
import io.javalin.validation.ValidationException;
import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Post Message Service
    public Message postMessages(Message messages) {
        // Message text not blank and is not over 255 characters
        if (messages.getMessage_text() == null || messages.getMessage_text().isBlank()) {
            throw new IllegalArgumentException("Messages cannot be blank");
        }
        if (messages.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Message text cannot exceed 255 characters");
        }
        if (!messageDAO.postByExist(messages.getPosted_by())) {
            throw new IllegalArgumentException("No existing account");
        }
        System.out.println("message_text: " + messages.getMessage_text());
        return messageDAO.postMessages(messages);

    }
    
}
