package Service;

import Model.Account;
// import io.javalin.validation.ValidationException;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    // Register Account Service
    public Account registerAccount(Account account) {
        if (accountDAO.usernameExist(account.getUsername())) {
            throw new IllegalArgumentException("Username already exist");
        }
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
        return accountDAO.registerAccount(account);
    }

        // Login Account Service
        public Account loginAccount(Account account) {
            Account retrievedAccount = accountDAO.loginAccount(account);
            if (retrievedAccount == null) {
                throw new IllegalArgumentException("Incorrect username or password");
            }
            return retrievedAccount;
        }
    
}
