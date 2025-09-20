package school.sorokin.javacore.spring_core_homework_base.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;

import java.math.BigDecimal;


@Service
public class UserAccountService {

    private final UserService userService;
    private final AccountService accountService;


    @Autowired
    public UserAccountService(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public User createUserAndAccount(String login) {
        User user = userService.createUser(login);
        Account account = accountService.createdAccount(user.getId());
        user.addAccountList(account);
        return user;
    }

    public void getAllUsers() {
        userService.getAllUsers();
    }

    public void createAccount(Long idUser) {
        User user = userService.findUserById(idUser);
        Account newAccount = accountService.createdAccount(user.getId());
        user.addAccountList(newAccount);
    }

    public void closeAccount(Long accountId) {
        Account account = accountService.findAccountById(accountId);
        User user = userService.findUserById(account.getUserId());
        accountService.accountClose(account.getId());
        user.removeAccount(account);
    }

    public void depositAccount(Long id, BigDecimal deposit) {
        accountService.accountDeposit(id, deposit);
    }

    public void transfer(Long fromId, Long toId, BigDecimal deposit) {
        accountService.accountTransfer(fromId, toId, deposit);
    }

    public void withdrawAccount(Long accountId, BigDecimal withdraw) {
        accountService.accountWithdraw(accountId, withdraw);
    }
}