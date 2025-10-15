package school.sorokin.javacore.spring_core_homework_base.Services;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import school.sorokin.javacore.spring_core_homework_base.Entity.Account;
import school.sorokin.javacore.spring_core_homework_base.Entity.User;
import school.sorokin.javacore.spring_core_homework_base.Exception.UserCreatedException;

import java.util.*;

@Service
public class UserService {


    public User createUser(User user, Session session) {
        User us = (User) session.createQuery("SELECT u FROM User u WHERE u.login = :login")
                .setParameter("login", user.getLogin()).uniqueResult();
        if (us == null) {
            throw new UserCreatedException("Такой пользователь уже есть");
        } else {
            session.persist(user);
            return user;
        }
    }


        public List<User> getUsersWithPagination(int page, int size, Session session) {
            return session.createQuery("FROM User u LEFT JOIN FETCH u.accounts", User.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        }
    }