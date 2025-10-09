package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Exception.UserCreatedException;

import java.util.*;

@Service
public class UserService {


    public User createdUser(User user, Session session) {
        User us = (User) session.createQuery("SELECT u FROM User u WHERE u.login = :login")
                .setParameter("login", user.getLogin()).uniqueResult();
        if (us == null) {
            session.persist(user);
            return user;
        } else {
            throw new UserCreatedException("Такой пользователь уже есть");
        }
    }


    public void getAllUsers(Session session) {
        List<User> users =
                session.createQuery
                        ("SELECT u FROM User u LEFT JOIN FETCH u.accountList", User.class).getResultList();
        for (User u : users) {
            System.out.println("__________________________\n" + "ID: " + u.getId() + " пользователь " + u.getLogin()
                    + "\n__________________________");
            for (Account account : u.getAccountList()) {
                System.out.println("Список аккаунтов:\n" + "№ "
                        + account.getId() + " баланс: "
                        + account.getMoneyAmount());
                System.out.println();
            }
            System.out.println();
        }
    }
}