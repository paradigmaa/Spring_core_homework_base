package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class DepositAccountCommand implements OperationCommand {

    private final Scanner scanner;

    private final UserAccountService userAccountService;

    @Autowired
    public DepositAccountCommand(Scanner scanner, UserAccountService userAccountService) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите сумму, которую хотите положить на счёт");
        BigDecimal sum = scanner.nextBigDecimal();
        scanner.nextLine();
        userAccountService.depositAccount(id, sum);
        System.out.println("Операция выполнена");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
