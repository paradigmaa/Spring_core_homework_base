package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.DefaultInputValidator;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;

@Component
public class CloseAccountCommand implements OperationCommand {
    private final Scanner scanner;

    private final UserAccountService userAccountService;

    private final DefaultInputValidator defaultInputValidator;


    public CloseAccountCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidator) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidator = defaultInputValidator;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта, который хотите закрыть");
        String input = scanner.nextLine();
        Long accountId = defaultInputValidator.inputValidLong(input, "id аккаунта");
            userAccountService.closeAccount(accountId);
            System.out.println("Аккаунт закрыт");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
