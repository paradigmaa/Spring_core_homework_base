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
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }
        try {
            Long id = Long.parseLong(input);
            if (id < 0) {
                throw new IllegalArgumentException("Id не может быть отрицательным");
            }
            System.out.println("Введите сумму, которую хотите положить на счёт");
            String sumInput = scanner.nextLine();
            if (sumInput.trim().isEmpty()) {
                throw new IllegalArgumentException("Сумма не может быть пустой");
            }
            try {
                BigDecimal sum = new BigDecimal(sumInput.trim());
                if (sum.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Сумма пополнения не может быть отрицательной");
                }
                userAccountService.depositAccount(id, sum);
                System.out.println("Операция пополнения выполнена");
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Введите корректную сумму");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Для продолжения введите число");
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
