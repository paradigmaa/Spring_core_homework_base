package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.DefaultInputValidator;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;

@Component
public class CreateUserCommand implements OperationCommand {

    private final Scanner scanner;

    private final UserAccountService userAccountService;

    private final DefaultInputValidator defaultInputValidator;


    public CreateUserCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidator) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidator = defaultInputValidator;
    }

    @Override
    public void execute() {
        System.out.println("Введите логин для создания нового пользователя");
        String login = scanner.nextLine();
        String validatedLogin = defaultInputValidator.validateStringInput(login, "Логин");

        userAccountService.createUserAndDefaultAccount(new User(validatedLogin));
        System.out.println("Пользователь и базовый аккаунт созданы");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
