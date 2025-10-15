package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.DefaultInputValidator;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;

@Component
public class CreateAnAccountForTheUseCommand implements OperationCommand {

    private final Scanner scanner;

    private final UserAccountService userAccountService;

    private final DefaultInputValidator defaultInputValidator;

    public CreateAnAccountForTheUseCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidator) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidator = defaultInputValidator;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID пользователя, для которого нужно создать новый аккаунт");
        String input = scanner.nextLine();
        Long idUser = defaultInputValidator.inputValidLong(input, "Id пользователя для создания нового аккаунта");
        userAccountService.createdAccountForUser(idUser);
        System.out.println("Новый аккаунт для указанного пользователя создан");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
