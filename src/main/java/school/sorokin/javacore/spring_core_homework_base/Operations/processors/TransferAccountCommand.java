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
public class TransferAccountCommand implements OperationCommand {
    private final Scanner scanner;

    private final UserAccountService userAccountService;

    private final DefaultInputValidator defaultInputValidator;

    public TransferAccountCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidator) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidator = defaultInputValidator;
    }


    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта с которого хотите перевести деньги");
        String inputFromId = scanner.nextLine();
        Long fromId = defaultInputValidator.inputValidLong(inputFromId, "ID аккаунта отправителя");

        System.out.println("Введите ID аккаунта на который хотите перевести деньги");
        String inputToId = scanner.nextLine();
        Long toId = defaultInputValidator.inputValidLong(inputToId, "ID аккаунта получателя");

        System.out.println("Введите сумму перевода");
        String sumInput = scanner.nextLine();
        BigDecimal sum = defaultInputValidator.validateBigDecimalInput(sumInput, "Сумма перевода");

        userAccountService.transfer(fromId, toId, sum);
        System.out.println("Перевод выполнен успешно");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
