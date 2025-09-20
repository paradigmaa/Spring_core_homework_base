package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;

@Component
public class CreateAnAccountForTheUseCommand implements OperationCommand {

    private final Scanner scanner;

    private final UserAccountService userAccountService;

    @Autowired
    public CreateAnAccountForTheUseCommand(Scanner scanner, UserAccountService userAccountService) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID пользователя, для которого нужно создать новый аккаунт");
        Long idUser = scanner.nextLong();
        scanner.nextLine();
        userAccountService.createAccount(idUser);
        System.out.println("Аккаунт создан");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
