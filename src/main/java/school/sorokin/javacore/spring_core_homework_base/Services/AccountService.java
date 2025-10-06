package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Exception.TransferFromAccountException;

import java.math.BigDecimal;


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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account newAccount = new Account(user, defaultAmount);
        user.addAccountList(newAccount);
        session.getTransaction().commit();
        session.close();
        return newAccount;
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
        try (Session session = sessionFactory.openSession()) {
            Account accountDeposit = findAccountById(id);
            accountDeposit.setMoneyAmount(accountDeposit.getMoneyAmount().subtract(deposit));
        }
    }

    public void accountTransfer(Long from, Long to, BigDecimal sum) {
        try (Session session = sessionFactory.openSession()) {
            Account fromAccount = findAccountById(from);
            Account toAccount = findAccountById(to);
            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount().subtract(sum));
            toAccount.setMoneyAmount(toAccount.getMoneyAmount().add(sum));
        }

    }


    public Account findAccountById(Long id) {
        Session session = sessionFactory.openSession();
        Account findAccount = session.find(Account.class, id);
        session.close();
        return findAccount;
    }

    public void accountClose(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Account account = findAccountById(id);
            User user = account.getUser();
            if (user.getAccountList().size() >= 2) {
                Account transferAc = user.getAccountList().getFirst();
                transferAc.setMoneyAmount(transferAc.getMoneyAmount().add(account.getMoneyAmount()));
                account.setUser(null);
                user.getAccountList().remove(account);
                session.remove(account);
            } else {
                throw new TransferFromAccountException("У пользователя всего 1 аккаунт, закрытие невозможно");
            }
            session.getTransaction().commit();
        } catch (Exception e) {

        }
    }
}