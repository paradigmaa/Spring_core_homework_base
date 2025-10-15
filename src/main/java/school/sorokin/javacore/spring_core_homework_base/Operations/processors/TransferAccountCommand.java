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

    private final DefaultInputValidator defaultInputValidatorl;

    public TransferAccountCommand(Scanner scanner, UserAccountService userAccountService, DefaultInputValidator defaultInputValidatorl) {
        this.scanner = scanner;
        this.userAccountService = userAccountService;
        this.defaultInputValidatorl = defaultInputValidatorl;
    }

    @Override
    public void execute() {
        System.out.println("Введите ID аккаунта с которого хотите перевести деньги");
        String inputFromId = scanner.nextLine();
        if (inputFromId.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка отправителя не может быть пустой");
        }
        try {
            Long fromId = Long.parseLong(inputFromId);
            if (fromId < 0) {
                throw new IllegalArgumentException("Id отправителя не может быть отрицательным");
            }
            System.out.println("Введите ID аккаунта на который хотите перевести деньги");
            String inputToId = scanner.nextLine();
            if (inputToId.trim().isEmpty()) {
                throw new IllegalArgumentException("Строка получателя не может быть пустой");
            }
            try {
                Long toId = Long.parseLong(inputToId);
                if (toId < 0) {
                    throw new IllegalArgumentException("ID получателя не может быть отрицательным");
                }
                System.out.println("Введите сумму перевода");
                String sum = scanner.nextLine();
                if (sum.trim().isEmpty()) {
                    throw new IllegalArgumentException("Строка суммы не может быть пустой");
                }
                try {
                    BigDecimal sumTotal = new BigDecimal(sum.trim());
                    if (sumTotal.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException("Сумма перевода не может быть отрицательной");
                    }
                    userAccountService.transfer(fromId, toId, sumTotal);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Введите корректную сумму");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Введите корректные данные получателя");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введите корректные данные отправителя");
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
