package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Exception.AccountDepositException;
import school.sorokin.javacore.spring_core_homework_base.Exception.TransactionsFailedException;
import school.sorokin.javacore.spring_core_homework_base.Exception.TransferFromAccountException;
import school.sorokin.javacore.spring_core_homework_base.Exception.UserIncorrectEnter;

import java.math.BigDecimal;
import java.math.BigInteger;


@Service
public class AccountService {
    @Value("${account.moneyAmount}")
    private BigDecimal defaultAmount;
    @Value("${account.commissionPayment}")
    private BigDecimal commissionPayment;
    private final SessionFactory sessionFactory;

    public AccountService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Account createdAccount(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Account newAccount = new Account(user, defaultAmount);
            user.addAccountList(newAccount);
            session.getTransaction().commit();
            session.close();
            return newAccount;
        }
    }

    public void accountDeposit(Long id, BigDecimal deposit) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Account accountDeposit = findAccountById(id);
            accountDeposit.setMoneyAmount(accountDeposit.getMoneyAmount().add(deposit));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void accountWithdraw(Long id, BigDecimal deposit) {
        if (id == null || deposit == null) {
            throw new UserIncorrectEnter("Введите не пустое значение");
        }

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Account accountDeposit = session.find(Account.class, id);
            if (accountDeposit == null) {
                throw new UserIncorrectEnter("Введите корректное значение пользователя");
            }
            if (accountDeposit.getMoneyAmount().compareTo(deposit) < 0) {
                throw new AccountDepositException("Недостаточная сумма для списания");
            }
            accountDeposit.setMoneyAmount(accountDeposit.getMoneyAmount().subtract(deposit));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void accountTransfer(Long from, Long to, BigDecimal sum) {
        if (from == null || to == null || sum == null) {
            throw new UserIncorrectEnter("Значение не должно быть пустым");
        }
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();

            Account fromAccount = session.find(Account.class, from);
            Account toAccount = session.find(Account.class, to);

            if (fromAccount == null) {
                throw new UserIncorrectEnter("Не найден счёт отправителя");
            }
            if (toAccount == null) {
                throw new UserIncorrectEnter("Не найден счёт получателя");
            }
            if (fromAccount.getMoneyAmount().compareTo(sum) < 0) {
                throw new UserIncorrectEnter("Недостаточно денег для перевода со счета на счёт");
            }
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount().subtract(sum));
            toAccount.setMoneyAmount(toAccount.getMoneyAmount().add(sum));
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public Account findAccountById(Long id) {
        if (id == null) {
            throw new UserIncorrectEnter("Нельзя вводить пустое значение");
        }
        try (Session session = sessionFactory.openSession()) {
            Account findAccount = session.find(Account.class, id);
            if (findAccount == null) {
                throw new UserIncorrectEnter("Такого пользователя нет в базе");
            }
            return findAccount;
        }
    }

    public void accountClose(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Account account = session.find(Account.class, id);
            User user = account.getUser();
            if (user.getAccountList().size() == 1) {
                throw new TransferFromAccountException("У пользователя всего 1 аккаунт, закрытие невозможно");
            }
            Account transferAc = user.getAccountList().getFirst();
            transferAc.setMoneyAmount(transferAc.getMoneyAmount().add(account.getMoneyAmount()));
            account.setUser(null);
            user.getAccountList().remove(account);
            session.remove(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}