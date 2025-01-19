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
            System.out.println("Username already exists: " + account.getUsername());
            return null;
        }
        return accountDAO.insertAccount(account);
    }
    
}
