package school.sorokin.javacore.spring_core_homework_base.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.sorokin.javacore.spring_core_homework_base.Exception.*;
import school.sorokin.javacore.spring_core_homework_base.Services.UserAccountService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;


@Component
public class OperationsConsoleListener {
    private final UserAccountService userAccountService;

    @Autowired
    public OperationsConsoleListener(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Выберите пункт из меню");
                System.out.println("1. USER_CREATE - Создание нового пользователя.");
                System.out.println("2. SHOW_ALL_USERS - Отображение списка всех пользователей.");
                System.out.println("3. ACCOUNT_CREATE - Создание нового счета для пользователя.");
                System.out.println("4. ACCOUNT_CLOSE - Закрытие счета.");
                System.out.println("5. ACCOUNT_DEPOSIT - Пополнение счета.");
                System.out.println("6. ACCOUNT_TRANSFER - Перевод средств между счетами.");
                System.out.println("7. ACCOUNT_WITHDRAW - Снятие средств со счета.");
                String menu = scanner.nextLine().trim();
                if(menu.isEmpty()){
                    continue;
                }
                switch (menu) {
                    case "1" -> {
                        System.out.println("Введите логин для создания пользователя");
                        String login = scanner.nextLine();
                        userAccountService.createUserAndAccount(login);
                        System.out.println("Пользователь и базовый аккаунт созданы");
                    }
                    case "2" -> userAccountService.getAllUsers();
                    case "3" -> {
                        System.out.println("Введите ID пользователя, для которого нужно создать новый аккаунт");
                        Long idUser = scanner.nextLong();
                        scanner.nextLine();
                        userAccountService.createAccount(idUser);
                        System.out.println("Аккаунт создан");
                    }
                    case "4" -> {
                        System.out.println("Введите ID аккаунта, который хотите закрыть");
                        Long accountId = scanner.nextLong();
                        scanner.nextLine();
                        userAccountService.closeAccount(accountId);
                        System.out.println("Аккаунт закрыт");
                    }

                    case "5" -> {
                        System.out.println("Введите ID аккаунта");
                        Long id = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("Введите сумму, которую хотите положить на счёт");
                        BigDecimal sum = scanner.nextBigDecimal();
                        scanner.nextLine();
                        userAccountService.depositAccount(id, sum);
                        System.out.println("Операция выполнена");
                    }

                    case "6" -> {
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

                    case "7" -> {
                        System.out.println("Введите ID аккаунта");
                        Long id = scanner.nextLong();
                        scanner.nextLine();
                        System.out.println("Введите сумму, которую хотите снять со счета");
                        BigDecimal sum = scanner.nextBigDecimal();
                        scanner.nextLine();
                        userAccountService.withdrawAccount(id, sum);
                        System.out.println("Операция выполнена");
                    }
                }
            }catch (AccountWithdrawException | FindAccountByIdException | FindUserByIdException |
                    TransferFromAccounException | AccountDepositException | UserIncorrectEnter | IllegalStateException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("Введите корректное значение");
            }
        }
    }
}
