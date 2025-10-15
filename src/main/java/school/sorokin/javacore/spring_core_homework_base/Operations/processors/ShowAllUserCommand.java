package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.util.Scanner;


@Component
public class ShowAllUserCommand implements OperationCommand {

    private final Scanner scanner;

    private final UserAccountService userAccountService;


    @Autowired
    public ShowAllUserCommand(Scanner scanner, UserAccountService userAccountService) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
    }

    @Override
    public void execute() {
        System.out.println("Введите размер страницы");
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        try {
            int res = Integer.parseInt(input);
            if (res < 0) {
                throw new IllegalArgumentException("Недопустимое значение");
            }
            userAccountService.showAllUsers(res);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите корректное значение");
        }

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }
}
