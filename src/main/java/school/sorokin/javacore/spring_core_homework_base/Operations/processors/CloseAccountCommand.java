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
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        try {
            Long accountId = Long.parseLong(input);
            if (accountId < 0) {
                throw new IllegalArgumentException("Id не может быть отрицательным");
            }
            userAccountService.closeAccount(accountId);
            System.out.println("Аккаунт закрыт");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Для продолжения введите число");
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
