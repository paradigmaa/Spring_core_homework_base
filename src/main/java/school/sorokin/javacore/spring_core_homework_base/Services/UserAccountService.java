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

    public void createUserAndDefaultAccount(User user) {
        User newUser = userService.createUser(user);
        accountService.createdAccount(newUser);
    }

    public void getAllUsers() {
        userService.getAllUsers();
    }

    public void createAccount(Long userId) {
        User user = userService.findUserById(userId);
        accountService.createdAccount(user);
    }

    public void closeAccount(Long accountId) {
        Account removeAccount = accountService.findAccountById(accountId);
        User user = userService.findUserById(removeAccount.getUser().getId());
        accountService.accountClose(removeAccount.getId());

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