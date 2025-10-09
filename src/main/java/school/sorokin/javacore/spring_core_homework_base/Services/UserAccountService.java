package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;

import java.math.BigDecimal;


@Service
public class UserAccountService {

    private final UserService userService;
    private final AccountService accountService;
    private final SessionFactory sessionFactory;

    public UserAccountService(UserService userService, AccountService accountService, SessionFactory sessionFactory) {
        this.userService = userService;
        this.accountService = accountService;
        this.sessionFactory = sessionFactory;
    }

    public void createUserAndDefaultAccount(User user) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            User newUser = userService.createdUser(user, session);
            accountService.createdAccount(newUser, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void showAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            userService.getAllUsers(session);
        }
    }

    public void createdAccountForUser(Long userId) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            accountService.createAccountForUser(userId, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public void closeAccount(Long accountId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            accountService.closeAccount(accountId, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void depositAccount(Long id, BigDecimal deposit) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            accountService.accountDeposit(id, deposit, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public void transfer(Long fromId, Long toId, BigDecimal deposit) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            accountService.accountTransfer(fromId, toId, deposit, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public void withdrawAccount(Long accountId, BigDecimal withdraw) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            accountService.accountWithdraw(accountId, withdraw, session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
