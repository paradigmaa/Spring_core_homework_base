package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.DefaultInputValidator;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class DepositAccountCommand implements OperationCommand {
    private final Scanner scanner;

    private final UserAccountService userAccountService;

    private final DefaultInputValidator defaultInputValidator;


    public DepositAccountCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidator) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidator = defaultInputValidator;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта");
        String input = scanner.nextLine();
        Long id = defaultInputValidator.inputValidLong(input, "Id пользователя");
        System.out.println("Введите сумму, которую хотите положить на счёт");
        String sumInput = scanner.nextLine();
        BigDecimal sum = defaultInputValidator.validateBigDecimalInput(sumInput, "Сумма пополнения");
        userAccountService.depositAccount(id, sum);
        System.out.println("Операция пополнения выполнена");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
