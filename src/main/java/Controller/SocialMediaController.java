package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    // Constructor
    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
     /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler); // Example
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessagesByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesByIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) { // Example
        context.json("sample text");
    }
    private void postRegisterHandler(Context context) {
        try {
            // Parse the request into account object
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(context.body(), Account.class);

            // Call the service validations
            Account registerAccount = accountService.registerAccount(account);

            if (registerAccount != null) {
                context.json(registerAccount).status(200);
            }
        } catch (IllegalArgumentException e) {
            context.status(400);
        } catch (JsonProcessingException  e) {
            // context.status(400).result("Invalid JSON format: " + e.getMessage());
            context.status(400);
        }
    }

    private void postLoginHandler(Context context) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(context.body(), Account.class);

            Account loginAccount = accountService.loginAccount(account);

            if (loginAccount != null) {
                context.json(loginAccount).status(200);
            }
        } catch (IllegalArgumentException e) {
            context.status(401);

        } catch (JsonProcessingException e) {
            context.status(400);
        }
    }
    private void postMessagesHandler(Context context) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(context.body(), Message.class);

            Message postMessage = messageService.postMessages(message);
            
            if (postMessage != null) {
                context.json(postMessage).status(200);
            }
        } catch (JsonProcessingException e) {
            context.status(400);
        }
    }
    private void getAllMessagesHandler(Context context) {
        context.json("sample text");
    }
    private void getMessagesByIdHandler(Context context) {
        context.json("sample text");
    }
    private void deleteMessagesByIdHandler(Context context) {
        context.json("sample text");
    }
    private void patchMessagesByIdHandler(Context context) {
        context.json("sample text");
    }
    private void getAccountMessagesByIdHandler(Context context) {
        context.json("sample text");
    }


}