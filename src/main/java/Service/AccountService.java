package Service;
import Model.Account;
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
            throw new IllegalArgumentException();
        }
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException();
        }
        return accountDAO.registerAccount(account);
    }

    // Login Account Service
    public Account loginAccount(Account account) {
        Account retrievedAccount = accountDAO.loginAccount(account);
        if (retrievedAccount == null) {
            throw new IllegalArgumentException();
        }
        return retrievedAccount;
    }
    
}
