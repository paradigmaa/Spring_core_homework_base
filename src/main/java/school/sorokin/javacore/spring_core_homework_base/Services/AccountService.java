package school.sorokin.javacore.spring_core_homework_base.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Exception.AccountDepositException;
import school.sorokin.javacore.spring_core_homework_base.Exception.AccountWithdrawException;
import school.sorokin.javacore.spring_core_homework_base.Exception.FindAccountByIdException;
import school.sorokin.javacore.spring_core_homework_base.Exception.TransferFromAccounException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountService {
    private final List<Account> allAccounts = new ArrayList<>();
    private Long idIncrement = 1L;
    @Value("${account.moneyAmount}")
    private BigDecimal defaultAmount;
    @Value("${account.commissionPayment}")
    private BigDecimal commissionPayment;


    public Account createdAccount(Long userId) {
        Account account = new Account(idIncrement++, userId, defaultAmount);
        allAccounts.add(account);
        return account;
    }

    public Account accountDeposit(Long id, BigDecimal deposit) {
        if(deposit.compareTo(BigDecimal.ZERO) <= 0){
            throw new AccountDepositException("Введите значение больше нуля");
        }
        Account accountDeposit = findAccountById(id);
        accountDeposit.setMoneyAmount(accountDeposit.getMoneyAmount().add(deposit));
        return accountDeposit;
    }

    public Account accountWithdraw(Long id, BigDecimal deposit) {
        Account accountWithdraw = findAccountById(id);
        if (accountWithdraw.getMoneyAmount().compareTo(deposit) >= 0) {
            accountWithdraw.setMoneyAmount(accountWithdraw.getMoneyAmount().subtract(deposit));
        } else {
            throw new AccountWithdrawException("На счету недостаточно средств для этой операции");
        }
        return accountWithdraw;
    }

    public void accountTransfer(Long from, Long to, BigDecimal sum) {
        Account accountFrom = findAccountById(from);
        Account accountTo = findAccountById(to);
        if (accountFrom.getUserId().equals(accountTo.getUserId())) {
            if (accountFrom.getMoneyAmount().compareTo(sum) >= 0) {
                accountFrom.setMoneyAmount(accountFrom.getMoneyAmount().subtract(sum));
                accountTo.setMoneyAmount(accountTo.getMoneyAmount().add(sum));
                System.out.println("Транзация между своими счетами прошла успешно");
            }else {
                throw new TransferFromAccounException("Недостаточно средств для перевода между своими счетами");
            }

        } else {
            BigDecimal percent = sum.multiply(commissionPayment).divide(BigDecimal.valueOf(100));
            if (accountFrom.getMoneyAmount().compareTo(sum.add(percent)) >= 0) {
                accountFrom.setMoneyAmount(accountFrom.getMoneyAmount().subtract(sum.add(percent)));
                accountTo.setMoneyAmount(accountTo.getMoneyAmount().add(sum));
                System.out.println("Транзакция между чужими счетами прошла успешно, комиссия: " + percent);
            }else {
                throw new TransferFromAccounException("Недостаточно средств для перевода на чужой счёт(комиссия 1,5%)");
            }
        }
    }


    public Account findAccountById(Long id) {
        Optional<Account> account = allAccounts.stream().filter(ac -> ac.getId()
                .equals(id)).findAny();
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new FindAccountByIdException("Такого аккаунта не существует");
        }
    }

    public void accountClose(Long id) {
        Account account = findAccountById(id);
        List<Account> closeAc = allAccounts.stream().filter(ac -> ac.getUserId().equals(account.getUserId()))
                .sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());

        if(closeAc.size() == 1){
            throw new IllegalStateException("Нельзя закрыть последний аккаунт пользователя");
        }
        else if (closeAc.size() > 1 && closeAc.getLast().getMoneyAmount().compareTo(BigDecimal.ZERO) > 0) {
            accountTransfer(account.getId(), closeAc.get(0).getId(), account.getMoneyAmount());
        }
        closeAc.remove(account);
        allAccounts.remove(account);
    }
}