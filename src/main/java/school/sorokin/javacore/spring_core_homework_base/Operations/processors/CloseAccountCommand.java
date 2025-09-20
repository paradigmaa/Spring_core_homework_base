package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final Scanner scanner;

    private final UserAccountService userAccountService;

    @Autowired
    public CloseAccountCommand(Scanner scanner, UserAccountService userAccountService) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта, который хотите закрыть");
        Long accountId = scanner.nextLong();
        scanner.nextLine();
        userAccountService.closeAccount(accountId);
        System.out.println("Аккаунт закрыт");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
