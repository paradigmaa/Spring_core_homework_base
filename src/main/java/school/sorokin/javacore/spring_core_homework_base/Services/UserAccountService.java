package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


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

    private <T> T executeInTransaction(Function<Session, T> function) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
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

    private void executeInTransaction(Consumer<Session> consumer) {
        executeInTransaction(session -> {
            consumer.accept(session);
            return null;
        });
    }

    public void createUserAndDefaultAccount(User user) {
        executeInTransaction(session -> {
            User newUser = userService.createUser(user, session);
            accountService.createAccount(newUser, session);
        });
    }

    public void showAllUsers(int page) {
        executeInTransaction(session -> {
            List<User> users = userService.getUsersWithPagination(page,10, session);
            users.forEach(System.out::println);
        });
    }

    public void createdAccountForUser(Long userId) {
        executeInTransaction(session -> {
            accountService.createAccountForUser(userId, session);
        });
    }


    public void closeAccount(Long accountId) {
        executeInTransaction(session -> {
            accountService.closeAccount(accountId, session);
        });
    }

    public void depositAccount(Long id, BigDecimal deposit) {
        executeInTransaction(session -> {
            accountService.depositAccount(id, deposit, session);
        });
    }

    public void transfer(Long fromId, Long toId, BigDecimal deposit) {
        executeInTransaction(session -> {
            accountService.transferAccount(fromId, toId, deposit, session);
        });
    }


    public void withdrawAccount(Long accountId, BigDecimal withdraw) {
        executeInTransaction(session -> {
            accountService.withdrawAccount(accountId, withdraw, session);
        });
    }
}
