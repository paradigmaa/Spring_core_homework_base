package school.sorokin.javacore.spring_core_homework_base.Operations.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class TransferAccountCommand implements OperationCommand {
    private final Scanner scanner;

    private final UserAccountService userAccountService;

    @Autowired
    public TransferAccountCommand(Scanner scanner, UserAccountService userAccountService) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта с которого хотите перевести деньги");
        Long fromId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите ID аккаунта на который хотите перевести деньги");
        Long toId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Введите сумму перевода");
        BigDecimal sum = scanner.nextBigDecimal();
        scanner.nextLine();
        userAccountService.transfer(fromId, toId, sum);
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
