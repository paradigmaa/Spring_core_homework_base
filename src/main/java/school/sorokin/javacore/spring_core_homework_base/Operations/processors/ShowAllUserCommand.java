package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.DefaultInputValidator;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;


@Component
public class ShowAllUserCommand implements OperationCommand {

    private final Scanner scanner;

    private final UserAccountService userAccountService;

    private final DefaultInputValidator defaultInputValidator;

    public ShowAllUserCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidator) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidator = defaultInputValidator;
    }

    @Override
    public void execute() {
        System.out.println("Введите размер страницы");
        String input = scanner.nextLine();
        int pageSize = defaultInputValidator.validatePageSize(input);

        userAccountService.showAllUsers(pageSize);

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
