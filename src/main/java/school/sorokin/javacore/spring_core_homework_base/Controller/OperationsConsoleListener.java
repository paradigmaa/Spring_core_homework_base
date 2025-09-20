package school.sorokin.javacore.spring_core_homework_base.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Exception.*;
import school.sorokin.javacore.spring_core_homework_base.Operations.OperationCommand;
import school.sorokin.javacore.spring_core_homework_base.Operations.ConsoleOperationType;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;


@Component
public class OperationsConsoleListener implements Runnable {

    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    private final Scanner scanner;

    private static final Map<Integer, ConsoleOperationType> MENU_MAP = Map.of(
            1, ConsoleOperationType.USER_CREATE,
            2, ConsoleOperationType.SHOW_ALL_USERS,
            3, ConsoleOperationType.ACCOUNT_CREATE,
            4, ConsoleOperationType.ACCOUNT_CLOSE,
            5, ConsoleOperationType.ACCOUNT_DEPOSIT,
            6, ConsoleOperationType.ACCOUNT_TRANSFER,
            7, ConsoleOperationType.ACCOUNT_WITHDRAW
    );

    @Autowired
    public OperationsConsoleListener(List<OperationCommand> commandList, Scanner scanner) {
        this.commandMap = commandList.stream()
                .collect(Collectors.toMap(
                        OperationCommand::getOperationType,
                        cmd -> cmd));
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            print();
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                continue;
            }

            try {
                int choice = Integer.parseInt(input.trim());
                ConsoleOperationType type = MENU_MAP.get(choice);

                if (type == null) {
                    System.out.println("Неверный номер пункта меню. Попробуйте снова.");
                    continue;
                }

                OperationCommand command = commandMap.get(type);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Команда не найдена.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число от 1 до 7.");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public void print() {
        System.out.println("Выберите пункт из меню");
        System.out.println("1. USER_CREATE - Создание нового пользователя.");
        System.out.println("2. SHOW_ALL_USERS - Отображение списка всех пользователей.");
        System.out.println("3. ACCOUNT_CREATE - Создание нового счета для пользователя.");
        System.out.println("4. ACCOUNT_CLOSE - Закрытие счета.");
        System.out.println("5. ACCOUNT_DEPOSIT - Пополнение счета.");
        System.out.println("6. ACCOUNT_TRANSFER - Перевод средств между счетами.");
        System.out.println("7. ACCOUNT_WITHDRAW - Снятие средств со счета.");
    }
}