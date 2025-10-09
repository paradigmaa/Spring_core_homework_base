package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Exception.*;

import java.math.BigDecimal;


@Service
public class AccountService {
    @Value("${account.moneyAmount}")
    private BigDecimal defaultAmount;
    @Value("${account.commissionPayment}")
    private BigDecimal commissionPayment;


    public void createdAccount(User user, Session session) {
        Account newAccount = new Account(user, defaultAmount);
        session.persist(newAccount);
    }


    public void createAccountForUser(Long id, Session session) {
        User userAccount = session.find(User.class, id);
        if (userAccount == null) {
            throw new UserCreatedException("Такого пользователя не существует");
        }
        Account newAccount = new Account(userAccount, defaultAmount);
        session.persist(newAccount);
    }

    public void accountDeposit(Long id, BigDecimal deposit, Session session) {
        Account accountDeposit = session.find(Account.class, id);
        if (accountDeposit == null) {
            throw new AccountDepositException("Проверьте id аккаунта, который хотите пополнить");
        }
        accountDeposit.setMoneyAmount(accountDeposit.getMoneyAmount().add(deposit));
    }

    public void accountWithdraw(Long id, BigDecimal deposit, Session session) {
        Account accountDeposit = session.find(Account.class, id);
        if (accountDeposit == null) {
            throw new AccountWithdrawException("Введите корректное значение пользователя");
        }
        if (accountDeposit.getMoneyAmount().compareTo(deposit) < 0) {
            throw new AccountWithdrawException("Недостаточная сумма для списания");
        }
        accountDeposit.setMoneyAmount(accountDeposit.getMoneyAmount().subtract(deposit));
    }

    public void accountTransfer(Long from, Long to, BigDecimal sum, Session session) {
        Account fromAccount = session.find(Account.class, from);
        Account toAccount = session.find(Account.class, to);

        if (fromAccount == null) {
            throw new TransferAccountException("Не найден аккаунт отправителя");
        }
        if (toAccount == null) {
            throw new TransferAccountException("Не найден аккаунт получателя");
        }
        if (fromAccount.getMoneyAmount().compareTo(sum) < 0) {
            throw new TransferAccountException("Недостаточно денег для перевода со счета на счёт");
        }
        if (fromAccount.getUser().getId().equals(toAccount.getUser().getId())) {
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount().subtract(sum));
            toAccount.setMoneyAmount(toAccount.getMoneyAmount().add(sum));
            System.out.println("Транзакция между своими счетами прошла успешно, комиссии нет");
        } else {
            BigDecimal percent = sum.multiply(commissionPayment).divide(BigDecimal.valueOf(100));
            if (fromAccount.getMoneyAmount().compareTo(sum.add(percent)) >= 0) {
                fromAccount.setMoneyAmount(fromAccount.getMoneyAmount().subtract(sum.add(percent)));
                toAccount.setMoneyAmount(toAccount.getMoneyAmount().add(sum));
                System.out.println("Транзакция между чужими счетами прошла успешно, комиссия: " + percent);
            } else {
                throw new TransferAccountException("Недостаточно средств для перевода на чужой счёт(комиссия 1,5%)");
            }
        }
    }

    public void closeAccount(Long id, Session session) {
        Account account = session.createQuery("SELECT a FROM Account a" +
                " LEFT JOIN FETCH a.user u " +
                "LEFT JOIN FETCH u.accountList WHERE a.id = :id", Account.class).setParameter("id", id).uniqueResult();
        if (account == null) {
            throw new CloseAccountException("Такого аккаунта не существует, проверьте данные");
        }
        User user = account.getUser();
        if (user.getAccountList().size() == 1) {
            throw new CloseAccountException("У пользователя всего 1 аккаунт, закрытие невозможно");
        }
        if (account.getId() == 1) {
            throw new CloseAccountException("Невозможно закрыть первый первый(базовый) аккаунт пользователя");
        }
        Account transferAc = user.getAccountList().getFirst();
        transferAc.setMoneyAmount(transferAc.getMoneyAmount().add(account.getMoneyAmount()));
        session.remove(account);
    }
}